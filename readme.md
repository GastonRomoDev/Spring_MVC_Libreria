# Proyecto CRUD Libreria con Spring MVC
-------------------

## Acerca del proyecto:
-------------------
Esta aplicacion fue realizada con la finalidad de poner en practica mis conocimientos en Java 8, Spring MVC,HTML5,CCSS3,Bootstrap5, MySQL.

### Casos de uso de la aplicacion:

* Altas,bajas y modificaciones de Autores
* Altas,bajas y modificaciones de Editoriales
* Altas,bajas y modificaciones de Clientes
* Altas,bajas y modificaciones de Libros
* Altas,bajas y modificaciones de Editoriales
* Prestamos de libros a Clientes
* Visualizacion de libros disponibles


### Obtener el codigo:
-------------------
Clonar el repositorio:

    $ git clone https://github.com/GastonRomoDev/Spring_MVC_Libreria.git

Si es tu primera vez usando Github puedes visitar https://help.github.com para aprender lo basico.

### Como correr la aplicacion:
-------------------	
Primero ingresar a Spring_MVC_Libreria/libreriaApp/src/main/resources y en el mismo hay un archivo llamado application.properties, el cual hay que modificar, ingresando las propiedades de su MySQL.

Desde una terminal realizar lo siguiente:

    $ cd Spring_MVC_Libreria/libreriaApp   #Ingresar a la carpeta del proyecto.
    $ mvn clean install         #Maven hace un clean de cada modulo y luego la instalacion de cada uno.
    $ mvn spring-boot:run       #Deploy de la aplicacion

Para acceder a la app una se hizo el deploy: http://localhost:8080/

### Requisitos previos:
-------------------

* Tener instalado un IDE o editor de texto.
* Tener instalado Java 8 o superior.
* Tener instalado MySQL, configurado con usuario y contraseña.
* Tener instalado Maven.
