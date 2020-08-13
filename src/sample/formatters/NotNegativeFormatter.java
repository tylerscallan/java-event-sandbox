package sample.formatters;

import javafx.scene.control.TextFormatter;

import java.text.DecimalFormatSymbols;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotNegativeFormatter implements Predicate<TextFormatter.Change> {

    private static final Pattern PATTERN = Pattern.compile("^-");

    @Override
    public boolean test(TextFormatter.Change change) {
        return !PATTERN.matcher(change.getControlNewText()).matches();
    }
}
