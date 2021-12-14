package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private DatePicker timeDatePicker;
    @FXML
    private TextField timeHoursField;
    @FXML
    private TextField intervalTaskField;
    @FXML
    private RadioButton activeRadioBtn;
    @FXML
    private RadioButton nonActiveRadioBtn;

    private ToggleGroup groupActive;

    private ToggleGroup groupRepeated;

    @FXML
    private GridPane gridPaneDialog;
    @FXML
    private RadioButton repeatedRadioBtn;
    @FXML
    private RadioButton nonRepeatedRadioBtn;

    private Task task;
    private Stage stage;

    private DatePicker endTaskDatePicker;
    private HBox hbox;
    private TextField endTimeField;

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
        groupActive = new ToggleGroup();
        activeRadioBtn.setToggleGroup(groupActive);
        nonActiveRadioBtn.setToggleGroup(groupActive);

        groupRepeated = new ToggleGroup();
        repeatedRadioBtn.setToggleGroup(groupRepeated);
        nonRepeatedRadioBtn.setToggleGroup(groupRepeated);
        if (task.isActive()) {
            activeRadioBtn.setSelected(true);
        } else {
            nonActiveRadioBtn.setSelected(true);
        }
        if (!task.isRepeated()) {
            timeTaskLabel.setText("Время выполнения");
            endTaskLabel.setText("");

            nonRepeatedRadioBtn.setSelected(true);

            timeDatePicker.setValue(task.getTime().toLocalDate());
            timeHoursField.setText(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        } else {
            timeTaskLabel.setText("Время начала");
            endTaskLabel.setText("Время конца");

            repeatedRadioBtn.setSelected(true);

            timeDatePicker.setValue(task.getStartTime().toLocalDate());
            timeHoursField.setText(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            endTaskDatePicker = new DatePicker();
            endTaskDatePicker.setValue(task.getEndTime().toLocalDate());
            endTaskDatePicker.setPrefWidth(191);

            endTimeField = new TextField();
            endTimeField.setText(task.getEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            hbox = new HBox(10,endTaskDatePicker,endTimeField);

            gridPaneDialog.add(hbox,1,3);

        }

        groupRepeated.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton selectedBtn = (RadioButton) t1;
                if (selectedBtn.getText().equals("Да")) {
                    timeTaskLabel.setText("Время начала");
                    endTaskLabel.setText("Время конца");
                    repeatedRadioBtn.setSelected(true);

                    timeDatePicker.setValue(task.getStartTime().toLocalDate());
                    timeHoursField.setText(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                    endTaskDatePicker = new DatePicker();
                    endTaskDatePicker.setValue(task.getEndTime().toLocalDate());
                    endTaskDatePicker.setPrefWidth(191);

                    endTimeField = new TextField();
                    endTimeField.setText(task.getEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                    hbox = new HBox(10,endTaskDatePicker,endTimeField);


                    gridPaneDialog.add(hbox,1,3);
                }
                else {
                    timeTaskLabel.setText("Время выполнения");
                    endTaskLabel.setText("");

                    nonRepeatedRadioBtn.setSelected(true);
                    Label temp = new Label();
                    temp.setText("");
                    intervalTaskField.setText("0");
                    gridPaneDialog.getChildren().remove(hbox);
                    timeDatePicker.setValue(task.getTime().toLocalDate());
                    timeHoursField.setText(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }
            }
        });
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            RadioButton selectionRepeated = (RadioButton) groupRepeated.getSelectedToggle();
            RadioButton selectionActive = (RadioButton) groupActive.getSelectedToggle();
            if (selectionRepeated.getText().equals("Да")) {
                task.setTitle(nameTaskField.getText());
                task.setTime(timeDatePicker.getValue().atTime(LocalTime.parse(timeHoursField.getText(),DateTimeFormatter.ofPattern("HH:mm:ss"))),
                        endTaskDatePicker.getValue().atTime(LocalTime.parse(endTimeField.getText(),DateTimeFormatter.ofPattern("HH:mm:ss"))),
                        Integer.parseInt(intervalTaskField.getText()));
                task.setActive(selectionActive.getText().equals("Активна"));
            }
            else {
                task.setTitle(nameTaskField.getText());
                task.setTime(timeDatePicker.getValue().atTime(LocalTime.parse(timeHoursField.getText(),DateTimeFormatter.ofPattern("HH:mm:ss"))));
                task.setActive(selectionActive.getText().equals("Активна"));
            }

            stage.close();
            okClicked = true;
        }
    }

    @FXML
    private void handleCancel() {
        stage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (nameTaskField.getText() == null || nameTaskField.getText().length() == 0) {
            errorMessage += "No valid title!\n";
        }
        RadioButton temp = (RadioButton) groupRepeated.getSelectedToggle();
        if (temp.getText().equals("Да")) {
            if (timeDatePicker == null || timeHoursField.getText().length() == 0) {
                errorMessage += "No valid start time!\n";
            } else if (!DateUtil.validTime(timeHoursField.getText())) {
                errorMessage += "No valid start time! Use the format HH:mm:ss\n";
            }
            if (endTaskDatePicker == null || endTimeField.getText().length() == 0) {
                errorMessage += "No valid end time!\n";
            } else if (!DateUtil.validTime(endTimeField.getText())) {
                errorMessage += "No valid end time! Use the format HH:mm:ss\n";
            }
            if (intervalTaskField.getText() == null || intervalTaskField.getText().length() == 0) {
                errorMessage += "No valid interval \n";
            } else if (Integer.parseInt(intervalTaskField.getText()) <= 0) {
                errorMessage += "No valid interval! The interval cannot be less than or equal to zero \n";
            }
            if (timeDatePicker.getValue().equals(endTaskDatePicker.getValue())
                    && DateUtil.stringToTime(timeHoursField.getText()).isAfter(DateUtil.stringToTime(endTimeField.getText()))) {
                errorMessage += "No valid start time and end time!\n";
            }
            if (timeDatePicker.getValue().equals(endTaskDatePicker.getValue())
                    && DateUtil.stringToTime(timeHoursField.getText()).equals(DateUtil.stringToTime(endTimeField.getText()))) {
                errorMessage += "No valid start time and end time! Time can't be the same\n";
            }
            if (timeDatePicker.getValue().isAfter(endTaskDatePicker.getValue())) {
                errorMessage += "No valid start time and end time! \n";
            }
        } else {
            if (timeDatePicker == null || timeHoursField.getText().length() == 0) {
                errorMessage += "No valid time!\n";
            } else if (!DateUtil.validTime(timeHoursField.getText())) {
                errorMessage += "No valid time! Use the format HH:mm:ss\n";
            }
            if (intervalTaskField.getText() == null || intervalTaskField.getText().length() == 0) {
                errorMessage += "No valid interval \n";
            } else if (!intervalTaskField.getText().equals("0")) {
                errorMessage += "No valid interval! The interval must be zero \n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
