# Val's Sudoku 🧩

Un Sudoku 9x9 hecho con cariño para Val, en Java y JavaFX.

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

## Autor

Andrés Gerardo González
