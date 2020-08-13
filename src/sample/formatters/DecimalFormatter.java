package sample.formatters;

import javafx.scene.control.TextFormatter;

import java.text.DecimalFormatSymbols;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalFormatter implements Predicate<TextFormatter.Change> {

    private static final String DELIMITER = DecimalFormatSymbols.getInstance().getDecimalSeparator() + "";
    private static final Pattern PATTERN = Pattern.compile(String.format("^(-)?([0-9]+)?\\%s?((?<=\\%s)[0-9]+)?$", DELIMITER, DELIMITER));

    Integer minIntegralLength = null;
    Integer maxIntegralLength = null;
    Integer maxFractionalLength = null;
    Integer minFractionalLength = null;

    public DecimalFormatter(Integer minIntegralLength, Integer maxIntegralLength, Integer minFractionalLength, Integer maxFractionalLength) {
        this.minIntegralLength = minIntegralLength;
        this.maxIntegralLength = maxIntegralLength;
        this.minFractionalLength = minFractionalLength;
        this.maxFractionalLength = maxFractionalLength;
    }

    @Override
    public boolean test(TextFormatter.Change change) {

        Matcher matcher = PATTERN.matcher(change.getControlNewText());

        if (!matcher.find()) {
            return false;
        }

        boolean isNegative = matcher.group(1) != null;
        String integral = matcher.group(2);
        String fractional = matcher.group(3);


        if (integral != null) {

            if (this.minIntegralLength != null && integral.length() < this.minIntegralLength) {
                return false;
            }

            if (this.maxIntegralLength != null && integral.length() > this.maxIntegralLength) {
                return false;
            }
        }

        if (fractional != null) {

            if (this.minFractionalLength != null && fractional.length() < this.minFractionalLength) {
                return false;
            }

            if (this.maxFractionalLength != null && fractional.length() > this.maxFractionalLength) {
                return false;
            }
        }

        return true;
    }
}
