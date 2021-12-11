package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;

public class TaskEditDialogController {
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
    private TextField timeTaskField;
    @FXML
    private TextField intervalTaskField;
    @FXML
    private TextField activityTaskField;
    @FXML
    private GridPane gridPaneDialog;
    private Task task;
    private TextField endTaskField;
    private Stage stage;

    public boolean okClicked = false;

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }



    public void showCurrentTask(Task task) {
        this.task = task;
        nameTaskLabel.setText("Название");
        intervalTaskLabel.setText("Интервал");
        activityTaskLabel.setText("Активность");

        nameTaskField.setText(task.getTitle());
        intervalTaskField.setText(Integer.toString(task.getRepeatInterval()));
        activityTaskField.setText(task.isActive() ? "Активна" : "Неактивна");
        if (!task.isRepeated()) {
            timeTaskLabel.setText("Время выполнения");
            endTaskLabel.setText("");

            timeTaskField.setText(DateUtil.dateToString(task.getTime()));
        } else {
            timeTaskLabel.setText("Время начала");
            endTaskLabel.setText("Время конца");

            timeTaskField.setText(DateUtil.dateToString(task.getStartTime()));
            endTaskField = new TextField();
            endTaskField.setText(DateUtil.dateToString(task.getEndTime()));
            gridPaneDialog.add(endTaskField,1,2);
        }
    }

    @FXML
    private void handleOk() {
        task.setTitle(nameTaskField.getText());
        task.setActive(activityTaskField.getText() == "Активна");
        if (task.isRepeated()) {
            task.setTime(DateUtil.stringToDate(timeTaskField.getText()),DateUtil.stringToDate(endTaskField.getText()),Integer.parseInt(intervalTaskField.getText()));
        } else {
            task.setTime(DateUtil.stringToDate(timeTaskField.getText()));
        }
        stage.close();
        okClicked = true;
    }
}
