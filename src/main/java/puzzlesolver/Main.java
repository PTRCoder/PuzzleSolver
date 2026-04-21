package puzzlesolver;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NonNls;
import org.jspecify.annotations.Nullable;
import puzzlesolver.commands.CompoundCommand;
import puzzlesolver.fs.FileSystem;
import puzzlesolver.generics.puzzle.Puzzle;
import puzzlesolver.loc.GUIStrings;
import puzzlesolver.loc.LocaleManager;
import puzzlesolver.puzzles.PuzzleFactory;
import puzzlesolver.solvers.Solver;
import puzzlesolver.solvers.SolverFactory;
import puzzlesolver.ui.ExceptionAlertFactory;
import puzzlesolver.ui.LocaleSelector;

import java.io.*;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import static javafx.stage.FileChooser.ExtensionFilter;

@Slf4j
public final class Main extends Application {

    private static final @NonNls String CSS = "styles.css";
    private static final @NonNls String EXT_TXT = "*.txt";

    private static final ObjectProperty<@Nullable Puzzle<?>> puzzle = new SimpleObjectProperty<>();
    private static final ObservableBooleanValue noPuzzle = Bindings.isNull(puzzle);
    private static final CompoundCommand comms = new CompoundCommand();
    private static final SolverFactory solverFactory = new SolverFactory();
    private static final ObservableValue<GridPane> gridProperty = puzzle.map(
            p -> p != null ? p.getView() : new GridPane()
    );

    @Override
    public void start(Stage stage) {
        // Root VBox
        VBox root = new VBox();

        // Create scene and update stage
        Scene scene = new Scene(root);
        stage.titleProperty().bind(GUIStrings.TITLE);
        scene.getStylesheets().add(CSS);
        stage.setScene(scene);

        // Create menubar
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);
        root.getChildren().add(menuBar);

        // Create content area
        HBox content = new HBox();
        content.setId("content");
        root.getChildren().add(content);

        // Create puzzle area
        VBox puzzleArea = new VBox();
        puzzleArea.setId("puzzle-area");
        content.getChildren().add(puzzleArea);

        // Add grid
        Label grid = new Label();
        grid.graphicProperty().bind(gridProperty);
        puzzleArea.getChildren().add(grid);

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
        Menu solverConfSubMenu = new Menu();
        solverConfSubMenu.textProperty().bind(GUIStrings.SOLVE_SELECT_SOLVERS_NAME);
        CheckMenuItem reasonToggleItem = new CheckMenuItem();
        reasonToggleItem.textProperty().bind(GUIStrings.SOLVE_SELECT_REASONER_NAME);
        CheckMenuItem backtrackToggleItem = new CheckMenuItem();
        backtrackToggleItem.textProperty().bind(GUIStrings.SOLVE_SELECT_BACKTRACKER_NAME);
        MenuItem localeMenuItem = new MenuItem("choose locale...");

