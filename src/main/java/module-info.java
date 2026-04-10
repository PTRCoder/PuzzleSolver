import org.jspecify.annotations.NullMarked;

@NullMarked
module puzzlesolver {
    requires javafx.controls;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires static lombok;
    requires static org.jetbrains.annotations;
    requires static org.jspecify;
    requires org.slf4j;

    exports puzzlesolver;
}