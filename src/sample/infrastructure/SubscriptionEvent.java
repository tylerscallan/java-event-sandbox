package sample.infrastructure;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SubscriptionEvent<T extends SubscriptionEventArgs> implements ISubscriptionEvent<T> {

    private final Collection<ISubscriptionEventListener<T>> eventListeners = new ArrayList<>();

    private final String name;

    public SubscriptionEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void invoke(Object sender, T eventArgs) {
        System.out.println("Event<" + this.name + "> invoked, notifying " + this.eventListeners.size() + " listeners with args: " + eventArgs.toString());
        this.eventListeners.forEach(listener -> listener.invoke(sender, eventArgs));
    }


    public void subscribe(ISubscriptionEventListener<T> eventListener) {
        if (!this.eventListeners.contains(eventListener)) {
            this.eventListeners.add(eventListener);
        }
    }


    public void unsubscribe(ISubscriptionEventListener<T> eventListener) {
        this.eventListeners.remove(eventListener);
    }
}