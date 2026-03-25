package puzzlesolver;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import static javafx.stage.FileChooser.ExtensionFilter;

import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.exceptions.InvalidPuzzleNameException;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.puzzles.binairo.puzzle.BinairoPuzzle;
import puzzlesolver.puzzles.kakurasu.puzzle.KakurasuPuzzle;
import puzzlesolver.puzzles.sudoku.puzzle.SudokuPuzzle;
import puzzlesolver.solvers.Solver;
import puzzlesolver.solvers.SolverFactory;
import puzzlesolver.ui.SolverConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public final class Main extends Application {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    private @Nullable Puzzle<?> puzzle;
    private final CompoundCommand comms = new CompoundCommand();
    private final SolverFactory solverFactory = new SolverFactory();
//    private final ObservableValue<ResourceBundle> bundle = new SimpleObjectProperty<>(ResourceBundle.getBundle());

    @Override
    public void start(Stage stage) {
        // Root VBox
        VBox root = new VBox();

        // Create scene and update stage
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setTitle("Puzzle Solver");
        stage.setScene(scene);
        scene.getStylesheets().add("styles.css");

        // Create menubar
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);
        root.getChildren().add(menuBar);

        Label text = new Label();
        root.getChildren().add(text);
        text.setStyle("-fx-font-family: 'monospaced';");

        // Create menus
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu puzzleMenu = new Menu("Puzzle");

        // Put menus in their spots
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(editMenu);
        menuBar.getMenus().add(puzzleMenu);

        // Create menu items
        MenuItem createPuzzleMenuItem = new MenuItem("create puzzle...");
        MenuItem loadPuzzleMenuItem = new MenuItem("load puzzle...");
        MenuItem savePuzzleMenuItem = new MenuItem("save puzzle...");
        MenuItem undoMenuItem = new MenuItem("undo");
        MenuItem redoMenuItem = new MenuItem("redo");
        MenuItem undoAllMenuItem = new MenuItem("undo all");
        MenuItem redoAllMenuItem = new MenuItem("redo all");
        MenuItem solvePuzzleMenuItem = new MenuItem("solve");
        MenuItem modifySolverMenuItem = new MenuItem("configure solver...");
        Menu solverConfSubMenu = new Menu("test");
        CheckMenuItem reasonToggleItem = new CheckMenuItem("Reasoning");
        CheckMenuItem backtrackToggleItem = new CheckMenuItem("Backtracking");

        // Put menu items in their spots
        fileMenu.getItems().add(createPuzzleMenuItem);
        fileMenu.getItems().add(loadPuzzleMenuItem);
        fileMenu.getItems().add(savePuzzleMenuItem);
        editMenu.getItems().add(undoMenuItem);
        editMenu.getItems().add(redoMenuItem);
        editMenu.getItems().add(undoAllMenuItem);
        editMenu.getItems().add(redoAllMenuItem);
        puzzleMenu.getItems().add(solvePuzzleMenuItem);
        puzzleMenu.getItems().add(modifySolverMenuItem);
        puzzleMenu.getItems().add(solverConfSubMenu);
        solverConfSubMenu.getItems().add(reasonToggleItem);
        solverConfSubMenu.getItems().add(backtrackToggleItem);

        // Set default states
        undoMenuItem.setDisable(true);
        redoMenuItem.setDisable(true);
        undoAllMenuItem.setDisable(true);
        redoAllMenuItem.setDisable(true);
        solvePuzzleMenuItem.setDisable(true);

        // Set actions
        createPuzzleMenuItem.setOnAction(e -> {});
        loadPuzzleMenuItem.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Load puzzle");
            fc.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
            fc.setInitialDirectory(new File(System.getProperty("user.dir")));
            File selectedFile = fc.showOpenDialog(stage);
            if (selectedFile == null)
                return;
            try {
                Scanner sc = new Scanner(selectedFile);
                this.puzzle = resolvePuzzleName(sc);
                comms.clear();
                text.setText("");
                puzzle.print(text);
                undoMenuItem.setDisable(false);
                redoMenuItem.setDisable(false);
                undoAllMenuItem.setDisable(false);
                redoAllMenuItem.setDisable(false);
                solvePuzzleMenuItem.setDisable(false);
            }
            catch (IOException err) {
                Alert d = new Alert(Alert.AlertType.ERROR);
                d.setTitle("Error occurred");
                d.setContentText("This file does not exist or cannot be opened. Please try again.");
                d.show();
            }
            catch (InvalidPuzzleNameException err) {
                Alert d = new Alert(Alert.AlertType.ERROR);
                d.setTitle("Error occurred");
                d.setContentText(
                        "This file has an invalid puzzle name. Please fix the contents or try a different file."
                );
                d.show();
            }
            catch (InvalidPuzzleSyntaxException err) {
                Alert d = new Alert(Alert.AlertType.ERROR);
                d.setTitle("Error occurred");
                d.setContentText(
                        "This file does not contain a valid puzzle. Please fix the contents or try a different file."
                );
                d.show();
            }
        });
        savePuzzleMenuItem.setOnAction(e -> {});
        undoMenuItem.setOnAction(e -> {
            comms.undo();
            text.setText("");
            Objects.requireNonNull(this.puzzle).print(text);
        });
        redoMenuItem.setOnAction(e -> {
            comms.apply();
            text.setText("");
            Objects.requireNonNull(this.puzzle).print(text);
        });
        undoAllMenuItem.setOnAction(e -> {
            comms.undoAll();
            text.setText("");
            Objects.requireNonNull(this.puzzle).print(text);
        });
        redoAllMenuItem.setOnAction(e -> {
            comms.applyAll();
            text.setText("");
            Objects.requireNonNull(this.puzzle).print(text);
        });
        solvePuzzleMenuItem.setOnAction(e -> {
            Puzzle<?> puzzle = this.puzzle;
            Solver s = solverFactory.withPuzzle(Objects.requireNonNull(puzzle)).build();
            s.solve(comms);
            text.setText("");
            puzzle.print(text);
        });
        modifySolverMenuItem.setOnAction(e -> {
            Stage sConfiguration = new SolverConfiguration(solverFactory);
            sConfiguration.show();
        });
        reasonToggleItem.selectedProperty().bindBidirectional(solverFactory.reasonerProperty());
        backtrackToggleItem.selectedProperty().bindBidirectional(solverFactory.backtrackProperty());

        // Create accelerator keys
        KeyCombination createKeys = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);
        KeyCombination loadKeys = new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN);
        KeyCombination saveKeys = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
        KeyCombination undoKeys = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
        KeyCombination redoKeys = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
        KeyCombination undoAllKeys = new KeyCodeCombination(KeyCode.Z,
                KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN);
        KeyCombination redoAllKeys = new KeyCodeCombination(KeyCode.Y,
                KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN);

        // Set accelerators
        createPuzzleMenuItem.setAccelerator(createKeys);
        loadPuzzleMenuItem.setAccelerator(loadKeys);
        savePuzzleMenuItem.setAccelerator(saveKeys);
        undoMenuItem.setAccelerator(undoKeys);
        redoMenuItem.setAccelerator(redoKeys);
        undoAllMenuItem.setAccelerator(undoAllKeys);
        redoAllMenuItem.setAccelerator(redoAllKeys);

        // Finish up
        stage.show();
    }

    private static Puzzle<?> resolvePuzzleName(Scanner sc)
            throws InvalidPuzzleNameException, InvalidPuzzleSyntaxException {
        String name = sc.next();
        return switch (name.toLowerCase()) {
            case "sudoku" -> new SudokuPuzzle(sc);
            case "kakurasu" -> new KakurasuPuzzle(sc);
            case "binairo" -> new BinairoPuzzle(sc);
            default -> throw new InvalidPuzzleNameException("This name cannot be resolved");
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
