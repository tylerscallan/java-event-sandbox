package sample.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;
import sample.infrastructure.ISubscriptionEvent;
import sample.infrastructure.PropertyChangedSubscriptionEventArgs;

import java.util.function.Supplier;

public class JfxOnTextChangedToDefaultValueEventSubscription implements ChangeListener<String> {

    private final TextInputControl sender;
    private final ISubscriptionEvent<PropertyChangedSubscriptionEventArgs<String>> event;
    private Supplier<String> defaultValueSupplier;

    public JfxOnTextChangedToDefaultValueEventSubscription(TextInputControl sender, ISubscriptionEvent<PropertyChangedSubscriptionEventArgs<String>> event, Supplier<String> defaultValueSupplier) {
        this.sender = sender;
        this.event = event;
        this.defaultValueSupplier = defaultValueSupplier;
        this.attach();
    }

    private void attach() {
        this.sender.textProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (this.defaultValueSupplier != null && newValue.equals(this.defaultValueSupplier.get())) {
            this.event.invoke(this.sender, new PropertyChangedSubscriptionEventArgs<>(observableValue, oldValue, newValue));
        }
    }
}
