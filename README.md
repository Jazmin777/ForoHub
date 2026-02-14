# ForoHub

### **Descripción**
ForoHub es una **API REST** desarrollada en Java con **Spring Boot**. El proyecto replica el funcionamiento de un foro de discusión. Permite gestionar tópicos de conversación, asegurando que cada acción sea realizada por usuarios autenticados mediante tokens.



### **Funcionalidades**
* **Seguridad con JWT**: Solo los usuarios con un token pueden entrar a las rutas protegidas.
* **Gestión de Tópicos**:
    * Crear un tópico.
    * Listar todos los tópicos.
    * Ver el detalle de un tópico por su ID.
    * Actualizar los datos de un tópico.
    * Borrar tópicos.
* **Persistencia**: Todos los datos se guardan de en una base de datos.

### **Cómo pueden usarlo los usuarios**
1. **Autenticación**:
   - Enviar una petición `POST` a `http://localhost:8080/login` con usuario y contraseña.
   - La respuesta devolverá un token.

2. **Uso**:
   - Copia el token y úsalo en el encabezado de tus peticiones en Insomnia como `Bearer Token`.


### **Autores**
* **Jazmin Rodriguez** - Desarrolladora del proyecto.
