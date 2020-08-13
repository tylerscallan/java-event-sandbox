package sample.events;

import sample.infrastructure.SubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;

public class OnFocusEvent extends SubscriptionEvent<SubscriptionEventArgs> {

    public OnFocusEvent() {
        this("OnFocus");
    }

    public OnFocusEvent(String name) {
        super(name);
    }
}
