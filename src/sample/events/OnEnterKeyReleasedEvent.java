package sample.events;

import javafx.scene.input.KeyEvent;
import sample.infrastructure.SubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgsGeneric;

public class OnEnterKeyReleasedEvent extends SubscriptionEvent<SubscriptionEventArgsGeneric<KeyEvent>> {

    public OnEnterKeyReleasedEvent() {
        this("OnEnterKeyReleased");
    }

    public OnEnterKeyReleasedEvent(String name) {
        super(name);
    }
}
