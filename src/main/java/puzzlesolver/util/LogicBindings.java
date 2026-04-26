package puzzlesolver.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogicBindings {

    public static <T extends ObservableBooleanValue> ObservableBooleanValue forall(ObservableList<T> bools) {
        // init properties
        IntegerProperty falseCount = new SimpleIntegerProperty(0);
        ObservableBooleanValue result = falseCount.isEqualTo(0);
        ChangeListener<Boolean> listener = (ob, o, n) -> {
            if (o && !n)
                falseCount.set(falseCount.get() + 1);
            if (!o && n)
                falseCount.set(falseCount.get() - 1);
        };
        // set initial values
        for (var x : bools) {
            if (!x.get())
                falseCount.set(falseCount.get() + 1);
            x.addListener(listener);
        }
        // ensure dynamicity
        bools.addListener((ListChangeListener<? super T>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (var x : c.getAddedSubList()) {
                        if (!x.get())
                            falseCount.set(falseCount.get() + 1);
                        x.addListener(listener);
                    }
                }
                if (c.wasRemoved()) {
                    for (var x : c.getRemoved()) {
                        if (!x.get())
                            falseCount.set(falseCount.get() - 1);
                        x.addListener(listener);
                    }
                }
            }
        });

        return result;
    }
}
