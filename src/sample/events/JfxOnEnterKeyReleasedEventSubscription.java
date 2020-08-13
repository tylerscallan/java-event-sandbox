package sample.events;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.infrastructure.ISubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgsGeneric;

public class JfxOnEnterKeyReleasedEventSubscription implements EventHandler<KeyEvent> {

    private final Node sender;
    private final ISubscriptionEvent<SubscriptionEventArgsGeneric<KeyEvent>> event;

    public JfxOnEnterKeyReleasedEventSubscription(Node sender, ISubscriptionEvent<SubscriptionEventArgsGeneric<KeyEvent>> event) {
        this.sender = sender;
        this.event = event;
        this.attach();
    }

    private void attach() {
        this.sender.addEventHandler(KeyEvent.KEY_RELEASED, this);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.event.invoke(this.sender, new SubscriptionEventArgsGeneric<>(keyEvent));
        }
    }
}
