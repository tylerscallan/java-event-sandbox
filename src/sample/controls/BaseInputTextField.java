package sample.controls;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.infrastructure.*;
import sample.events.*;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BaseInputTextField extends TextField {

    private final Supplier<String> defaultValueSupplier;
    private final Collection<Predicate<TextFormatter.Change>> textFormatterPredicates = new LinkedHashSet<>();
    private final SimpleBooleanProperty isTransferringFocusOnEnterKeyProperty = new SimpleBooleanProperty(false);

    private final OnBlurEvent onBlurEvent = new OnBlurEvent();
    private final OnFocusEvent onFocusEvent = new OnFocusEvent();
    private final OnEnterKeyReleasedEvent onEnterKeyReleasedEvent = new OnEnterKeyReleasedEvent();
    private final OnEnterKeyPressedEvent onEnterKeyPressedEvent = new OnEnterKeyPressedEvent();
    private final OnTextChangedEvent onTextChangedEvent = new OnTextChangedEvent();
    private final OnTextChangedFromDefaultValueEvent onTextChangedFromDefaultValueEvent = new OnTextChangedFromDefaultValueEvent();
    private final OnTextChangedToDefaultValueEvent onTextChangedToDefaultValueEvent = new OnTextChangedToDefaultValueEvent();
    private final OnTextChangeAcceptedEvent onTextChangeAcceptedEvent = new OnTextChangeAcceptedEvent();
    private final OnTextChangeRejectedEvent onTextChangeRejectedEvent = new OnTextChangeRejectedEvent();

    private final ChangeListener<Boolean> transferFocusOnEnterKey = (observableValue, oldValue, newValue) -> {
        if (newValue) {
            this.onEnterKeyPressedEvent.subscribe(this.triggerTabKeyListener);
        } else {
            this.onEnterKeyPressedEvent.unsubscribe(this.triggerTabKeyListener);
        }
    };

    private final ISubscriptionEventListener<SubscriptionEventArgsGeneric<KeyEvent>> triggerTabKeyListener = (sender, eventArgs) -> {
        this.triggerTabKeyEvent(eventArgs.getArgument());
        eventArgs.getArgument().consume();
    };

    public BaseInputTextField() {
        this("");
    }

    public BaseInputTextField(String defaultValue) {
        this(() -> defaultValue);
    }

    public BaseInputTextField(Supplier<String> defaultValueSupplier) {
        super(defaultValueSupplier.get());

        this.defaultValueSupplier = defaultValueSupplier;
        this.isTransferringFocusOnEnterKeyProperty.addListener(transferFocusOnEnterKey);

        JfxOnBlurEventSubscription onBlurEventSubscription = new JfxOnBlurEventSubscription(this, this.onBlurEvent);
        JfxOnFocusEventSubscription onFocusChangeListener = new JfxOnFocusEventSubscription(this, this.onFocusEvent);
        JfxOnEnterKeyReleasedEventSubscription onEnterKeyReleasedListener = new JfxOnEnterKeyReleasedEventSubscription(this, this.onEnterKeyReleasedEvent);
        JfxOnEnterKeyPressedEventSubscription onEnterKeyPressedListener = new JfxOnEnterKeyPressedEventSubscription(this, this.onEnterKeyPressedEvent);
        JfxOnTextChangedEventSubscription onTextChangedEventSubscription = new JfxOnTextChangedEventSubscription(this, this.onTextChangedEvent);
        JfxOnTextChangedFromDefaultValueEventSubscription onTextChangedFromDefaultValueEventSubscription = new JfxOnTextChangedFromDefaultValueEventSubscription(this, this.onTextChangedFromDefaultValueEvent, this.defaultValueSupplier);
        JfxOnTextChangedToDefaultValueEventSubscription onTextChangedToDefaultValueEventSubscription = new JfxOnTextChangedToDefaultValueEventSubscription(this, this.onTextChangedToDefaultValueEvent, this.defaultValueSupplier);
        JfxOnTextFormatterChangeEventSubscription onTextFormatterChangeEventSubscription = new JfxOnTextFormatterChangeEventSubscription(this, this.textFormatterPredicates, this.onTextChangeAcceptedEvent, this.onTextChangeRejectedEvent, true);
    }

    public OnBlurEvent getOnBlurEvent() {
        return this.onBlurEvent;
    }

    public OnFocusEvent getOnFocusEvent() {
        return this.onFocusEvent;
    }

    public OnEnterKeyReleasedEvent getOnEnterKeyReleasedEvent() {
        return this.onEnterKeyReleasedEvent;
    }

    public OnTextChangedEvent getOnTextChangedEvent() {
        return this.onTextChangedEvent;
    }

    public OnTextChangedFromDefaultValueEvent getOnTextChangedFromDefaultValueEvent() {
        return this.onTextChangedFromDefaultValueEvent;
    }

    public OnTextChangeRejectedEvent getOnTextChangeRejectedEvent() {
        return this.onTextChangeRejectedEvent;
    }

    public OnTextChangeAcceptedEvent getOnTextChangeAcceptedEvent() {
        return this.onTextChangeAcceptedEvent;
    }

    public OnTextChangedToDefaultValueEvent getOnTextChangedToDefaultValueEvent() {
        return this.onTextChangedToDefaultValueEvent;
    }

    public void addStyleClass(String styleClass) {
        if (!this.getStyleClass().contains(styleClass)) {
            this.getStyleClass().add(styleClass);
        }
    }

    public void addTextFormatterPredicate(Predicate<TextFormatter.Change> predicate) {
        this.textFormatterPredicates.add(predicate);
    }

    private void triggerTabKeyEvent(KeyEvent event) {
        this.fireEvent(new KeyEvent(
                null,
                null,
                KeyEvent.KEY_PRESSED,
                "",
                "\t",
                KeyCode.TAB,
                event.isShiftDown(),
                event.isControlDown(),
                event.isAltDown(),
                event.isMetaDown()));
    }

    public boolean isEmpty() {
        return this.getText() == null || this.getText().isEmpty();
    }

    public void removeStyleClass(String styleClass) {
        if (this.getStyleClass().contains("input-changed-info")) {
            this.getStyleClass().removeAll("input-changed-info");
        }
    }

    public void resetTextToDefaultValue() throws NoDefaultValueSupplierException {
        if (this.defaultValueSupplier == null) {
            throw new NoDefaultValueSupplierException();
        }
        this.setText(this.defaultValueSupplier.get());
    }

    public void selectAllLater() {
        Platform.runLater(this::selectAll);
    }

    public void setIsTransferringFocusOnEnterKey(Boolean value) {
        this.isTransferringFocusOnEnterKeyProperty.set(value);
    }

    public SimpleBooleanProperty getIsTransferringFocusOnEnterKeyProperty() {
        return this.isTransferringFocusOnEnterKeyProperty;
    }

    public Boolean isTransferringFocusOnEnterKey() {
        return this.isTransferringFocusOnEnterKeyProperty.get();
    }

    public String getDefaultValue() {
        return this.defaultValueSupplier.get();
    }
}
