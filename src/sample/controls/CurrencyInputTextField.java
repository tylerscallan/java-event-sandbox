package sample.controls;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import sample.formatters.CurrencyFormatter;
import sample.formatters.DecimalFormatter;
import sample.formatters.NotNegativeFormatter;
import sample.infrastructure.ISubscriptionEventListener;
import sample.infrastructure.PropertyChangedSubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgsGeneric;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyInputTextField extends BaseInputTextField {

    private final ISubscriptionEventListener<SubscriptionEventArgs> sanitize = (sender, eventArgs) -> {

        CurrencyInputTextField input = (CurrencyInputTextField) sender;

        if (input.isEmpty()) {
            input.resetTextToDefaultValue();
            return;
        }

        if (!input.getText().equals(this.toTruncatedCurrencyValue(input.getText()))) {
            input.setText(this.toTruncatedCurrencyValue(input.getText()));
        }
    };

    private final ISubscriptionEventListener<SubscriptionEventArgs> selectAll = (sender, eventArgs) -> {
        CurrencyInputTextField input = (CurrencyInputTextField) sender;
        input.selectAllLater();
    };

    private final ISubscriptionEventListener<PropertyChangedSubscriptionEventArgs<String>> addValueChangedStyle = (sender, eventArgs) -> {
        CurrencyInputTextField input = (CurrencyInputTextField) sender;
        input.addStyleClass("input-changed-info");
    };

    private final ISubscriptionEventListener<PropertyChangedSubscriptionEventArgs<String>> removeValueChangedStyle = (sender, eventArgs) -> {
        CurrencyInputTextField input = (CurrencyInputTextField) sender;
        input.removeStyleClass("input-changed-info");
    };

    Predicate<TextFormatter.Change> decimalFormatter = new DecimalFormatter(null, null, null, 2);
    Predicate<TextFormatter.Change> currencyFormatter = new CurrencyFormatter();
    Predicate<TextFormatter.Change> notNegativeFormatter = new NotNegativeFormatter();

    private final ISubscriptionEventListener<SubscriptionEventArgsGeneric<Map<Predicate<TextFormatter.Change>, TextFormatter.Change>>> alertTextRejected = (sender, eventArgs) -> {

        String message = "";

        for (Predicate<TextFormatter.Change> key : eventArgs.getArgument().keySet()) {

            if (key.equals(this.currencyFormatter)) {
                message += "\nAn invalid currency format";
            }

            if (key.equals(this.decimalFormatter)) {
                message += "\nAn invalid decimal number";
            }

            if (key.equals(this.notNegativeFormatter)) {
                message += "\nA negative number";
            }
        }

        if (!message.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, String.format("Validation has failed. The following errors have occurred and have been prevented: %n%s%n%nThe value in the text field will now be reset.",  message), new ButtonType("Goodbye", ButtonBar.ButtonData.OK_DONE));
            alert.setTitle("No");
            alert.showAndWait();
        }

        CurrencyInputTextField input = (CurrencyInputTextField) sender;
        ((CurrencyInputTextField) sender).resetTextToDefaultValue();
    };

    public CurrencyInputTextField() {
        this("");
    }

    public CurrencyInputTextField(String defaultValue) {
        this(() -> defaultValue);
    }

    public CurrencyInputTextField(Supplier<String> defaultValueSupplier) {
        super(defaultValueSupplier);

        this.addTextFormatterPredicate(this.currencyFormatter);
        this.addTextFormatterPredicate(this.decimalFormatter);
        this.addTextFormatterPredicate(this.notNegativeFormatter);

        this.getOnTextChangeRejectedEvent().subscribe(alertTextRejected);

        this.getOnBlurEvent().subscribe(this.sanitize);
        this.getOnFocusEvent().subscribe(this.selectAll);
        this.getOnTextChangedFromDefaultValueEvent().subscribe(this.addValueChangedStyle);
        this.getOnTextChangedToDefaultValueEvent().subscribe(this.removeValueChangedStyle);
    }

    private static String DELIMITER = DecimalFormatSymbols.getInstance().getDecimalSeparator() + "";
    private static Pattern PATTERN = Pattern.compile(String.format("^(-)?([0-9]+)?\\%s?((?<=\\%s)[0-9]+)?$", DELIMITER, DELIMITER));

    private String toTruncatedCurrencyValue(String value) {

        Matcher matcher = PATTERN.matcher(value);

        matcher.find();

        boolean isNegative = matcher.group(1) != null;
        String integral = matcher.group(2) != null ? "0" + matcher.group(2) : "";
        String fractional = matcher.group(3) != null ? DELIMITER + matcher.group(3) : "";

        BigDecimal decimalValue = new BigDecimal(integral + fractional).setScale(2, RoundingMode.HALF_UP);

        if (isNegative) {
            return decimalValue.negate().toString();
        }

        return decimalValue.toString();
    }
}
