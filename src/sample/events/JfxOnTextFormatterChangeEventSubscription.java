package sample.events;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import sample.infrastructure.ISubscriptionEvent;
import sample.infrastructure.SubscriptionEventArgs;
import sample.infrastructure.SubscriptionEventArgsGeneric;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class JfxOnTextFormatterChangeEventSubscription implements UnaryOperator<TextFormatter.Change> {

    private final TextInputControl sender;
    private final ISubscriptionEvent<SubscriptionEventArgsGeneric<TextFormatter.Change>> acceptedEvent;
    private final ISubscriptionEvent<SubscriptionEventArgsGeneric<Map<Predicate<TextFormatter.Change>, TextFormatter.Change>>> rejectedEvent;
    private final Collection<Predicate<TextFormatter.Change>> predicates;
    private final boolean isInvokingCombinedRejectionEvent;

    public JfxOnTextFormatterChangeEventSubscription(
            TextInputControl sender,
            Collection<Predicate<TextFormatter.Change>> predicates,
            ISubscriptionEvent<SubscriptionEventArgsGeneric<TextFormatter.Change>> acceptedEvent,
            ISubscriptionEvent<SubscriptionEventArgsGeneric<Map<Predicate<TextFormatter.Change>, TextFormatter.Change>>> rejectedEvent,
            boolean isInvokingCombinedRejectionEvent
            ) throws IllegalArgumentException, IllegalComponentStateException {

        if (sender == null) {
            throw new IllegalArgumentException("Sender must not be null.");
        }

        this.sender = sender;
        this.acceptedEvent = acceptedEvent;
        this.rejectedEvent = rejectedEvent;
        this.predicates = predicates;
        this.isInvokingCombinedRejectionEvent = isInvokingCombinedRejectionEvent;
        this.attach();
    }

    private void attach() throws IllegalComponentStateException {

        if (this.sender.getTextFormatter() != null) {
            throw new IllegalComponentStateException("Text formatter must not be previously set.");
        }

        this.sender.setTextFormatter(new TextFormatter<UnaryOperator<TextFormatter.Change>>(this));
    }


    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {

        if (this.predicates == null || this.predicates.isEmpty()) {
            return change;
        }

        if (change.getControlNewText().isEmpty()) {
            return change;
        }

        List<Predicate<TextFormatter.Change>> failedPredicates = this.predicates.stream().filter((predicate) -> !predicate.test(change)).collect(Collectors.toList());

        if (!failedPredicates.isEmpty()) {
            if (this.rejectedEvent != null) {

                if (this.isInvokingCombinedRejectionEvent) {

                    Map<Predicate<TextFormatter.Change>, TextFormatter.Change> results = new HashMap<>();

                    failedPredicates.forEach(predicate -> {
                        results.put(predicate, change);
                    });

                    this.rejectedEvent.invoke(this.sender, new SubscriptionEventArgsGeneric<>(results));

                } else {

                    failedPredicates.forEach(predicate -> {
                        this.rejectedEvent.invoke(this.sender, new SubscriptionEventArgsGeneric<>(
                                new HashMap<Predicate<TextFormatter.Change>, TextFormatter.Change>() {{ put(predicate, change); }}
                        ));
                    });
                }



            }
            return null;
        }

        if (change.isContentChange() && this.acceptedEvent != null) {
            this.acceptedEvent.invoke(this.sender, new SubscriptionEventArgsGeneric<>(change));
        }

        return change;
    }
}
