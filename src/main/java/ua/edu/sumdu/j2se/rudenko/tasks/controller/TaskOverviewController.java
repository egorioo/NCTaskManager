package ua.edu.sumdu.j2se.rudenko.tasks.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.rudenko.tasks.Main;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Tasks;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class TaskOverviewController {
    private Main main;

    @FXML
    private TableView<Task> tableView;
    @FXML
    private TableColumn<Task, String> nameColumn;
    @FXML
    private TableColumn<Task, String> dateColumn;
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

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateUtil.dateToString(cellData.getValue().getTime())));

        showTaskDetails(null);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task person, Task t1) {
                showTaskDetails(t1);
                System.out.println("listening");
            }
        });
    }

    private void showTaskDetails(Task task) {
        if (task == null) {
            nameValue.setText("");
            timeValue.setText("");
            endValue.setText("");
            intervalValue.setText("");
            activeValue.setText("");
        } else if (task.isRepeated()) {
            showTaskDetails(null);
            nameValue.setText(task.getTitle());
            timeLabel.setText("Время начала");
            endLabel.setText("Время конца");
            timeValue.setText(DateUtil.dateToString(task.getStartTime()));
            endValue.setText(DateUtil.dateToString(task.getEndTime()));
            intervalValue.setText(String.valueOf(task.getRepeatInterval()));
            activeValue.setText(task.isActive() ? "Активна" : "Неактивна");
        } else {
            showTaskDetails(null);
            nameValue.setText(task.getTitle());
            timeLabel.setText("Время выполнения");
            endLabel.setText("");
            timeValue.setText(DateUtil.dateToString(task.getTime()));
            intervalValue.setText(task.getRepeatInterval() == 0 ? "Задача не повторяется" : String.valueOf(task.getRepeatInterval()));
            activeValue.setText(task.isActive() ? "Активна" : "Неактивна");
        }
    }

    public void setMainApp(Main main) {
        this.main = main;
        tableView.setItems(main.getData());
    }

    public boolean showTaskEditDialog(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TaskEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TaskEditDialogController controller = loader.getController();
            controller.showCurrentTask(task);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.okClicked;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleDeleteTask() {
        int index = tableView.getSelectionModel().getSelectedIndex();
        var a = tableView.getSelectionModel().getSelectedItem();
        if (index == -1) {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        } else {
            main.getData().remove(a);
            tableView.getItems().remove(a);
        }
    }

    @FXML
    private void handleEditTask() {
        Task selectedTask = tableView.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No task selected");
            alert.setContentText("Please select task in the table");
            alert.showAndWait();
        } else {
            boolean okClicked = showTaskEditDialog(selectedTask);
            if (okClicked) {
                showTaskDetails(selectedTask);
                tableView.refresh();
            }
        }
    }

    @FXML
    private void handleAddTask() {
        Task tempPerson = new Task("", LocalDateTime.now());
        boolean okClicked = showTaskEditDialog(tempPerson);
        if (okClicked) {
            main.getData().add(tempPerson);
            showTaskDetails(tempPerson);
        }
    }

    @FXML
    private void filterThisWeek() throws ClassNotFoundException {
        ObservableList<Task> tasksWeek = FXCollections.observableArrayList();

        for (Task task : Tasks.incoming(main.getData(), LocalDateTime.now(), LocalDateTime.now().with(DayOfWeek.SUNDAY))) {
            tasksWeek.add(task);
        }
        tableView.setItems(tasksWeek);

    }

    @FXML
    private void filterThisMonth() throws ClassNotFoundException {
        ObservableList<Task> tasksWeek = FXCollections.observableArrayList();
        for (Task task : Tasks.incoming(main.getData(), LocalDateTime.now(), LocalDateTime.now().with(lastDayOfMonth()))) {
            tasksWeek.add(task);
        }
        tableView.setItems(tasksWeek);
    }

    @FXML
    private void allTasks() {
        tableView.setItems(main.getData());
    }

    @FXML
    private void filterTimeInterval() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/DateIntervalDialogWindow.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dateSelectStage = new Stage();
        dateSelectStage.setTitle("Edit Task");
        dateSelectStage.initModality(Modality.WINDOW_MODAL);
        dateSelectStage.initOwner(main.getPrimaryStage());
        Scene scene = new Scene(page);
        dateSelectStage.setScene(scene);

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
    }

    @FXML
    private void filterDay() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/DateDialogWindow.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dateSelectStage = new Stage();
        dateSelectStage.setTitle("Edit Task");
        dateSelectStage.initModality(Modality.WINDOW_MODAL);
        dateSelectStage.initOwner(main.getPrimaryStage());
        Scene scene = new Scene(page);
        dateSelectStage.setScene(scene);

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
    }
}
