package controller;

import domain.Complex;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;

public class QuickSortController
{
    @javafx.fxml.FXML
    private TextArea txtA_low;
    @javafx.fxml.FXML
    private TextField highBoundField;
    @javafx.fxml.FXML
    private TextArea txtA_pivot;
    @javafx.fxml.FXML
    private TextArea txtA_high;
    @javafx.fxml.FXML
    private TextField arrayLengthField;
    @javafx.fxml.FXML
    private TableView noSortedTableView;
    @javafx.fxml.FXML
    private TextArea txtA_recursive;
    @javafx.fxml.FXML
    private TableView sortedTableView;
    @javafx.fxml.FXML
    private TextField lowBoundField;

    private int originalArray[];
    private Complex complex;

    @javafx.fxml.FXML
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

    @FXML
    public void startOnAction(ActionEvent actionEvent) {

        if (originalArray == null) {
            showAlert("Create the array first.");
            return;
        }

        int n = originalArray.length;
        int[] tmp = new int[n];
        complex.quickSort(originalArray, 0, n - 1);
        txtA_high.setText(complex.getLowsString());
        txtA_low.setText(complex.getHighsString());
        txtA_pivot.setText(String.valueOf(complex.getPivot()));
        txtA_recursive.setText(String.valueOf(complex.getRecursionCount()));
        fillTable(sortedTableView, originalArray);
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
        txtA_high.clear();
        txtA_low.clear();
        txtA_pivot.clear();
        txtA_recursive.clear();
        originalArray = null;
    }

    @FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        arrayLengthField.setText(String.valueOf(util.Utility.getRandom(200)));
        lowBoundField.setText(String.valueOf(util.Utility.getRandom(100)));
        highBoundField.setText(String.valueOf(util.Utility.getRandom(1000)));
        createButtonOnAction(actionEvent);
        startOnAction(actionEvent);
    }

    @FXML
    public void createButtonOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;
        int n = Integer.parseInt(arrayLengthField.getText());
        int low = Integer.parseInt(lowBoundField.getText());
        int high = Integer.parseInt(highBoundField.getText());
        originalArray = new int[n];
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            originalArray[i] = rnd.nextInt(high - low + 1) + low;
        }
        fillTable(noSortedTableView, originalArray);
        sortedTableView.getItems().clear(); sortedTableView.getColumns().clear();
        txtA_low.clear(); txtA_high.clear(); txtA_recursive.clear();
        complex = new Complex();
    }

    private void fillTable(TableView<ObservableList<Integer>> tableView, int[] array) {
        tableView.getColumns().clear();
        ObservableList<ObservableList<Integer>> data = FXCollections.observableArrayList();
        ObservableList<Integer> row = FXCollections.observableArrayList();
        for (int val : array) row.add(val);
        data.add(row);
        for (int i = 0; i < array.length; i++) {
            TableColumn<ObservableList<Integer>, Integer> col = new TableColumn<>(String.valueOf(i));
            final int idx = i;
            col.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(cd.getValue().get(idx)));
            tableView.getColumns().add(col);
        }
        tableView.setItems(data);
    }

    private boolean validateInputs() {
        if (arrayLengthField.getText().isEmpty() || lowBoundField.getText().isEmpty() || highBoundField.getText().isEmpty()) {
            showAlert("All fields are required."); return false; }
        int len = Integer.parseInt(arrayLengthField.getText());
        if (len <= 0 || len > 200) { showAlert("Length must be 1-200"); return false; }
        int low = Integer.parseInt(lowBoundField.getText());
        int high = Integer.parseInt(highBoundField.getText());
        if (low >= high) { showAlert("Low must be less than high"); return false; }
        return true;
    }
    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(msg);
        a.showAndWait();
    }
}