package sample.events;

import sample.infrastructure.PropertyChangedSubscriptionEventArgs;
import sample.infrastructure.SubscriptionEvent;

public class OnTextChangedFromDefaultValueEvent extends SubscriptionEvent<PropertyChangedSubscriptionEventArgs<String>> {

    public OnTextChangedFromDefaultValueEvent() {
        this("OnTextChangedFromDefaultValue");
    }

    public OnTextChangedFromDefaultValueEvent(String name) {
        super(name);
    }
}
