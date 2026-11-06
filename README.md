# Tienda Soluciones Tecnológicas

## 1. Descripción del proyecto

Este proyecto consiste en el desarrollo de una aplicación web para una tienda de soluciones tecnológicas que se dedica a la venta de productos y a brindar servicios de reparación de celulares y computadoras.

La idea es crear un sitio web donde los clientes puedan consultar los productos disponibles, realizar pedidos y solicitar reparaciones. Además, el sistema permitirá llevar un mejor control de los productos, pedidos y servicios que ofrece el negocio.

El sistema contará con tres tipos de usuarios:

- **Administrador:** podrá administrar los productos, las categorías, los usuarios, los pedidos y las reparaciones.
- **Cliente:** podrá consultar los productos, realizar pedidos y solicitar reparaciones.
- **Técnico:** podrá revisar las solicitudes de reparación y actualizar su estado.

## 2. Tecnologías utilizadas

Para desarrollar el proyecto se están utilizando las siguientes tecnologías y herramientas:

- Java 21.
- Spring Boot.
- Spring MVC.
- Spring Data JPA.
- Thymeleaf.
- HTML y CSS.
- Bootstrap 5.
- MySQL.
- Apache NetBeans.
- Maven.
- GitHub.

## 3. Requisitos para ejecutar el proyecto

Para poder ejecutar la aplicación se necesita tener instalado:

- JDK 21.
- Apache NetBeans.
- MySQL Server.
- MySQL Workbench (opcional).
- Maven.
- Un navegador web.

## 4. Configuración de la base de datos

Antes de ejecutar el proyecto, es necesario tener iniciado el servidor MySQL y crear la siguiente base de datos:

`tienda_soluciones_tecnologicas`

También se deben configurar los datos de conexión en el archivo `application.properties`, que se encuentra dentro de `src/main/resources`.

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tienda_soluciones_tecnologicas?zeroDateTimeBehavior=CONVERT_TO_NULL
spring.datasource.username=soluciones_app
spring.datasource.password=12345678

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8081
```

Cada integrante debe colocar su propio usuario y contraseña de MySQL.

## 5. Cómo ejecutar el proyecto

1. Descargar o clonar el proyecto desde GitHub.
2. Abrirlo utilizando Apache NetBeans.
3. Verificar que MySQL esté funcionando y que la conexión esté configurada correctamente.
4. Esperar a que Maven cargue las dependencias.
5. Ejecutar la clase `SolucionestecnologicasApplication.java`.
6. Abrir el navegador e ingresar a `http://localhost:8081/`.

## 6. Estado actual del proyecto

Hasta el momento se ha trabajado en las siguientes partes:

- Creación de la base de datos y sus respectivas tablas.
- Creación de las entidades del sistema.
- Creación de los repositorios DAO.
- Desarrollo de las clases Service.
- Creación de los controladores para las diferentes secciones.
- Diseño de las páginas HTML.
- Conexión de Spring Boot con MySQL.
- Integración inicial entre los controladores y las vistas mediante Thymeleaf.

Actualmente, el proyecto cuenta con la estructura principal del backend y las interfaces del sistema. Todavía queda pendiente continuar con la integración y las pruebas para comprobar que todas las funcionalidades trabajen correctamente.

## 7. Integrantes del grupo

**Universidad:** Universidad Fidélitas.

**Curso:** Desarrollo de Aplicaciones Web.

- Sebastián Felipe Sánchez Meneses.
- Hellen Mariana Gómez Rocha.
- Joshua Mitchell Navarro.
- Kendall Andrés Orozco Gómez.
