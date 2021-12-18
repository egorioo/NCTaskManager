package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.Main;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import ua.edu.sumdu.j2se.rudenko.tasks.view.TaskOverviewView;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class TaskOverviewController extends TaskOverviewView {
    private Main main;
    private static final Logger logger = Logger.getLogger(TaskOverviewController.class);

    @FXML
    private AnchorPane leftAnchorPane;
    @FXML
    private AnchorPane rightAnchorPane;
    @FXML
    private TableView<Task> tableView;
    @FXML
    private TableColumn<Task, String> nameColumn;
    @FXML
    private TableColumn<Task, String> dateColumn;
    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.dateToString(cellData.getValue().getTime())));

        showTaskDetails(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task person, Task t1) {
                if (t1 != null)
                    logger.debug("task " + t1.getTitle() + " was selected");
                showTaskDetails(t1);
            }
        });

        ObservableList<String> filters = FXCollections.observableArrayList("На этой неделе", "В этом месяце", "Указать дату", "Указать промежуток", "Все задачи");
        filterComboBox.setValue("Все задачи");
        filterComboBox.setItems(filters);

        leftAnchorPane.setMinWidth(150);
        rightAnchorPane.setMinWidth(300);
    }

    private void showTaskDetails(Task task) {
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
            displayIntervalValue(String.valueOf(task.getRepeatInterval()));
            displayActivityValue(task.isActive() ? "Активна" : "Неактивна");
        } else {
            showTaskDetails(null);
            displayTitleValue(task.getTitle());
            displayTimeStartLabel("Время выполнения");
            displayEndTimeValue("");
            displayStartTimeValue(DateUtil.dateToString(task.getTime()));
            displayIntervalValue(task.getRepeatInterval() == 0 ? "Задача не повторяется" : String.valueOf(task.getRepeatInterval()));
            displayActivityValue(task.isActive() ? "Активна" : "Неактивна");
        }
    }

    public void setMainApp(Main main) {
        this.main = main;
        tableView.setItems(main.getData());
    }

    public boolean showTaskEditDialog(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/TaskEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Изменить");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.getIcons().add(new Image(TaskOverviewController.class.getResourceAsStream("/images/icon24px.png")));

            TaskEditDialogController controller = loader.getController();
            controller.showCurrentTask(task);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.okClicked;
        } catch (IOException e) {
            logger.fatal(e);
            return false;
        }
    }

    @FXML
    private void handleDeleteTask() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        Task task = tableView.getSelectionModel().getSelectedItem();
        if (index == -1) {
            logger.warn("task to delete is not selected");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ошибка");
            alert.setHeaderText("Задача не выбрана");
            alert.setContentText("Выберите задачу из списка");
            alert.showAndWait();
        } else {
            main.getData().remove(task);
            tableView.getItems().remove(task);
            logger.debug("deleting a task" + task.getTitle());
        }
    }

    @FXML
    private void handleEditTask() {
        Task selectedTask = tableView.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            logger.warn("task to edit is not selected");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ошибка");
            alert.setHeaderText("Задача не выбрана");
            alert.setContentText("Выберите задачу из списка");
            alert.showAndWait();
        } else {
            logger.debug("editing a task " + selectedTask.getTitle());
            boolean okClicked = showTaskEditDialog(selectedTask);
            if (okClicked) {
                showTaskDetails(selectedTask);
                tableView.refresh();
                logger.debug(selectedTask.getTitle() + " has been changed");
            }
        }
    }

    @FXML
    private void handleAddTask() {
        Task tempPerson = new Task("", LocalDateTime.now());
        logger.debug("adding a task");
        boolean okClicked = showTaskEditDialog(tempPerson);
        if (okClicked) {
            main.getData().add(tempPerson);
            showTaskDetails(tempPerson);
            logger.debug(tempPerson.getTitle() + " has been added");
        }
    }

    @FXML
    private void event() {
        logger.debug("filtering tasks");
        if (filterComboBox.getValue().equals("На этой неделе")) {
            filterThisWeek();
        } else if (filterComboBox.getValue().equals("В этом месяце")) {
            filterThisMonth();
        } else if (filterComboBox.getValue().equals("Указать дату")) {
            filterDay();
        } else if (filterComboBox.getValue().equals("Указать промежуток")) {
            filterTimeInterval();
        } else if (filterComboBox.getValue().equals("Указать промежуток")) {
            filterTimeInterval();
        } else if (filterComboBox.getValue().equals("Все задачи")) {
            allTasks();
        }
    }

    private void filterThisWeek() {
        try {
            logger.debug("filter by current week selected");
            ObservableList<Task> tasksWeek = FXCollections.observableArrayList();

            for (Task task : Tasks.incoming(main.getData(), LocalDateTime.now(), LocalDateTime.now().with(DayOfWeek.SUNDAY))) {
                tasksWeek.add(task);
            }
            tableView.setItems(tasksWeek);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    private void filterThisMonth() {
        try {
            logger.debug("filter by current months selected");
            ObservableList<Task> tasksWeek = FXCollections.observableArrayList();
            for (Task task : Tasks.incoming(main.getData(), LocalDateTime.now(), LocalDateTime.now().with(lastDayOfMonth()))) {
                tasksWeek.add(task);
            }
            tableView.setItems(tasksWeek);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
    }

    private void allTasks() {
        tableView.setItems(main.getData());
    }

    private void filterTimeInterval() {
        try {
            logger.debug("filter by date interval selected");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DateIntervalDialogWindow.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dateSelectStage = new Stage();
            dateSelectStage.setTitle("");
            dateSelectStage.initModality(Modality.WINDOW_MODAL);
            dateSelectStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            dateSelectStage.setScene(scene);
            dateSelectStage.setResizable(false);
            dateSelectStage.getIcons().add(new Image(TaskOverviewController.class.getResourceAsStream("/images/icon24px.png")));
            DateIntervalDialogWindowController controller = loader.getController();

            controller.setDialogStage(dateSelectStage);

            dateSelectStage.showAndWait();

            if (controller.okClicked) {
                ObservableList<Task> taskDateInterval = FXCollections.observableArrayList();
                for (Task task : Tasks.incoming(main.getData(), controller.getStartDateTime(), controller.getEndDateTime())) {
                    taskDateInterval.add(task);
                }
                tableView.setItems(taskDateInterval);
            }
        } catch (ClassNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void filterDay() {
        try {
            logger.debug("filter by date selected");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/DateDialogWindow.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dateSelectStage = new Stage();
            dateSelectStage.setTitle("");
            dateSelectStage.initModality(Modality.WINDOW_MODAL);
            dateSelectStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            dateSelectStage.setScene(scene);
            dateSelectStage.setResizable(false);
            dateSelectStage.getIcons().add(new Image(TaskOverviewController.class.getResourceAsStream("/images/icon24px.png")));

            DateDialogWindowController controller = loader.getController();

            controller.setDialogStage(dateSelectStage);

            dateSelectStage.showAndWait();

            if (controller.okClicked) {
                LocalDate date = controller.getDate();
                ObservableList<Task> taskDate = FXCollections.observableArrayList();
                for (Task task : Tasks.incoming(main.getData(), date.atTime(0, 0, 1), date.atTime(23, 59, 59))) {
                    taskDate.add(task);
                }
                tableView.setItems(taskDate);
            }
        } catch (ClassNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
