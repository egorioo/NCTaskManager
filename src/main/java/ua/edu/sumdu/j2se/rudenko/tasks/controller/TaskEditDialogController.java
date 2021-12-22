package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import ua.edu.sumdu.j2se.rudenko.tasks.view.TaskEditDialogView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskEditDialogController extends TaskEditDialogView {
    private static final Logger logger = Logger.getLogger(TaskEditDialogController.class);

    private Stage stage;

    public boolean okClicked = false;

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            RadioButton selectionRepeated = (RadioButton) groupRepeated.getSelectedToggle();
            RadioButton selectionActive = (RadioButton) groupActive.getSelectedToggle();
            try {
                if (selectionRepeated.getText().equals("Да")) {
                    task.setTitle(getTitleTaskField());
                    task.setTime(getStartTimeDatePicker().atTime(LocalTime.parse(getStartTimeHoursField(), DateTimeFormatter.ofPattern("HH:mm:ss"))),
                            getEndTimeDatePicker().atTime(LocalTime.parse(getEndTimeHoursField(), DateTimeFormatter.ofPattern("HH:mm:ss"))),
                            DateUtil.timeToSeconds(getIntervalTaskField()));

                    task.setActive(selectionActive.getText().equals("Активна"));
                } else {
                    task.setTitle(getTitleTaskField());
                    task.setTime(getStartTimeDatePicker().atTime(LocalTime.parse(getStartTimeHoursField(), DateTimeFormatter.ofPattern("HH:mm:ss"))));
                    task.setActive(selectionActive.getText().equals("Активна"));
                }
            } catch (IllegalArgumentException | NullPointerException e) {
                logger.error(e);
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
            } else if (!DateUtil.validTimeInterval(getIntervalTaskField())) {
                errorMessage += "Неверный интервал! Используйте формат HH:mm\n";
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
            } else if (getIntervalTaskField().equals("00:00")) {
                errorMessage += "Неверный интервал! Интервал не может быть равен нулю\n";
            }
        } else {
            if (timeDatePicker == null || getStartTimeHoursField().length() == 0) {
                errorMessage += "Неверное время!\n";
            } else if (!DateUtil.validTime(getStartTimeHoursField())) {
                errorMessage += "Неверное время! Используйте формат HH:mm:ss\n";
            }
            if (getIntervalTaskField() == null || getIntervalTaskField().length() == 0) {
                errorMessage += "Неверный интервал \n";
            } else if (!DateUtil.validTimeInterval(getIntervalTaskField())) {
                errorMessage += "Неверный интервал! Используйте формат HH:mm\n";
            } else if (!getIntervalTaskField().equals("00:00")) {
                errorMessage += "Неверный интервал! Интервал должен быть равен нулю\n";
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
