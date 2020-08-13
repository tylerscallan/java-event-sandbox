package sample.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import sample.infrastructure.ISubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;

public class JfxOnFocusEventSubscription implements ChangeListener<Boolean> {

    private final ISubscriptionEvent<SubscriptionEventArgs> event;
    private final Node sender;

    public JfxOnFocusEventSubscription(Node sender, ISubscriptionEvent<SubscriptionEventArgs> event) {
        this.sender = sender;
        this.event = event;
        this.attach();
    }

    private void attach() {
        this.sender.focusedProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue && !oldValue) {
            this.event.invoke(this.sender, SubscriptionEventArgs.EMPTY);
        }
    }
}
