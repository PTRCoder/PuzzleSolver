package puzzlesolver.ui;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import puzzlesolver.solvers.SolverFactory;


public class SolverConfiguration extends Stage {

    public SolverConfiguration(SolverFactory sf) {
        super();
        // Create root
        VBox root = new VBox();

        // Create observable values
        Property<Boolean> v1 = new SimpleBooleanProperty(this, "useReasoning", false);
        Property<Boolean> v2 = new SimpleBooleanProperty(this, "useBacktracking", false);

        // Bind values
        v1.bindBidirectional(sf.reasonerProperty());
        v2.bindBidirectional(sf.backtrackProperty());

        // Create subparts
        Label header = new Label("Solver configuration");
        final String s1 = "Reasoning";
        final String s2 = "Backtracking";
        ListView<String> list = new ListView<>();

        // init properties
        list.setCellFactory(p ->
                new CheckBoxListCell<>(s ->
                        switch (s) {
                            case s1 -> v1;
                            case s2 -> v2;
                            default -> throw new IllegalArgumentException();
                        }
                )
        );

        // construct tree
        root.getChildren().add(header);
        root.getChildren().add(list);
        list.getItems().add(s1);
        list.getItems().add(s2);

        Scene scene = new Scene(root);
        this.setScene(scene);
        this.sizeToScene();
        this.setTitle("Solver configuration");
        this.initModality(Modality.APPLICATION_MODAL);
    }

}
