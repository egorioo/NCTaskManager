package ua.edu.sumdu.j2se.rudenko.tasks.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TaskOverviewView {
    @FXML
    private Label nameValue;
    @FXML
    private Label timeValue;
    @FXML
    private Label endValue;
    @FXML
    private Label timeLabel;
    @FXML
    private Label endLabel;
    @FXML
    private Label intervalValue;
    @FXML
    private Label activeValue;

    public void displayTitleValue(String title) {
        nameValue.setText(title);
    }

    public void displayStartTimeValue(String time) {
        timeValue.setText(time);
    }

    public void displayEndTimeValue(String end) {
        endValue.setText(end);
    }

    public void displayIntervalValue(String interval) {
        intervalValue.setText(interval);
    }

    public void displayActivityValue(String activity) {
        activeValue.setText(activity);
    }

    public void displayTimeStartLabel(String time) {
        timeLabel.setText(time);
    }

    public void displayEndLabel(String end) {
        endLabel.setText(end);
    }
}
