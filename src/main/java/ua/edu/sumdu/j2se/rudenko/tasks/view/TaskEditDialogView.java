package ua.edu.sumdu.j2se.rudenko.tasks.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.services.StageManager;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskEditDialogView {
    private static final Logger logger = Logger.getLogger(TaskEditDialogView.class);

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
    protected DatePicker timeDatePicker;
    @FXML
    private TextField timeHoursField;
    @FXML
    private TextField intervalTaskField;
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
    private HBox hbox;
    protected ToggleGroup groupActive;
    protected ToggleGroup groupRepeated;
    protected DatePicker endTaskDatePicker;
    private TextField endTimeField;
    protected Task task;

    public static FXMLLoader createEditDialogWindow(Stage dialogStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(TaskEditDialogView.class.getResource("/fxml/TaskEditDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        dialogStage.setTitle("Изменить");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(StageManager.getInstance().getPrimaryStage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.getIcons().add(new Image(TaskOverviewController.class.getResourceAsStream("/images/icon24px.png")));
        return loader;
    }

    public void showCurrentTask(Task task) {
        this.task = task;
        displayTitleDialogLabel("Название");
        displayIntervalDialogLabel("Интервал");
        displayActivityDialogLabel("Активность");

        displayTitleDialogField(task.getTitle());

        displayIntervalDialogField(DateUtil.secondsToTime(task.getRepeatInterval()));
        intervalTaskField.setPromptText("HH:mm");

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

            hbox = new HBox(10, endTaskDatePicker, endTimeField);

            gridPaneDialog.add(hbox, 1, 3);
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

                    hbox = new HBox(10, endTaskDatePicker, endTimeField);

                    gridPaneDialog.add(hbox, 1, 3);
                } else {
                    logger.debug("changing a task to a non-repeated one");
                    displayTimeStartDialogLabel("Время выполнения");
                    displayEndDialogLabel("");

                    nonRepeatedRadioBtn.setSelected(true);
                    Label temp = new Label();
                    temp.setText("");
                    displayIntervalDialogField("00:00");
                    gridPaneDialog.getChildren().remove(hbox);
                    displayStartTimeDialogDatePicker(task.getTime().toLocalDate());
                    displayStartTimeDialogField(task.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }
            }
        });
    }

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
