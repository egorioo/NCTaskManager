package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import ua.edu.sumdu.j2se.rudenko.tasks.view.DateIntervalDialogWindowView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateIntervalDialogWindowController extends DateIntervalDialogWindowView {
    private static final Logger logger = Logger.getLogger(DateIntervalDialogWindowController.class);
    private Stage stage;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public boolean okClicked = false;

    @FXML
    private void initialize() {
        displayStartIntervalDatePicker(LocalDate.now());
        displayEndIntervalDatePicker(LocalDate.now());
    }

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @FXML
    private void okDateIntervalButtonPressed() {
        logger.debug("button ok pressed");
        if (validInput()) {
            startDateTime = getStartIntervalDatePicker().atTime(DateUtil.stringToTime(getStartHoursIntervalField()));
            endDateTime = getEndIntervalDatePicker().atTime(DateUtil.stringToTime(getEndHoursIntervalField()));
            okClicked = true;
            stage.close();
            logger.debug("tasks filtered");
        }
    }

    @FXML
    private void cancelDateIntervalButtonPressed() {
        stage.close();
        logger.debug("button cancel pressed");
    }

    private boolean validInput() {
        String errorMessage = "";
        if (startIntervalDatePicker == null || getStartHoursIntervalField().length() == 0) {
            errorMessage += "Неверное время начала!\n";
        } else if (!DateUtil.validTime(getStartHoursIntervalField())) {
            errorMessage += "Неверное время начала! Используйте формат HH:mm:ss\n";
        }
        if (endIntervalDatePicker == null || getEndHoursIntervalField().length() == 0) {
            errorMessage += "Неверное время конца!\n";
        } else if (!DateUtil.validTime(getEndHoursIntervalField())) {
            errorMessage += "Неверное время конца! Используйте формат HH:mm:ss\n";
        }
        if (!getStartHoursIntervalField().equals("") && !getEndHoursIntervalField().equals("")) {
            if (getStartIntervalDatePicker().equals(getEndIntervalDatePicker())
                    && DateUtil.stringToTime(getStartHoursIntervalField()).isAfter(DateUtil.stringToTime(getEndHoursIntervalField()))) {
                errorMessage += "Неверное время начала и конца!\n";
            }
            if (getStartIntervalDatePicker().equals(getEndIntervalDatePicker())
                    && DateUtil.stringToTime(getStartHoursIntervalField()).equals(DateUtil.stringToTime(getEndHoursIntervalField()))) {
                errorMessage += "Неверное время начала и конца! Время не может быть одинаковым\n";
            }
            if (getStartIntervalDatePicker().isAfter(getEndIntervalDatePicker())) {
                errorMessage += "Неверное время начала и конца! \n";
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
