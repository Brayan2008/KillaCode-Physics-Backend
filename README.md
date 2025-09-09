# PhysicsApp-Backend

### (ACTUALIZACION: El proyecto se queda en desarrollo, actualmente hay un error critico con los ejercicios y la compatibilidad con una app móvil, se tomarán medidas para arreglar este error. El programa aun no esta apto para ser usado)

Este repositorio contiene la API hecha en Spring Boot, en su primera versión y ya esta disponible. Por ahora solo soporta PostgreSQL y como mínimo debe ser ejecutado en Java 21.

Además, para que funcione adecuadamente, es necesario establecer variables de entorno para la conexión a la base de datos, dichas variables son las siguientes:

* DB_URL : La URL de conexión a tu base de datos
* DB_USER: El nombre de usuario ("postgres" por default)
* DB_PASS: La contraseña de tu base de datos
* El puerto por default es 80.

Este servicio ayudará en las peticiones de la aplicación principal hecha en Flutter. La documentación de los endpoints aun no esta disponible, pero puedes ver el código fuente y guiarte (lo sé, puede ser poco útil, pero la universidad me quita algo de tiempo...). Muy pronto, si Dios quiere estaré subiendo la documentación de los endpoints y talvez del código.
