package sample.formatters;

import javafx.scene.control.TextFormatter;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class MaxLengthFormatter implements Predicate<TextFormatter.Change> {

    private final int maxLength;

    public MaxLengthFormatter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean test(TextFormatter.Change change) {
        return change.getControlNewText().length() <= maxLength;
    }
}
