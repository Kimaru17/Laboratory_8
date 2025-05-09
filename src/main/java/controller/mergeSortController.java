package controller;

import domain.Complex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.Random;

public class mergeSortController{
    @FXML private TextField arrayLengthField;
    @FXML private TextField lowBoundField;
    @FXML private TextField highBoundField;

    @FXML private TableView<ObservableList<Integer>> noSortedTableView;
    @FXML private TableView<ObservableList<Integer>> tempArrayTableView;
    @FXML private TextField lowValueTf;
    @FXML private TextField highValueTf;
    @FXML private TextField recursiveCallsTf;
    @FXML private TableView<ObservableList<Integer>> sortedTableView;

    private Complex complex;
    private int[] originalArray;

    @FXML
    private void createButtonOnAction(ActionEvent event) {
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
        // clear previous merge outputs
        tempArrayTableView.getItems().clear(); tempArrayTableView.getColumns().clear();
        sortedTableView.getItems().clear(); sortedTableView.getColumns().clear();
        lowValueTf.clear(); highValueTf.clear(); recursiveCallsTf.clear();
        // prepare Complex instance
        complex = new Complex();
    }

    @FXML
    private void startOnAction(ActionEvent event) {
        if (originalArray == null) {
            showAlert("Create the array first.");
            return;
        }

        int n = originalArray.length;
        int[] tmp = new int[n];
        // run mergeSort with tracking
        complex.mergeSort(originalArray, tmp, 0, n - 1);
        // display temp array
        fillTable(tempArrayTableView, complex.getTempArray());
        // display sorted
        fillTable(sortedTableView, originalArray);
        // display lows/highs and recursion
        lowValueTf.setText(complex.getLowsString());
        highValueTf.setText(complex.getHighsString());
        recursiveCallsTf.setText(String.valueOf(complex.getRecursionCount()));
    }

    @FXML
    private void clearOnAction(ActionEvent event) {
        originalArray = null;
        noSortedTableView.getItems().clear(); noSortedTableView.getColumns().clear();
        tempArrayTableView.getItems().clear(); tempArrayTableView.getColumns().clear();
        sortedTableView.getItems().clear(); sortedTableView.getColumns().clear();
        arrayLengthField.clear(); lowBoundField.clear(); highBoundField.clear();
        lowValueTf.clear(); highValueTf.clear(); recursiveCallsTf.clear();
    }

    @FXML
    public void randomizeOnAction(ActionEvent actionEvent) {
        arrayLengthField.setText(String.valueOf(util.Utility.getRandom(200)));
        lowBoundField.setText(String.valueOf(util.Utility.getRandom(100)));
        highBoundField.setText(String.valueOf(util.Utility.getRandom(1000)));
        createButtonOnAction(actionEvent);
        startOnAction(actionEvent);
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
