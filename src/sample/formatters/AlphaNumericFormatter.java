package sample.formatters;

import javafx.scene.control.TextFormatter;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class AlphaNumericFormatter implements Predicate<TextFormatter.Change> {

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    @Override
    public boolean test(TextFormatter.Change change) {
        return PATTERN.matcher(change.getControlNewText()).matches();
    }
}
