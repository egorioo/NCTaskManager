package ua.edu.sumdu.j2se.rudenko.tasks.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;

import java.io.IOException;

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

    public static FXMLLoader createMainWindow(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TaskOverviewView.class.getResource("/fxml/TaskOverview.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Task Manager");
        stage.setMinHeight(370);
        stage.setMinWidth(550);
        stage.getIcons().add(new Image(TaskOverviewView.class.getResourceAsStream("/images/icon24px.png")));
        stage.show();
        return loader;
    }

    protected void showTaskDetails(Task task) {
        if (task == null) {
            displayTitleValue("");
            displayStartTimeValue("");
            displayEndTimeValue("");
            displayIntervalValue("");
            displayActivityValue("");
        } else if (task.isRepeated()) {
            showTaskDetails(null);
            displayTitleValue(task.getTitle());
            displayTimeStartLabel("Время начала");
            displayEndLabel("Время конца");
            displayStartTimeValue(DateUtil.dateToString(task.getStartTime()));
            displayEndTimeValue(DateUtil.dateToString(task.getEndTime()));
            displayIntervalValue(DateUtil.secondsToTime(task.getRepeatInterval()));
            displayActivityValue(task.isActive() ? "Активна" : "Неактивна");
        } else {
            showTaskDetails(null);
            displayTitleValue(task.getTitle());
            displayTimeStartLabel("Время выполнения");
            displayEndLabel("");
            displayEndTimeValue("");
            displayStartTimeValue(DateUtil.dateToString(task.getTime()));
            displayIntervalValue(task.getRepeatInterval() == 0 ? "Задача не повторяется" : String.valueOf(task.getRepeatInterval()));
            displayActivityValue(task.isActive() ? "Активна" : "Неактивна");
        }
    }

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
