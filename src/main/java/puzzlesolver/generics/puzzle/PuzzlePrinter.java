package puzzlesolver.generics.puzzle;

import javafx.scene.control.Label;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

@NoArgsConstructor
@Setter
@Getter
public class PuzzlePrinter<T> {
    private boolean useBorder;
    private boolean useSpaces;
    private int vBlockSize;
    private int hBlockSize;
    private Function<T, String> toString;
    private boolean connect;

    public void print(Grid<T> grid, Label parent) {
        if (useBorder && connect)
            throw new IllegalArgumentException();
        int width = grid.getWidth();
        int height = grid.getHeight();
        if (hBlockSize <= 0)
            hBlockSize = width;
        if (vBlockSize <= 0)
            vBlockSize = height;
        if (width % hBlockSize != 0 || height % vBlockSize != 0)
            throw new IllegalArgumentException();
        final String hl = hl(width, useBorder, useSpaces, hBlockSize);
        int hBlocks = width / hBlockSize;
        StringJoiner out = new StringJoiner("\n");
        if (useBorder)
            out.add(hl);
        for (int k = 0; k < height; k++) {
            if (k > 0 && k % vBlockSize == 0)
                out.add(hl);
            List<? extends Cell<T>> row = grid.getCells().get(k);
            StringJoiner sj = new StringJoiner(useSpaces ? " " : "");
            StringJoiner sjbs = new StringJoiner(useSpaces ? " | " : "|");
            if (useBorder)
                sj.add("|");
            for (int i = 0; i < hBlocks; i++) {
                StringJoiner sjb = new StringJoiner(useSpaces ? " " : "");
                for (int j = 0; j < hBlockSize; j++) {
                    T value = row.get(i * hBlockSize + j).getValue();
                    String val = toString.apply(value);
                    sjb.add(val);
                }
                sjbs.add(sjb.toString());
            }
            sj.add(sjbs.toString());
            if (useBorder)
                sj.add("|");
            out.add(sj.toString());
        }
        if (useBorder)
            out.add(hl);
        parent.setText(out.toString());
    }

    @SuppressWarnings("StringConcatenation")
    private static String hl(int width, boolean useBorder, boolean useSpaces, int blockSize) {
        int blocks = width / blockSize;
        int intBlockSize = useSpaces ? 2 * blockSize + 1 : blockSize;
        String simpleHL = "-".repeat(intBlockSize);
        if (useBorder)
            return ("+" + simpleHL).repeat(blocks) + "+";
        return (simpleHL + "+").repeat(blocks - 1) + simpleHL;
    }
}
