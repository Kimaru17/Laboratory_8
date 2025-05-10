package controller;

import domain.Elementary;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;

public class SelectionSortController
{
    @javafx.fxml.FXML
    private TextField highBoundField;
    @javafx.fxml.FXML
    private TextField arrayLengthField;
    @javafx.fxml.FXML
    private TableView noSortedTableView;
    @javafx.fxml.FXML
    private TableView sortedTableView;
    @javafx.fxml.FXML
    private TextField lowBoundField;

    private int original[];
    @FXML
    private TextArea txtA_totalIterations;
    @FXML
    private TextArea txtA_MinIndexChanges;
    @FXML
    private TextArea txtA_MinChanges;
    @FXML
    private TextArea txtA_totalChanges;

    @FXML
    private void createButtonOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        int n = Integer.parseInt(arrayLengthField.getText());
        int low = Integer.parseInt(lowBoundField.getText());
        int high = Integer.parseInt(highBoundField.getText());
        original = new int[n];
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            original[i] = rnd.nextInt(high - low + 1) + low;
        }
        fillTable(noSortedTableView, original);

        //limpiamos el resto
        txtA_MinIndexChanges.clear();
        txtA_MinChanges.clear();
        sortedTableView.getItems().clear();
        sortedTableView.getColumns().clear();
        txtA_totalChanges.clear();
        txtA_totalIterations.clear();

    }

    private void fillTable(TableView<ObservableList<Integer>> tableView, int[] array) {
        tableView.getColumns().clear();
        ObservableList<ObservableList<Integer>> data = FXCollections.observableArrayList();
        ObservableList<Integer> row = FXCollections.observableArrayList();
        for (int val : array) row.add(val);
        data.add(row);

        for (int i = 0; i < array.length; i++) {
            TableColumn<ObservableList<Integer>, Integer> col = new TableColumn<>(String.valueOf(i));
            final int colIndex = i;
            col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().get(colIndex)));
            tableView.getColumns().add(col);
        }

        tableView.setItems(data);
    }


    public void initialize() {
        arrayLengthField.setTextFormatter(createNumberFormatter());
        lowBoundField.setTextFormatter(createNumberFormatter());
        highBoundField.setTextFormatter(createNumberFormatter());
    }

    private TextFormatter<String> createNumberFormatter() {
        return new TextFormatter<>(change -> {
            return change.getControlNewText().matches("\\d*") ? change : null;
        });
    }

    private boolean validateBounds() {
        if (lowBoundField.getText().isEmpty() || highBoundField.getText().isEmpty()) return false;
        int low = Integer.parseInt(lowBoundField.getText());
        int high = Integer.parseInt(highBoundField.getText());
        return low < high;
    }

    private boolean validateArrayLength() {
        String input = arrayLengthField.getText();
        if (input.isEmpty()) return false;
        int value = Integer.parseInt(input);
        return value > 0 && value <= 200;
    }

    private boolean validateInputs() {
        if (!validateArrayLength()) {
            showAlert("Array length must be between 1 and 200.");
            return false;
        }
        if (!validateBounds()) {
            showAlert("Low bound must be less than high bound.");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void startOnAction(ActionEvent actionEvent) {

        if (original == null) {
            showAlert("Please create the array first.");
            return;
        }
        Elementary.selectionSort(original);
        fillTable(sortedTableView, original);
        txtA_MinChanges.setText(Elementary.getMinChanges());
        txtA_MinIndexChanges.setText(Elementary.getMinIndexChanges());
        txtA_totalIterations.setText(String.valueOf(Elementary.getTotalIteractions()));
        txtA_totalChanges.setText(String.valueOf(Elementary.getTotalChanges()));

    }

    @FXML
    public void clearOnAction(ActionEvent actionEvent) {
        arrayLengthField.clear();
        lowBoundField.clear();
        highBoundField.clear();
        noSortedTableView.getItems().clear();
        sortedTableView.getItems().clear();
        noSortedTableView.getColumns().clear();
        sortedTableView.getColumns().clear();
        txtA_MinIndexChanges.clear();
        txtA_MinChanges.clear();
        txtA_totalChanges.clear();
        txtA_totalIterations.clear();
        original = null;
    }

    @FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        arrayLengthField.setText(String.valueOf(util.Utility.getRandom(200)));
        lowBoundField.setText(String.valueOf(util.Utility.getRandom(100)));
        highBoundField.setText(String.valueOf(util.Utility.getRandom(1000)));
        createButtonOnAction(actionEvent);
        startOnAction(actionEvent);
    }
}