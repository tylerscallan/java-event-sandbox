package sample.infrastructure;

import javafx.scene.Node;

@FunctionalInterface
public interface ISubscriptionEventListener<TEventArgs extends SubscriptionEventArgs> {

    void invoke(Object sender, TEventArgs eventArgs);
}
