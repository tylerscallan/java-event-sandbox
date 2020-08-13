package sample.events;

import javafx.scene.control.TextFormatter;
import sample.infrastructure.SubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgsGeneric;

public class OnTextChangeAcceptedEvent extends SubscriptionEvent<SubscriptionEventArgsGeneric<TextFormatter.Change>> {

    public OnTextChangeAcceptedEvent() {
        this("OnTextChangeAccepted");
    }

    public OnTextChangeAcceptedEvent(String name) {
        super(name);
    }
}
