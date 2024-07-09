Instrucciones para probar el proyecto

Crear una carpeta en el escritorio, hacer click derecho en git bash here y luego pegar el siguiente codigo: git clone https://github.com/Gaston-Tisera99/proyectoFinal-commerce.git 
Una vez clonado el repositorio abrir el proyecto en IntelliJ, luego crear la base de datos en mysql: appCoder. Ejecutar el proyecto y probar los siguientes endpoints en Postman:
--Producto--
Para crear un Producto: 
con metodo POST
localhost:8081/api/v1/products
Para leer todos los productos:
con metodo GET
localhost:8081/api/v1/products
Para leer un solo producto:
con metodo GET
localhost:8081/api/v1/products/:pid
Para actualizar
con metodo PUT
localhost:8081/api/v1/products/:pid
--Cliente--
Para crear un cliente:
con metodo POST
localhost:8081/api/v1/auth/register
Para actualizar un cliente 
con metodo PUT
localhost:8081/api/v1/auth/me
--Comprobantes--
Para crear un comprobante:
con metodo POST
localhost:8081/api/v1/carts
Para eliminar un comprobante: 
con metodo DELETE
localhost:8081/api/v1/carts/:id
