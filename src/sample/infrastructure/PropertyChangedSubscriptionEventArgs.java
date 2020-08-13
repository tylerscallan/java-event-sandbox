package sample.infrastructure;

import javafx.beans.value.ObservableValue;

public class PropertyChangedSubscriptionEventArgs<T> extends SubscriptionEventArgs {

    private final T newValue;

    private final ObservableValue<? extends T> observableValue;

    private final T oldValue;

    public PropertyChangedSubscriptionEventArgs(ObservableValue<? extends T> observableValue, T oldValue, T newValue) {
        super();

        this.observableValue = observableValue;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public Object getObservableValue() {
        return observableValue;
    }

    public T getOldValue() {
        return oldValue;
    }

    @Override
    public String toString() {
        return "{ 'newValue': '" + newValue + "', 'oldValue': '" + oldValue + "' }";
    }
}
