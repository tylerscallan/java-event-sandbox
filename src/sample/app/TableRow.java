package sample.app;

import javafx.beans.property.SimpleBooleanProperty;
import sample.controls.BaseInputTextField;
import sample.controls.CurrencyInputTextField;

public class TableRow {

    private String description;

    private SimpleBooleanProperty isDirty = new SimpleBooleanProperty(false);

    private String item;

    private String line;

    private CurrencyInputTextField professionalPrice;

    private CurrencyInputTextField retailPrice;

    public TableRow(String line, String item, String description, String retailPrice, String professionalPrice) {
        this.line = line;
        this.item = item;
        this.description = description;
        this.retailPrice = new CurrencyInputTextField(retailPrice);
        this.professionalPrice = new CurrencyInputTextField(professionalPrice);
    }

    public String getDescription() {
        return this.description;
    }

    public String getItem() {
        return this.item;
    }

    public String getLine() {
        return this.line;
    }

    public BaseInputTextField getProfessionalPrice() {
        return this.professionalPrice;
    }

    public BaseInputTextField getRetailPrice() {
        return this.retailPrice;
    }
}
