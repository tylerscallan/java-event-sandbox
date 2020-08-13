package sample.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextInputControl;
import sample.infrastructure.ISubscriptionEvent;
import sample.infrastructure.PropertyChangedSubscriptionEventArgs;

public class JfxOnTextChangedEventSubscription implements ChangeListener<String> {

    private final TextInputControl sender;
    private final ISubscriptionEvent<PropertyChangedSubscriptionEventArgs<String>> event;

    public JfxOnTextChangedEventSubscription(TextInputControl sender, ISubscriptionEvent<PropertyChangedSubscriptionEventArgs<String>> event) {
        this.sender = sender;
        this.event = event;
        this.attach();
    }

    private void attach() {
        this.sender.textProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
        if (!newValue.equals(oldValue)) {
            this.event.invoke(this.sender, new PropertyChangedSubscriptionEventArgs<>(observableValue, oldValue, newValue));
        }
    }
}
