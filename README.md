## El camino para ser el mejor

## Descripción del proyecto
El sistema tiene como propósito principal generar un juego interactivo por consola basado en el universo Pokémon, en el cual el jugador podrá vivir la experiencia de convertirse en el mejor entrenador.

El juego permitirá realizar distintas acciones como capturar Pokémon en diferentes hábitats, administrar el equipo de combate, desafiar gimnasios y enfrentarse al Alto Mando.

El proyecto utiliza archivos de texto para almacenar toda la información necesaria del juego, tales como los Pokémon disponibles, sus estadísticas, tipos, porcentajes de aparición, gimnasios, integrantes del Alto Mando y el progreso del jugador. Además, el sistema contará con persistencia de datos, permitiendo guardar y continuar partidas mediante el archivo Registros.txt.

La aplicación será desarrollada utilizando Programación Orientada a Objetos (POO), implementando clases que representen las principales entidades del juego, como Pokémon, Jugador, Gimnasio y Alto Mando. Además, se utilizarán colecciones dinámicas como ArrayList para manejar el equipo y los Pokémon capturados por el usuario, junto con arreglos para implementar la matriz de efectividad de tipos.

Dentro de las mecánicas principales del juego se incluirán combates basados en la suma de estadísticas de los Pokémon y en la efectividad de tipos, utilizando una matriz bidimensional para determinar ventajas y desventajas entre ellos. El jugador podrá capturar Pokémon según probabilidades de aparición, intercambiar integrantes de su equipo mediante el PC y progresar derrotando gimnasios en orden hasta poder acceder al desafío final del Alto Mando.

Dentro de las mecánicas principales del juego se incluirán combates basados en la suma de estadísticas de los Pokémon y en la efectividad de tipos, utilizando una matriz bidimensional para determinar ventajas y desventajas entre ellos. El jugador podrá capturar Pokémon según sus probabilidades de aparición, intercambiar integrantes de su equipo mediante el PC y avanzar derrotando gimnasios en orden hasta acceder al desafío final contra el Alto Mando, con el objetivo de coronarse como campeón.

Finalmente, el proyecto busca aplicar conceptos fundamentales de programación, manejo de archivos, estructuras de datos y diseño orientado a objetos, desarrollando un sistema funcional, dinámico e interactivo que simule varias mecánicas clásicas de la franquicia Pokémon.

## Integrantes
- Nombre Completo: Diego Nikolas Serrano Fuentes 
  - RUT: 22.105.561-6 
  - GitHub: diegoserrano-cloud

- Nombre Completo: Josselyn Alejandra Barraza Yáñez
  - RUT: 22.246.539-7
  - GitHub: josselynbarraza-sys
    
## Estructura del proyecto
- Taller2_DiegoSerrano_JosselynBarraza
    - src
        - Clases
            - AltoMando.java # Esta se encarga de representar a los miembros del Alto mando
            - Gimnasio.java # Representa cada gimnasio con su líder y pokémones
            - Jugador.java # Se encarga del estado del jugador y sus pokémones
            - Pokedex.java # Datos de cada pokémon 
            - TablaTipos.java #Matriz que se encarga de ver debilidades y fortalezas de cada tipo de pokémon
            - package-info.java
        - Logica
            - App.java # Clase principal que contiene toda la logica detrás del juego
            - package-info.java
        - module-info.java
    - .ruta de clases
    - .proyecto
    - Alto Mando.txt
    - DIAGRAMAS.pdf
    - Gimnasios.txt
    - Hábitats.txt
    - Pokedex.txt
    - Registros.txt
## Instrucciones 
#Requisitos previos
- Java JDK 11 o superior instalado
- Tener algún IDE instalado compatible con java, por ejemplo, Eclipse IDE o Visual Studio Code.

#Instrucciones de ejecución
1.- Importar el proyecto dentro del IDE seleccionado.
2.- Seleccionar la carpeta src -> package "Logica" -> Clase "App".
3.- Una vez dentro buscar el boton de "Play".
4.- Disfrutar del juego :D
