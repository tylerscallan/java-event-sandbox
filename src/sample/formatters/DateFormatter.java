package sample.formatters;

import javafx.scene.control.TextFormatter;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class DateFormatter implements Predicate<TextFormatter.Change> {

    private List<SimpleDateFormat> simpleDateFormats = new ArrayList<>();

    public DateFormatter() {
        this.simpleDateFormats.add(new SimpleDateFormat("yyyy-MM-dd"));
        this.simpleDateFormats.add(new SimpleDateFormat("yyyy/MM/dd"));
    }

    public DateFormatter(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormats.add(simpleDateFormat);
    }

    public DateFormatter(List<SimpleDateFormat> simpleDateFormats) {
        this.simpleDateFormats.addAll(simpleDateFormats);
    }

    @Override
    public boolean test(TextFormatter.Change change) {

        for (SimpleDateFormat simpleDateFormat : this.simpleDateFormats) {
            try {
                simpleDateFormat.parse(change.getControlNewText());
                return true;
            } catch (ParseException ex) {
            }
        }

        return false;
    }
}
