package ua.edu.sumdu.j2se.rudenko.tasks.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class TaskEditDialogView {
    @FXML
    private Label nameTaskLabel;
    @FXML
    private Label timeTaskLabel;
    @FXML
    private Label endTaskLabel;
    @FXML
    private Label intervalTaskLabel;
    @FXML
    private Label activityTaskLabel;
    @FXML
    private TextField nameTaskField;
    @FXML
    private DatePicker timeDatePicker;
    @FXML
    private TextField timeHoursField;
    @FXML
    protected TextField intervalTaskField;
    protected DatePicker endTaskDatePicker;
    protected TextField endTimeField;

    public void displayTitleDialogLabel(String title) {
        nameTaskLabel.setText(title);
    }

    public void displayIntervalDialogLabel(String interval) {
        intervalTaskLabel.setText(interval);
    }

    public void displayActivityDialogLabel(String activity) {
        activityTaskLabel.setText(activity);
    }

    public void displayTimeStartDialogLabel(String time) {
        timeTaskLabel.setText(time);
    }

    public void displayEndDialogLabel(String end) {
        endTaskLabel.setText(end);
    }

    public void displayTitleDialogField(String title) {
        nameTaskField.setText(title);
    }

    public void displayIntervalDialogField(String interval) {
        intervalTaskField.setText(interval);
    }

    public void displayStartTimeDialogDatePicker(LocalDate start) {
        timeDatePicker.setValue(start);
    }

    public void displayStartTimeDialogField(String time) {
        timeHoursField.setText(time);
    }

    public void displayEndDialogDatePicker(LocalDate end) {
        endTaskDatePicker.setValue(end);
    }

    public void displayEndDialogField(String end) {
        endTimeField.setText(end);
    }

    public String getTitleTaskField() {
        return nameTaskField.getText();
    }

    public LocalDate getStartTimeDatePicker() {
        return timeDatePicker.getValue();
    }

    public String getStartTimeHoursField() {
        return timeHoursField.getText();
    }

    public String getIntervalTaskField() {
        return intervalTaskField.getText();
    }

    public LocalDate getEndTimeDatePicker() {
        return endTaskDatePicker.getValue();
    }

    public String getEndTimeHoursField() {
        return endTimeField.getText();
    }
}
