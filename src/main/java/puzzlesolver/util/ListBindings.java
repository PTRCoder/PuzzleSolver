package puzzlesolver.util;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@UtilityClass
public class ListBindings {
    public static <I, O> ObservableList<O> mapEach(
            ObservableList<? extends I> in,
            Function<I, ? extends O> func) {
        // init output
        ObservableList<O> out = FXCollections.observableArrayList();
        // add current values first
        for (var x : in) {
            out.add(func.apply(x));
        }
        // add listchangelistener
        in.addListener((ListChangeListener<? super I>) c -> {
            while (c.next()) {
                if (c.wasPermutated()) {
                    List<O> old = new ArrayList<>(out.subList(c.getFrom(), c.getTo()));
                    for (int i = c.getFrom(); i < c.getTo(); i++) {
                        out.set(c.getPermutation(i), old.get(i));
                    }
                }
                if (c.wasRemoved()) {
                    int first = c.getFrom();
                    for (int i = c.getFrom(); i < c.getTo(); i++) {
                        in.remove(first);
                    }
                }
                if (c.wasAdded()) {
                    int i = c.getFrom();
                    if (i == out.size())
                        out.addAll(c.getAddedSubList().stream().map(func).toList());
                    else
                        out.addAll(i, c.getAddedSubList().stream().map(func).toList());
                    // index should never be > out.size()
                }
            }
        });
        return out;
    }
}
