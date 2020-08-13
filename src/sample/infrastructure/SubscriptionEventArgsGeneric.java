package sample.infrastructure;

public class SubscriptionEventArgsGeneric<T> extends SubscriptionEventArgs {

    private final T argument;

    public SubscriptionEventArgsGeneric(T argument) {
        super();
        this.argument = argument;
    }

    public T getArgument() {
        return this.argument;
    }

    public String toString() {
        return "{}";
    }
}
