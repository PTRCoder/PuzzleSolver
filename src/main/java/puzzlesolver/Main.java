package puzzlesolver;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.exceptions.InvalidPuzzleNameException;
import puzzlesolver.exceptions.InvalidPuzzleSyntaxException;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.loc.GUIStrings;
import puzzlesolver.loc.LocaleManager;
import puzzlesolver.puzzles.PuzzleFactory;
import puzzlesolver.solvers.Solver;
import puzzlesolver.solvers.SolverFactory;
import puzzlesolver.ui.LocaleSelector;
import puzzlesolver.ui.SolverConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import static javafx.stage.FileChooser.ExtensionFilter;

@Slf4j
public final class Main extends Application {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    private static final String CSS = "styles.css";

    private static final ObjectProperty<@Nullable Puzzle<?>> puzzle = new SimpleObjectProperty<>();
    private final CompoundCommand comms = new CompoundCommand();
    private final SolverFactory solverFactory = new SolverFactory();

    @Override
    public void start(Stage stage) {
        // Root VBox
        VBox root = new VBox();

        // Create scene and update stage
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.titleProperty().bind(GUIStrings.TITLE);
        stage.setScene(scene);
        scene.getStylesheets().add(CSS);

        // Create menubar
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);
        root.getChildren().add(menuBar);

        Label text = new Label();
        root.getChildren().add(text);
        text.setStyle("-fx-font-family: 'monospaced';");

        // Create menus
        Menu fileMenu = new Menu();
        fileMenu.textProperty().bind(GUIStrings.FILE_MENU_NAME);
        Menu editMenu = new Menu();
        editMenu.textProperty().bind(GUIStrings.EDIT_MENU_NAME);
        Menu puzzleMenu = new Menu();
        puzzleMenu.textProperty().bind(GUIStrings.PUZZLE_MENU_NAME);
        Menu settingsMenu = new Menu();
        settingsMenu.textProperty().bind(GUIStrings.SETTINGS_MENU_NAME);

        // Put menus in their spots
        menuBar.getMenus().addAll(fileMenu, editMenu, puzzleMenu, settingsMenu);

        // Create menu items
        MenuItem createPuzzleMenuItem = new MenuItem();
        createPuzzleMenuItem.textProperty().bind(GUIStrings.FILE_NEW_NAME);
        MenuItem loadPuzzleMenuItem = new MenuItem();
        loadPuzzleMenuItem.textProperty().bind(GUIStrings.FILE_OPEN_NAME);
        MenuItem savePuzzleMenuItem = new MenuItem();
        savePuzzleMenuItem.textProperty().bind(GUIStrings.FILE_SAVE_NAME);
        MenuItem closePuzzleMenuItem = new MenuItem();
        closePuzzleMenuItem.textProperty().bind(GUIStrings.FILE_CLOSE_NAME);
        MenuItem undoMenuItem = new MenuItem();
        undoMenuItem.textProperty().bind(GUIStrings.EDIT_UNDO_NAME);
        MenuItem redoMenuItem = new MenuItem();
        redoMenuItem.textProperty().bind(GUIStrings.EDIT_REDO_NAME);
        MenuItem undoAllMenuItem = new MenuItem();
        undoAllMenuItem.textProperty().bind(GUIStrings.EDIT_UNDO_ALL_NAME);
        MenuItem redoAllMenuItem = new MenuItem();
        redoAllMenuItem.textProperty().bind(GUIStrings.EDIT_REDO_ALL_NAME);
        MenuItem solvePuzzleMenuItem = new MenuItem();
        solvePuzzleMenuItem.textProperty().bind(GUIStrings.SOLVE_SOLVE_NAME);
        MenuItem modifySolverMenuItem = new MenuItem();
        modifySolverMenuItem.textProperty().bind(GUIStrings.SOLVE_CONFIG_NAME);
        Menu solverConfSubMenu = new Menu("test");
        CheckMenuItem reasonToggleItem = new CheckMenuItem("Reasoning");
        CheckMenuItem backtrackToggleItem = new CheckMenuItem("Backtracking");
        MenuItem localeMenuItem = new MenuItem("choose locale...");

