package sample.infrastructure;

public class SubscriptionEventArgs {

    public static final SubscriptionEventArgs EMPTY = new SubscriptionEventArgs();

    protected SubscriptionEventArgs() {
    }

    public String toString() {
        return "{}";
    }
}
