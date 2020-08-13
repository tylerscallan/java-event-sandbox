package sample.events;

import javafx.scene.input.KeyEvent;
import sample.infrastructure.SubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgsGeneric;

public class OnEnterKeyPressedEvent extends SubscriptionEvent<SubscriptionEventArgsGeneric<KeyEvent>> {

    public OnEnterKeyPressedEvent() {
        this("OnEnterKeyPressed");
    }

    public OnEnterKeyPressedEvent(String name) {
        super(name);
    }
}
