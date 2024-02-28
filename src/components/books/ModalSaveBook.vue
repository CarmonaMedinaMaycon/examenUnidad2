<template>
  <div>
    <div>
      <b-modal hide-footer hide-header centered id="modal-save-book">
        <header class="text-center border-bottom">
          <p style="font-family: cabin">Registrar libro</p>
        </header>
        <main>
          <form id="registrarlibro">
            <b-row>
              <b-col>
                <label for="libro">Nombre del libro: *</label>

                <b-form-input
                  v-model="libro.name"
                  type="text"
                  class="form-control"
                  placeholder="titulo del libro..."
                  required
                  :class="{
                    'input-border-error':
                      !validateName && libro.name.length > 0,
                    'input-border-success': validateName,
                  }"
                  aria-describedby="input-live-help input-live-feedback"
                />

                <div
                  v-if="!validateName && libro.name.length > 0"
                  class="invalid-feedback"
                >
                  Formato inválido
                </div>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <label for="libro">Autor: *</label>
                <b-form-textarea
                  id="textarea"
                  v-model="libro.autor"
                  placeholder="Nombre del autor del libro..."
                  rows="3"
                  max-rows="6"
                  :state="validateDescription"
                ></b-form-textarea>

                <b-form-invalid-feedback :state="validateDescription">
                  No se aceptan caracteres especiales ni espacios en blanco
                </b-form-invalid-feedback>
              </b-col>

              <b-col>
                <label for="releaseDate">Fecha de lanzamiento: *</label>
                <b-form-input
                  v-model="libro.releaseDate"
                  type="date"
                  class="form-control"
                  placeholder="Fecha de lanzamiento..."
                  required
                  :class="{
                    'input-border-error':
                      !validateReleaseDate && libro.releaseDate.length > 0,
                    'input-border-success': validateReleaseDate,
                  }"
                  aria-describedby="input-live-help input-live-feedback"
                />
                <div
                  v-if="!validateReleaseDate && libro.releaseDate.length > 0"
                  class="invalid-feedback"
                >
                  Formato inválido
                </div>
              </b-col>
            </b-row>
            <b-row>
                <b-form-file v-model="libro.image" class="mt-3" plain></b-form-file>
            </b-row>
          </form>
        </main>

        <footer class="text-center mt-5">
          <button class="btn m-1 cancel" @click="onClose">Cancelar</button>
          <button
            class="btn m-1 success"
            @click="save"
            :disabled="!validateForm"
            type="submit"
          >
            Registrar
          </button>
        </footer>
      </b-modal>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import Books from "../services/Books";
export default {
  name: "modal-save-book",

  data() {
    return {
      
      libro: {
        name: "",
        autor: "",
        releaseDate: "",
        image: "",
      },
    };
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-save-book");
      this.libro.name = "";
      this.libro.autor = "";
      this.libro.releaseDate = null;
    },
    async save() {
      Swal.fire({
        title: "¿Estás seguro de registrar etsa libro?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#008c6f",
        cancelButtonColor: "#e11c24",
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            console.log(this.libro);
            await Books.postBook(this.libro);

            Swal.fire({
              title: "¡Guardada!",
              text: "La libro se registró correctamente",
              icon: "success",
            });
            this.onClose();

            this.$emit("reloadMovies");
          } catch (error) {
            console.log("Error al guardar la libro", error);
          }
        }
      });
    },
  },
  computed: {
    validateName() {
      const regex = /^[a-zA-Z0-9]+$/;
      return (
        this.libro.name.length > 0 &&
        this.libro.name.length < 100 &&
        regex.test(this.libro.name)
      );
    },
    validateDescription() {
      const regex = /^[a-zA-Z0-9]+$/;
      return (
        this.libro.autor.length > 0 &&
        this.libro.autor.length < 100 &&
        regex.test(this.libro.autor)
      );
    },
    validateReleaseDate() {
      // Expresión regular para el formato YYYY-MM-DD
      const regex = /^\d{4}-\d{2}-\d{2}$/;
      // Verificar que la fecha cumpla con el formato YYYY-MM-DD
      return regex.test(this.libro.releaseDate);
    },
    validateForm() {
      return this.validateName && this.validateDescription;
    },
  },
};
</script>

<style scoped>
.success {
  font-family: Cabin;
  background-color: #009475;
  color: white;
}

.cancel {
  font-family: Cabin;
  background-color: brown;
  color: black;
}
.input-border-error {
  border: 1px solid red !important;
}

.input-border-success {
  border: 1px solid green !important;
}
</style>
