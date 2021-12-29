package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.util.ErrorMessages;
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
            errorMessage += ErrorMessages.INVALID_START_TIME;
        } else if (!DateUtil.validTime(getStartHoursIntervalField())) {
            errorMessage += ErrorMessages.INVALID_START_TIME_FORMAT;
        }
        if (endIntervalDatePicker == null || getEndHoursIntervalField().length() == 0) {
            errorMessage += ErrorMessages.INVALID_END_TIME;
        } else if (!DateUtil.validTime(getEndHoursIntervalField())) {
            errorMessage += ErrorMessages.INVALID_END_TIME_FORMAT;
        }
        if (!getStartHoursIntervalField().equals("") && !getEndHoursIntervalField().equals("")) {
            if (getStartIntervalDatePicker().equals(getEndIntervalDatePicker())
                    && DateUtil.stringToTime(getStartHoursIntervalField()).isAfter(DateUtil.stringToTime(getEndHoursIntervalField()))) {
                errorMessage += ErrorMessages.INVALID_START_END_TIME;
            }
            if (getStartIntervalDatePicker().equals(getEndIntervalDatePicker())
                    && DateUtil.stringToTime(getStartHoursIntervalField()).equals(DateUtil.stringToTime(getEndHoursIntervalField()))) {
                errorMessage += ErrorMessages.INVALID_START_END_TIME_SAME;
            }
            if (getStartIntervalDatePicker().isAfter(getEndIntervalDatePicker())) {
                errorMessage += ErrorMessages.INVALID_START_END_TIME;
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            logger.warn("Invalid data: \n" + errorMessage);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Ошибка");
            alert.setHeaderText(ErrorMessages.INCORRECT_DATA);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
