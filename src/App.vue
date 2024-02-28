<template>
  <div style="height: 2000px" class="content">
    <template>
      <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
          <b>
            <h3>Libros <b-icon icon="camera-reels"></b-icon></h3>
          </b>
        </div>
      </div>
    </template>

    <b-row class="mb-4" v-show="showElement">
      <b-col>
        <b-carousel
          id="carousel-1"
          v-model="slide"
          :interval="2000"
          controls
          indicators
          background="#ababab"
          img-width="200"
          img-height="480"
          style="margin-bottom: 100px; text-shadow: 1px 1px 2px #333"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <!-- Text slides with image -->
          <b-carousel-slide
            caption="First slide"
            text="Nulla vitae elit libero, a pharetra augue mollis interdum."
            img-src="https://w.forfun.com/fetch/51/5109391dccffed19bdaa99df57e1f29b.jpeg"
            img-height="10"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="First slide"
            text="Nulla vitae elit libero, a pharetra augue mollis interdum."
            img-src="https://w.forfun.com/fetch/51/5109391dccffed19bdaa99df57e1f29b.jpeg"
            img-height="10"
          ></b-carousel-slide>
          <b-carousel-slide
            caption="First slide"
            text="Nulla vitae elit libero, a pharetra augue mollis interdum."
            img-src="https://w.forfun.com/fetch/51/5109391dccffed19bdaa99df57e1f29b.jpeg"
            img-height="10"
          ></b-carousel-slide>
        </b-carousel>
      </b-col>
    </b-row>

    <b-row> </b-row>

    <b-row class="mb-4">
      <b-col>
        <div class="bodybutton">
          <b-button v-b-modal.modal-save-book class="btnadd">
            <b-icon icon="plus"></b-icon> Registrar Libro
          </b-button>
          <b-button
            id="areaLibros"
            @drop.prevent="handleDropEdit"
            @dragover.prevent
            class="btnadd"
          >
            <b-icon icon="eyedropper"></b-icon> Editar Libro
          </b-button>
          <b-button
            id="areaLibros"
            @drop.prevent="handleDrop"
            @dragover.prevent
            class="btnadd"
          >
            <b-icon icon="x-circle"></b-icon> Eliminar Libro
          </b-button>
        </div>
      </b-col>
      <TransitionGroup name="slideDown" tag="div" class="d-flex flex-wrap">
        <b-col
          v-for="(libro, key) in libros"
          :key="key"
          lg="3"
          md="6"
          sm="12"
          class="mb-4"
        >
          <b-card
            draggable="true"
            @dragstart="dragStart(libro)"
            :title="libro.name"
            img-src="https://imgs.search.brave.com/5yLy2Vd-AcHQFOAMoQtlMkUY5VNtYEPsmMJ2pLqI1HA/rs:fit:860:0:0/g:ce/aHR0cHM6Ly93YWxs/cGFwZXJzZXQuY29t/L3cvZnVsbC9iLzcv/OC80MDQzNy5qcGc"
            class="mb-2"
          >
            <b-card-text class="card-text-scroll">
              <b>Autor:</b> {{ libro.autor }}<br />
              <b>Fecha de publicación:</b> {{ libro.releaseDate }}<br />
            </b-card-text>
          </b-card>
        </b-col>
      </TransitionGroup>
    </b-row>

    <ModalSaveBook @reloadMovies="getMovies" />
    <ModalUpdateBook />
  </div>
</template>

<script>
import Movies from "./components/services/Books";
import ModalSaveBook from "./components/books/ModalSaveBook.vue";
import ModalUpdateBook from "./components/books/ModalUpdateBook.vue";
export default {
  components: { ModalSaveBook, ModalUpdateBook },
  name: "libros",
  data() {
    return {
      selected: null,
      showElement: true,
      lastScrollPosition: 0,
      libros: {
        autor: "",
        name: "",
        relaseDate: "",
      },
      slide: 0,
      sliding: null,
    };
  },

  methods: {
    dragStart(libro) {
      // Inicia el arrastre de la tarjeta específica
      event.dataTransfer.setData("libro", JSON.stringify(libro));
    },
    handleDrop(event) {
      // Manipula el evento de soltar tarjeta en el área
      const libroData = JSON.parse(event.dataTransfer.getData("libro"));
      console.log("libro data", libroData.id);
      this.deleteBooks(libroData.id);
    },
    handleDropEdit(event) {
      // Manipula el evento de soltar tarjeta en el área
      const libroData = JSON.parse(event.dataTransfer.getData("libro"));
      console.log("libro data", libroData);
      this.$bvModal.show("modal-update-book");
    },
    async deleteBooks(libroData) {
      try {
        await Movies.deleteBook(libroData);
        this.getMovies(); // Actualiza la lista de películas después de registrar una nueva
      } catch (error) {
        console.error("Error al registrar la película:", error);
      }
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },
    onScroll() {
      // Obtiene la posición actual del scroll
      const currentScrollPosition =
        window.pageYOffset || document.documentElement.scrollTop;
      console.log(currentScrollPosition);

      // La función abs para tener valores absolutos y se delimita con un offset o bien llamado
      // margen para que el valor de la posición sea después de desplazarce y no desde que uno se desplaza
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }
      // aqui determinamos si la posición es mayor a la posición anterior. Entonces, si lo es, mostramos el elemento.
      this.showElement = currentScrollPosition < this.lastScrollPosition;
      //
      this.lastScrollPosition = currentScrollPosition;
    },
    async getMovies() {
      try {
        const response = await Movies.getBook();
        console.log("soy la data", response);
        this.libros = response;
      } catch (error) {
        console.log("trono papito", error);
      }
    },
  },
  mounted() {
    window.addEventListener("scroll", this.onScroll);
    this.getMovies();
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
  },
};
</script>

<style>
.body {
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 10px;
}

@media screen and (max-width: 768px) {
  .body {
    padding-left: 10px;
    padding-right: 10px;
  }
}

.btnadd {
  background-color: #089779;
  padding-right: px;
}

.icono {
  text-align: end;
}
.hidden {
  display: none;
}
.card-text-scroll {
  max-height: 150px;
  overflow-y: auto;
  /* añade una barra de desplazamiento vertical si es necesario */
}
</style>
