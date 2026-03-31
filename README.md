# Puzzle Solver

Hello, this is a personal pet project of mine. It is a JavaFX application that is meant to allow a user to define
a puzzle and then solve it through reasoners or solvers. The idea is that I can add support for new puzzle types
at any point based on my own whims. The structure of the files is also oriented around this approach. It is still
largely a W.I.P., but I will add on to it from time to time.

Since this is a personal pet project, I will likely not accept any pull requests from others. I am keeping it
open-source mainly so others can give me feedback on my code and so they can request new puzzle types or features.

## Running locally

Since I do not yet have a proper packaging setup in Gradle, the only way to run this is to
run the gradle build locally. This can be done by using the command

```bash
PuzzleSolver> ./gradlew run
```