        // Put menu items in their spots
        fileMenu.getItems().addAll(createPuzzleMenuItem, loadPuzzleMenuItem, savePuzzleMenuItem, closePuzzleMenuItem);
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem, undoAllMenuItem, redoAllMenuItem);
        puzzleMenu.getItems().addAll(solvePuzzleMenuItem, modifySolverMenuItem, solverConfSubMenu);
        settingsMenu.getItems().addAll(localeMenuItem);
        solverConfSubMenu.getItems().addAll(reasonToggleItem, backtrackToggleItem);

        // Set default states
        closePuzzleMenuItem.disableProperty().bind(Bindings.isNull(puzzle));
        undoMenuItem.disableProperty().bind(Bindings.isNull(puzzle));
        redoMenuItem.disableProperty().bind(Bindings.isNull(puzzle));
        undoAllMenuItem.disableProperty().bind(Bindings.isNull(puzzle));
        redoAllMenuItem.disableProperty().bind(Bindings.isNull(puzzle));
        solvePuzzleMenuItem.disableProperty().bind(Bindings.isNull(puzzle));

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
                puzzle.setValue(PuzzleFactory.create(sc));
                comms.clear();
                text.setText("");
                puzzle.getValue().print(text);
            }
            catch (IOException err) {
                Alert d = new Alert(Alert.AlertType.ERROR);
                d.titleProperty().bind(GUIStrings.ERROR_TITLE);
                d.setContentText("This file does not exist or cannot be opened. Please try again.");
                d.show();
            }
            catch (InvalidPuzzleNameException err) {
                Alert d = new Alert(Alert.AlertType.ERROR);
                d.titleProperty().bind(GUIStrings.ERROR_TITLE);
                d.setContentText(
                        "This file has an invalid puzzle name. Please fix the contents or try a different file."
                );
                d.show();
            }
            catch (InvalidPuzzleSyntaxException err) {
                Alert d = new Alert(Alert.AlertType.ERROR);
                d.titleProperty().bind(GUIStrings.ERROR_TITLE);
                d.setContentText(
                        "This file does not contain a valid puzzle. Please fix the contents or try a different file."
                );
                d.show();
            }
        });
        savePuzzleMenuItem.setOnAction(e -> {});
        closePuzzleMenuItem.setOnAction(e -> {
            text.setText("");
            puzzle.setValue(null);
        });
        undoMenuItem.setOnAction(e -> {
            comms.undo();
            text.setText("");
            puzzle.getValue().print(text);
        });
        redoMenuItem.setOnAction(e -> {
            comms.apply();
            text.setText("");
            puzzle.getValue().print(text);
        });
        undoAllMenuItem.setOnAction(e -> {
            comms.undoAll();
            text.setText("");
            puzzle.getValue().print(text);
        });
        redoAllMenuItem.setOnAction(e -> {
            comms.applyAll();
            text.setText("");
            puzzle.getValue().print(text);
        });
        solvePuzzleMenuItem.setOnAction(e -> {
            Puzzle<?> puzzle = Main.puzzle.getValue();
            Solver s = solverFactory.withPuzzle(puzzle).build();
            s.solve(comms);
            text.setText("");
            puzzle.print(text);
        });
        modifySolverMenuItem.setOnAction(e -> {
            Stage sConfiguration = new SolverConfiguration(solverFactory);
            sConfiguration.showAndWait();
        });
        localeMenuItem.setOnAction(e -> {
            Dialog<Locale> selector = new LocaleSelector();
            Optional<Locale> newLocale = selector.showAndWait();
            newLocale.ifPresent(LocaleManager.LOCALE_PROPERTY::setValue);
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

    public static void main(String[] args) {
        launch(args);
    }
}
