package puzzlesolver.generics.puzzle;

import java.util.Collection;

/**
 * The general value type allowed to be used in puzzles. Implementations are expected to be interned,
 * with enums inherently upholding this idea.
 */
public interface PuzzleValue {
    /**
     * Converts the value to a readable format.
     * By default, this only constructs a string from the return value of {@code toChar}.
     *
     * @return Readable string representation of the value.
     */
    default String toText() {
        return Character.toString(toChar());
    }

    /**
     * Converts the value to a single character.
     * This is for a textual representation of the value. It should make encoding and decoding the value easier.
     * @return The character representation of the value.
     */
    char toChar();

    /**
     * The collection of allowed values.
     * These should only contain values that can be filled in,
     * so not containing the empty value or any blocked-like values. As such, one could state that
     * {@code x \in getAllowedValues() <==> !x.isEmpty() && !x.isBlocked()}.
     * <p>
     * Implementors are heavily encourage to use a static immutable collection to store these values.
     * </p>
     *
     * @return Collection of allowed values.
     */
    Collection<? extends PuzzleValue> getAllowedValues();

    /**
     * The collection of valid values.
     * These should only contain values that can be considered valid within a given group,
     * so not containing any blocked-like values. As such, one could state that
     * * {@code x \in getAllowedValues() <==> !x.isBlocked()}.
     * <p>
     * Implementors are heavily encourage to use a static immutable collection to store these values.
     * </p>
     *
     * @return LiCost of valid values.
     */
    Collection<? extends PuzzleValue> getValidValues();

    /**
     * Indicates whether the value symbolizes an empty cell or not.
     * This should typically be a very simple function that by default only states that
     * {@code getEmptyValues().contains(this)}.
     * This method should not be overridden. If you want to specify which cell types are considered empty, implement
     * that using the {@code getEmptyValues} method instead.
     *
     * @return Whether this value symbolizes emptiness.
     */
    default boolean isEmpty() {
        return getEmptyValues().contains(this);
    }

    /**
     * Indicates whether the value symbolizes a non-input cell or not.
     * This should typically be a very simple function that by default only states that
     * {@code getBlockedValues().contains(this)}.
     * This method should not be overridden. If you want to specify which cell types are considered blocked, implement
     * that using the {@code getBlockedValues} method instead.
     *
     * @return Whether this value symbolizes that no input is allowed here.
     */
    default boolean isBlocked() {
        return getBlockedValues().contains(this);
    }

    /**
     * Indicates whether this is an allowed value.
     * This allows for more concise code when verifying if something is (not) blocked or empty.
     * @return Whether this is an allowed value.
     */
    default boolean isAllowed() {
        return getAllowedValues().contains(this);
    }

    /**
     * Yields a collection of values that can be used to represent empty cells.
     * <p>
     * Implementors are heavily encourage to use a static immutable collection to store these values.
     * </p>
     *
     * @return Collection of values representing emptiness.
     */
    Collection<? extends PuzzleValue> getEmptyValues();

    /**
     * Yields a collection of values that can be used to represent non-input cells.
     * <p>
     * Implementors are heavily encourage to use a static immutable collection to store these values.
     * </p>
     *
     * @return Collection of values representing no input allowed.
     */
    Collection<? extends PuzzleValue> getBlockedValues();

    /**
     * Default empty value.
     * Should adhere to {@code getDefaultEmptyValue() \in getEmptyValues()}. In case of a singular empty value,
     * return it. Else return the one that should be used if no other value is specified.
     *
     * @return The default empty value.
     */
    PuzzleValue getDefaultEmptyValue();
}
