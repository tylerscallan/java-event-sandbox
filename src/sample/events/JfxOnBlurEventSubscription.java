package sample.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import sample.infrastructure.IEventSubscription;
import sample.infrastructure.ISubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;

public class JfxOnBlurEventSubscription implements ChangeListener<Boolean>, IEventSubscription {

    private final Node sender;
    private final ISubscriptionEvent<SubscriptionEventArgs> event;

    public JfxOnBlurEventSubscription(Node sender, ISubscriptionEvent<SubscriptionEventArgs> event) {
        this.sender = sender;
        this.event = event;
        this.attach();
    }

    private void attach() {
        this.sender.focusedProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (!newValue && oldValue) {
            this.event.invoke(this.sender, SubscriptionEventArgs.EMPTY);
        }
    }
}
