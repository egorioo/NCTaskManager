package ua.edu.sumdu.j2se.rudenko.tasks.view;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DateIntervalDialogWindowView {
    @FXML
    protected DatePicker startIntervalDatePicker;
    @FXML
    protected DatePicker endIntervalDatePicker;
    @FXML
    private TextField startIntervalField;
    @FXML
    private TextField endIntervalField;

    public void displayStartIntervalDatePicker (LocalDate start) {
        startIntervalDatePicker.setValue(start);
    }

    public void displayEndIntervalDatePicker (LocalDate end) {
        endIntervalDatePicker.setValue(end);
    }

    public LocalDate getStartIntervalDatePicker() {
        return startIntervalDatePicker.getValue();
    }

    public LocalDate getEndIntervalDatePicker() {
        return endIntervalDatePicker.getValue();
    }

    public String getStartHoursIntervalField() {
        return startIntervalField.getText();
    }

    public String getEndHoursIntervalField() {
        return endIntervalField.getText();
    }
}
