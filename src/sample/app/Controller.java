package sample.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {

    @FXML
    private TableView<TableRow> tableView;

    private ObservableList<TableRow> generateTestData(int count) {
        return FXCollections.observableArrayList(IntStream.range(0, count).mapToObj(i ->
                new TableRow(
                        "Lorem",
                        "Ipsum",
                        "Dolor et set",
                        this.getRandomDollarAmount(),
                        this.getRandomDollarAmount()
                )
        ).collect(Collectors.toList()));
    }

    private String getRandomDollarAmount() {
        return new BigDecimal(Math.random() * 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    @FXML
    public void initialize() throws Exception {

        Collection<TableRow> rows = this.generateTestData(25);

        rows.forEach(row -> {
            row.getRetailPrice().setIsTransferringFocusOnEnterKey(true);
            row.getProfessionalPrice().setIsTransferringFocusOnEnterKey(true);
        });

        this.tableView.getItems().addAll(rows);


    }
}
