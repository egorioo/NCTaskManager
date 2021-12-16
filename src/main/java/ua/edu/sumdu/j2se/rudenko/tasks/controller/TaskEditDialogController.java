package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import ua.edu.sumdu.j2se.rudenko.tasks.view.TaskEditDialogView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskEditDialogController extends TaskEditDialogView {
    private static final Logger logger = Logger.getLogger(TaskEditDialogController.class);
    @FXML
    private DatePicker timeDatePicker;
    @FXML
    private RadioButton activeRadioBtn;
    @FXML
    private RadioButton nonActiveRadioBtn;
    @FXML
    private GridPane gridPaneDialog;
    @FXML
    private RadioButton repeatedRadioBtn;
    @FXML
    private RadioButton nonRepeatedRadioBtn;

    private Task task;
    private Stage stage;
    private HBox hbox;
    private ToggleGroup groupActive;
    private ToggleGroup groupRepeated;

    public boolean okClicked = false;

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public void showCurrentTask(Task task) {
        this.task = task;
        displayTitleDialogLabel("Название");
        displayIntervalDialogLabel("Интервал");
        displayActivityDialogLabel("Активность");

        displayTitleDialogField(task.getTitle());
        displayIntervalDialogField(Integer.toString(task.getRepeatInterval()));

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
            displayTimeStartDialogLabel("Время выполнения");
            displayEndDialogLabel("");

            nonRepeatedRadioBtn.setSelected(true);

            displayStartTimeDialogDatePicker(task.getTime().toLocalDate());
            displayStartTimeDialogField(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        } else {
            displayTimeStartDialogLabel("Время начала");
            displayEndDialogLabel("Время конца");

            repeatedRadioBtn.setSelected(true);

            displayStartTimeDialogDatePicker(task.getStartTime().toLocalDate());
            displayStartTimeDialogField(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            endTaskDatePicker = new DatePicker();
            endTaskDatePicker.setPrefWidth(191);
            displayEndDialogDatePicker(task.getEndTime().toLocalDate());

            endTimeField = new TextField();
            displayEndDialogField(task.getEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            hbox = new HBox(10,endTaskDatePicker,endTimeField);

            gridPaneDialog.add(hbox,1,3);
        }

        groupRepeated.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton selectedBtn = (RadioButton) t1;
                if (selectedBtn.getText().equals("Да")) {
                    logger.debug("changing a task to a repeated one");
                    displayTimeStartDialogLabel("Время начала");
                    displayEndDialogLabel("Время конца");
                    repeatedRadioBtn.setSelected(true);

                    displayStartTimeDialogDatePicker(task.getStartTime().toLocalDate());
                    displayStartTimeDialogField(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                    endTaskDatePicker = new DatePicker();
                    endTaskDatePicker.setPrefWidth(191);
                    displayEndDialogDatePicker(task.getEndTime().toLocalDate());

                    endTimeField = new TextField();
                    displayEndDialogField(task.getEndTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                    hbox = new HBox(10,endTaskDatePicker,endTimeField);

                    gridPaneDialog.add(hbox,1,3);
                }
                else {
                    logger.debug("changing a task to a non-repeated one");
                    displayTimeStartDialogLabel("Время выполнения");
                    displayEndDialogLabel("");

                    nonRepeatedRadioBtn.setSelected(true);
                    Label temp = new Label();
                    temp.setText("");
                    displayIntervalDialogField("0");
                    gridPaneDialog.getChildren().remove(hbox);
                    displayStartTimeDialogDatePicker(task.getTime().toLocalDate());
                    displayStartTimeDialogField(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
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
                task.setTitle(getTitleTaskField());
                task.setTime(getStartTimeDatePicker().atTime(LocalTime.parse(getStartTimeHoursField(),DateTimeFormatter.ofPattern("HH:mm:ss"))),
                        getEndTimeDatePicker().atTime(LocalTime.parse(getEndTimeHoursField(),DateTimeFormatter.ofPattern("HH:mm:ss"))),
                        Integer.parseInt(getIntervalTaskField()));
                task.setActive(selectionActive.getText().equals("Активна"));
            }
            else {
                task.setTitle(getTitleTaskField());
                task.setTime(getStartTimeDatePicker().atTime(LocalTime.parse(getStartTimeHoursField(),DateTimeFormatter.ofPattern("HH:mm:ss"))));
                task.setActive(selectionActive.getText().equals("Активна"));
            }

            stage.close();
            okClicked = true;
            logger.debug("button OK pressed");
        }
    }

    @FXML
    private void handleCancel() {
        stage.close();
        logger.debug("button cancel pressed");
        logger.debug("data has not been changed");
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (getTitleTaskField() == null || getTitleTaskField().length() == 0) {
            errorMessage += "Неверное название задачи!\n";
        }
        RadioButton temp = (RadioButton) groupRepeated.getSelectedToggle();
        if (temp.getText().equals("Да")) {
            if (timeDatePicker == null || getStartTimeHoursField().length() == 0) {
                errorMessage += "Неверное время начала!\n";
            } else if (!DateUtil.validTime(getStartTimeHoursField())) {
                errorMessage += "Неверное время начала! Используйте формат HH:mm:ss\n";
            }
            if (endTaskDatePicker == null || getEndTimeHoursField().length() == 0) {
                errorMessage += "Неверное время начала!\n";
            } else if (!DateUtil.validTime(getEndTimeHoursField())) {
                errorMessage += "Неверное время начала! Используйте формат HH:mm:ss\n";
            }
            if (getIntervalTaskField() == null || getIntervalTaskField().length() == 0) {
                errorMessage += "Неверный интервал \n";
            } else if (Integer.parseInt(getIntervalTaskField()) <= 0) {
                errorMessage += "Неверный интервал! Интервал не может быть меньше нуля \n";
            }
            if (getStartTimeDatePicker().equals(getEndTimeDatePicker())
                    && DateUtil.stringToTime(getStartTimeHoursField()).isAfter(DateUtil.stringToTime(getEndTimeHoursField()))) {
                errorMessage += "Неверное время начала и конца!\n";
            }
            if (getStartTimeDatePicker().equals(getEndTimeDatePicker())
                    && DateUtil.stringToTime(getStartTimeHoursField()).equals(DateUtil.stringToTime(getEndTimeHoursField()))) {
                errorMessage += "Неверное время начала и конца! Время не может быть одинаковым\n";
            }
            if (getStartTimeDatePicker().isAfter(getEndTimeDatePicker())) {
                errorMessage += "Неверное время начала и конца! \n";
            }
        } else {
            if (timeDatePicker == null || getStartTimeHoursField().length() == 0) {
                errorMessage += "Неверное время!\n";
            } else if (!DateUtil.validTime(getStartTimeHoursField())) {
                errorMessage += "Неверное время! Используйте формат HH:mm:ss\n";
            }
            if (getIntervalTaskField() == null || getIntervalTaskField().length() == 0) {
                errorMessage += "Неверный интервал! \n";
            } else if (!getIntervalTaskField().equals("0")) {
                errorMessage += "Неверный интервал! Интревал не может быть 0 \n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            logger.warn("Invalid data: \n" + errorMessage);
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неверные данные");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
