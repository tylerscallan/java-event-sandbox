package sample.events;

import sample.infrastructure.PropertyChangedSubscriptionEventArgs;
import sample.infrastructure.SubscriptionEvent;

public class OnTextChangedToDefaultValueEvent extends SubscriptionEvent<PropertyChangedSubscriptionEventArgs<String>> {

    public OnTextChangedToDefaultValueEvent() {
        this("OnTextChangedToDefaultValue");
    }

    public OnTextChangedToDefaultValueEvent(String name) {
        super(name);
    }
}