        // Put menu items in their spots
        fileMenu.getItems().addAll(createPuzzleMenuItem, loadPuzzleMenuItem, savePuzzleMenuItem, closePuzzleMenuItem);
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem, undoAllMenuItem, redoAllMenuItem);
        puzzleMenu.getItems().addAll(solvePuzzleMenuItem, solverConfSubMenu);
        settingsMenu.getItems().addAll(localeMenuItem);
        solverConfSubMenu.getItems().addAll(reasonToggleItem, backtrackToggleItem);

        // Set disabled states
        // Important result is that any action performed with these items directly indicate puzzle != null
        savePuzzleMenuItem.disableProperty().bind(noPuzzle);
        closePuzzleMenuItem.disableProperty().bind(noPuzzle);
        ObservableBooleanValue cannotUndo = Bindings.or(noPuzzle, comms.allNotDoneProperty());
        ObservableBooleanValue cannotDo = Bindings.or(noPuzzle, comms.allDoneProperty());
        undoMenuItem.disableProperty().bind(cannotUndo);
        redoMenuItem.disableProperty().bind(cannotDo);
        undoAllMenuItem.disableProperty().bind(cannotUndo);
        redoAllMenuItem.disableProperty().bind(cannotDo);
        solvePuzzleMenuItem.disableProperty().bind(noPuzzle);

        // Set config bindings
        reasonToggleItem.selectedProperty().bindBidirectional(Config.getInstance().reasoningProperty());
        backtrackToggleItem.selectedProperty().bindBidirectional(Config.getInstance().backtrackingProperty());

        // Set actions
        createPuzzleMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
        });
        loadPuzzleMenuItem.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle(GUIStrings.FC_LOAD_PUZZLE_TITLE.getValue());
            fc.getExtensionFilters().add(new ExtensionFilter(GUIStrings.FC_FILETYPE_TEXT.getValue(), EXT_TXT));
            File lastFolder = Config.getInstance().lastFolderProperty().getValue();
            fc.setInitialDirectory(
                    lastFolder.exists() && lastFolder.isDirectory() ? lastFolder : FileSystem.DEFAULT_PUZZLES_FOLDER
            );
            File selectedFile = fc.showOpenDialog(stage);
            if (selectedFile == null)
                return;
            Config.getInstance().lastFolderProperty().setValue(selectedFile.getParentFile());
            try {
                Scanner sc = new Scanner(selectedFile);
                puzzle.setValue(PuzzleFactory.create(sc));
                assert puzzle.getValue() != null;
                comms.clear();
            }
            catch (Exception err) {
                Alert d = ExceptionAlertFactory.getInstance(err);
                d.setTitle(GUIStrings.ERROR_TITLE.getValue());
                d.show();
            }
        });
        savePuzzleMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
            FileChooser fc = new FileChooser();
            fc.setTitle(GUIStrings.FC_SAVE_PUZZLE_TITLE.getValue());
            fc.getExtensionFilters().add(new ExtensionFilter(GUIStrings.FC_FILETYPE_TEXT.getValue(), EXT_TXT));
            File lastFolder = Config.getInstance().lastFolderProperty().getValue();
            fc.setInitialDirectory(
                    lastFolder.exists() && lastFolder.isDirectory() ? lastFolder : FileSystem.DEFAULT_PUZZLES_FOLDER
            );
            fc.getExtensionFilters().add(new ExtensionFilter(GUIStrings.FC_FILETYPE_TEXT.getValue(), EXT_TXT));
            File selected = fc.showSaveDialog(stage);
            if (selected == null) {
                return;
            }
            Config.getInstance().lastFolderProperty().setValue(selected.getParentFile());
            try (PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(selected)))) {
                writer.print(puzzle.getValue().encodeCurrentState());
                writer.flush();
            }
            catch (IOException err) {
                log.error(err.getLocalizedMessage());
            }
        });
        closePuzzleMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
            puzzle.setValue(null);
        });
        undoMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
            comms.undo();
        });
        redoMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
            comms.apply();
        });
        undoAllMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
            comms.undoAll();
        });
        redoAllMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
            comms.applyAll();
        });
        solvePuzzleMenuItem.setOnAction(e -> {
            assert puzzle.getValue() != null;
            Puzzle<?> puzzle = Main.puzzle.getValue();
            Solver s = solverFactory.withPuzzle(puzzle).build();
            s.solve(comms);
        });
        localeMenuItem.setOnAction(e -> {
            Dialog<Locale> selector = new LocaleSelector();
            Optional<Locale> newLocale = selector.showAndWait();
            newLocale.ifPresent(LocaleManager.LOCALE_PROPERTY::setValue);
        });

        solverFactory.reasonerProperty().bind(reasonToggleItem.selectedProperty());
        solverFactory.backtrackProperty().bind(backtrackToggleItem.selectedProperty());

        // Create accelerator keys
        KeyCombination createKeys = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);
        KeyCombination loadKeys = new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN);
        KeyCombination saveKeys = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
        KeyCombination closeKeys = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
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
        closePuzzleMenuItem.setAccelerator(closeKeys);
        undoMenuItem.setAccelerator(undoKeys);
        redoMenuItem.setAccelerator(redoKeys);
        undoAllMenuItem.setAccelerator(undoAllKeys);
        redoAllMenuItem.setAccelerator(redoAllKeys);

        // Setup stage config
        stage.setWidth(Config.getInstance().windowWidthProperty().get());
        stage.setHeight(Config.getInstance().windowHeightProperty().get());
        stage.setMaximized(Config.getInstance().maximizedProperty().get());
        Config.getInstance().maximizedProperty().bind(stage.maximizedProperty());
        Config.getInstance().windowWidthProperty().bind(stage.widthProperty().when(stage.maximizedProperty().not()));
        Config.getInstance().windowHeightProperty().bind(stage.heightProperty().when(stage.maximizedProperty().not()));

        // Finish up
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Config.saveInstance();
    }
}
