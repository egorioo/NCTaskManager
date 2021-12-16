package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;

public class DateDialogWindowController {
    @FXML
    private DatePicker dateSelectPickerDialog;
    private Stage stage;
    private LocalDate date;

    public boolean okClicked;

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public LocalDate getDate() {
        return date;
    }

    @FXML
    private void okDateButtonDialog() {
        date = dateSelectPickerDialog.getValue();
        okClicked = true;
        stage.close();
    }

    @FXML
    private void cancelDateButtonDialog() {
        stage.close();
    }

}
