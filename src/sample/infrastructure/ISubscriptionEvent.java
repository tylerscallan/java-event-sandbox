package sample.infrastructure;

import javafx.scene.Node;

import java.util.List;

public interface ISubscriptionEvent<T extends SubscriptionEventArgs> {

    String getName();

    void invoke(Object sender, T eventArgs);

    void subscribe(ISubscriptionEventListener<T> eventListener);

    void unsubscribe(ISubscriptionEventListener<T> eventListener);
}
