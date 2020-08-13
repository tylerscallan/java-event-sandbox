package sample.events;

import sample.infrastructure.PropertyChangedSubscriptionEventArgs;
import sample.infrastructure.SubscriptionEvent;

public class OnTextChangedEvent extends SubscriptionEvent<PropertyChangedSubscriptionEventArgs<String>> {

    public OnTextChangedEvent() {
        this("OnTextChanged");
    }

    public OnTextChangedEvent(String name) {
        super(name);
    }
}
