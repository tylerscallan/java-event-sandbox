package sample.events;

import sample.infrastructure.SubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;

public class OnBlurEvent extends SubscriptionEvent<SubscriptionEventArgs> {

    public OnBlurEvent() {
        this("OnBlur");
    }

    public OnBlurEvent(String name) {
        super(name);
    }
}
