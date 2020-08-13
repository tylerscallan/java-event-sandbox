package sample.formatters;

import javafx.scene.control.TextFormatter;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class BooleanFormatter implements Predicate<TextFormatter.Change> {

    private static final Pattern PATTERN = Pattern.compile("^[YyNn]$");

    @Override
    public boolean test(TextFormatter.Change change) {
        return PATTERN.matcher(change.getControlNewText()).matches();
    }
}
