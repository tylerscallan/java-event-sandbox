package sample.events;

import javafx.scene.control.TextFormatter;
import sample.infrastructure.SubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgsGeneric;

import java.util.Map;
import java.util.function.Predicate;

public class OnTextChangeRejectedEvent extends SubscriptionEvent<SubscriptionEventArgsGeneric<Map<Predicate<TextFormatter.Change>, TextFormatter.Change>>> {

    public OnTextChangeRejectedEvent() {
        this("OnTextFormatterChangeRejected");
    }

    public OnTextChangeRejectedEvent(String name) {
        super(name);
    }
}
