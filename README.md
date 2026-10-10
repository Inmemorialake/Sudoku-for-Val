# Val's Sudoku 🧩

Un Sudoku 9x9 hecho con cariño para Val, en Java y JavaFX.

## Capturas

<p align="center">
  <img src="Screenshots/Val's%20Sudoku%20Start%20Screen.png" alt="Menú de inicio" width="45%">
  &nbsp;
  <img src="Screenshots/Val's%20Sudoku%20Game%20Screen.png" alt="Pantalla de juego" width="45%">
</p>

## Características

- **Tres niveles de dificultad** según cómo te sintás hoy:

  | Nivel   | Números dados por bloque |
  |---------|--------------------------|
  | Tranqui | 5                        |
  | Normal  | 4                        |
  | Reto    | 3                        |

- **Tableros siempre resolubles**: cada partida parte de una solución completa generada al azar, y de ahí se dejan algunos números como pistas.
- **Validación en tiempo real**: si ponés un número que choca con su fila, columna o bloque, la celda se marca y aparece un aviso.
- **Resaltado de celdas**: al seleccionar una celda se iluminan su fila, columna y bloque, y también las celdas con el mismo número.
- **Pistas opcionales**: se activan desde el menú con *"Quiero pistas por si acaso"*. El botón *Pista* rellena una celda con un número que mantiene el tablero resoluble. Si ya no hay salida, te lo avisa.
- **Volver al menú** en cualquier momento para cambiar de dificultad.

## Paleta de colores

Todo gira alrededor del morado, sobre un fondo oscuro. Los estilos viven en `styles.css`, salvo los colores de los mensajes, que se asignan desde `SudokuController.java`.

| Color | Hex | Uso |
|:-----:|-----|-----|
| ![#221a2e](https://placehold.co/20x20/221a2e/221a2e.png) | `#221a2e` | Fondo |
| ![#9b6bff](https://placehold.co/20x20/9b6bff/9b6bff.png) | `#9b6bff` | Títulos, mensajes, botones y celda seleccionada |
| ![#b99cff](https://placehold.co/20x20/b99cff/b99cff.png) | `#b99cff` | Celdas editables y textos del menú |
| ![#8a5cf6](https://placehold.co/20x20/8a5cf6/8a5cf6.png) | `#8a5cf6` | Bordes del tablero y de los controles |
| ![#6b3fd4](https://placehold.co/20x20/6b3fd4/6b3fd4.png) | `#6b3fd4` | Celdas con números dados |
| ![#a283f5](https://placehold.co/20x20/a283f5/a283f5.png) | `#a283f5` | Fila, columna y bloque resaltados |
| ![#5a32b5](https://placehold.co/20x20/5a32b5/5a32b5.png) | `#5a32b5` | Números dados resaltados |
| ![#4a2a8c](https://placehold.co/20x20/4a2a8c/4a2a8c.png) | `#4a2a8c` | Celdas con el mismo número |
| ![#a57dff](https://placehold.co/20x20/a57dff/a57dff.png) | `#a57dff` | Celdas rellenadas con una pista |
| ![#7b1e3a](https://placehold.co/20x20/7b1e3a/7b1e3a.png) | `#7b1e3a` | Celda con un número inválido |
| ![#b04a78](https://placehold.co/20x20/b04a78/b04a78.png) | `#b04a78` | Mensajes de error y avisos |
| ![#e2d4ff](https://placehold.co/20x20/e2d4ff/e2d4ff.png) | `#e2d4ff` | Mensaje de victoria |

La tipografía es **JetBrains Mono**.

## Requisitos

- JDK 17 o superior

No hace falta instalar Maven: el proyecto trae el wrapper (`mvnw`), y JavaFX se descarga como dependencia.

## Cómo ejecutarlo

En Linux o macOS:

```bash
./mvnw clean javafx:run
```

En Windows:

```bat
mvnw.cmd clean javafx:run
```

## Estructura del proyecto

```
src/main/java/com/project2/project2fpoe/
├── Main.java                  # Punto de entrada
├── model/
│   ├── Sudoku.java            # Generación del tablero, validación y pistas
│   └── Difficulty.java        # Niveles de dificultad
├── controller/
│   ├── StartController.java   # Menú de inicio
│   └── SudokuController.java  # Lógica de la pantalla de juego
└── view/
    ├── StageStart.java        # Ventana del menú
    └── StageSudoku.java       # Ventana del juego

src/main/resources/com/project2/project2fpoe/
├── StartScreen.fxml           # Vista del menú
├── Project2FPOE.fxml          # Vista del tablero
└── styles.css                 # Paleta y estilos
```

## Para Val 💜

> Yo te prometí hace ya tiempo un sudoku, helo aquí. Aún queda margen de mejora, pero traté de hacerlo pensando en vos.

## Autor

Andrés Gerardo González
