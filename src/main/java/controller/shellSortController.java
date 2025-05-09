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

public class shellSortController{
    @FXML private TextField arrayLengthField;
    @FXML private TextField lowBoundField;
    @FXML private TextField highBoundField;

    @FXML private TableView<ObservableList<Integer>> noSortedTableView;
    @FXML private TextField gapTf;
    @FXML private TextField gapArr1Tf;
    @FXML private TextField gapArr2Tf;
    @FXML private TextField gapArr3Tf;
    @FXML private TableView<ObservableList<Integer>> sortedTableView;

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
        populateTable(noSortedTableView, originalArray);
        // clear fields
        gapTf.clear();
        gapArr1Tf.clear(); gapArr2Tf.clear(); gapArr3Tf.clear();
        sortedTableView.getItems().clear(); sortedTableView.getColumns().clear();
    }

    @FXML
    private void startOnAction(ActionEvent event) {
        if (originalArray == null) { showAlert("Create the array first."); return; }
        int n = originalArray.length;
        // initial gap
        int gap = n / 2;
        gapTf.setText(String.valueOf(gap));
        Complex util = new Complex();
        util.shellSort(originalArray);

        populateTable(sortedTableView, originalArray);
    }

    @FXML
    private void clearOnAction(ActionEvent event) {
        originalArray = null;
        noSortedTableView.getItems().clear(); noSortedTableView.getColumns().clear();
        sortedTableView.getItems().clear(); sortedTableView.getColumns().clear();
        arrayLengthField.clear(); lowBoundField.clear(); highBoundField.clear();
        gapTf.clear(); gapArr1Tf.clear(); gapArr2Tf.clear(); gapArr3Tf.clear();
    }

    @FXML
    private void randomizeOnAction(ActionEvent event) {
        arrayLengthField.setText(String.valueOf(util.Utility.getRandom(200)));
        lowBoundField.setText(String.valueOf(util.Utility.getRandom(100)));
        highBoundField.setText(String.valueOf(util.Utility.getRandom(1000)));
        createButtonOnAction(event);
        startOnAction(event);
    }


    private void populateTable(TableView<ObservableList<Integer>> tableView, int[] array) {
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
