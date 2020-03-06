# AteneaNote


_AteneaNote es una plataforma creada en adroid nativo con el objetivo de informatizar la manera en la que estudiamos y tomamos apuntes en clase._
 
## Introducción
 El objetivo de está aplicación es que los usuarios puedan tener todos sus apuntes en la nube vaya donde vaya. El usuario también puede ver sus datos y actualizarlos.
 
Estas son unas de las imagenes sobre la aplicación a modo de orientación del diseño y de minitutorial:

#### Login y Registro
 _Al entrar aparece el login para loguearte con un enlace al registro para registrarte si no estás registrado._

<img src="fotos/login.png" width="303"/> <img src="img/registro.jpg" width="303"/>

#### La jerarquía de datos

_Al iniciar sesión se muestra una colección de cursos en los que estás matriculada y son clickables para acceder a sus asignaturas._

<img src="img/232.png" width="303"/>

#### Asignaturas

<img src="img/subjects.jpg" width="303"/>

#### Material de Estudio

_Dentro de cada asignatura podrás ver un listado de dos tipos de objetos: los *apuntes* y las *notas*_

###### La primera consiste en los apuntes extensos que solemos tomar y la segunda se define como una síntesis de lo que has redactado en los apuntes para poder estudiar más eficientemente

<img src="img/busqueda.gif" width="303"/>

### Más multimedia++



### Aspectos técnicos
AteneaNote es una plataforma cuya interfaz con el usuario o front-end está basada en Java con Android Nativo y cuyos servicios, 
funcionalidades o back-end están hechos en python. La parte del backend consiste en un servidor en la nube de [Heroku](https://heroku.com) y soportado por el framework
de python Flask. AteneaNote tambíen implementa el uso de la autenticación con Google FireBase Authentication para el control de usuarios.

Otro aspecto de la aplicación es que es una de las primeras aplicaciones que implementa una fuente que ha sido diseñada cientificamente por el MIT (massachusetts institute of technology)
usando psicologías del diseño y del aprendizaje. Esta letra a sido diseñada especialmente para estudiar de una forma más eficiente. Por ello en AteneaNote hay un apartado que te muestra solamente las notas para poder repasar lo que ya has estudiado con facilidad
