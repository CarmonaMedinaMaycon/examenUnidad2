import axios from 'axios';



const getBook = async () => {
   try { 
    const response = await axios.get("http://localhost:8080/api/books/");
    console.log(response);
      return response.data.data;
   } catch (error) {
      throw error;
   }
};


const postBook = async (libro) => {
   try {
      const response = await axios.post("http://localhost:8080/api/books/", libro);
       console.log("hola chinga tu madre",response);
      return response.data;
   } catch (error) {
      throw error;
   }
};


const deleteBook = async (id) => {
   try {
     const response = await axios.delete(`http://localhost:8080/api/books/delete/${id}`);
     return response.data;
   } catch (error) {
     throw error;
   }
 };

 const updatepeBook = async (libro) => {
   try {
     const response = await axios.put(`http://localhost:8080/api/books/update`, libro);
     console.log(response.data);
     if (response.data.error) {
       console.error(response.data.message);
     } else {
       console.log(response.data.message);
       this.obtenerPeliculas();
     }
   } catch (error) {
     console.error("Error al actualizar la película:", error);
   }
 };


 const sortedByReleaseDateDesc = async () => {
   try {
      const response = await axios.get("http://localhost:8080/api/books/sortedByReleaseDateDesc");
      return response.data;
   } catch (error) {
      throw error;
   }
};


const sortedByAutorDesc = async () => {
   try {
      const response = await axios.get("http://localhost:8080/api/books/sortedByBooksDesc");
      return response.data;
   } catch (error) {
      throw error;
   }
};
   
export default {
   getBook,
   postBook,
   deleteBook,
   updatepeBook,
   sortedByReleaseDateDesc,
   sortedByAutorDesc
};