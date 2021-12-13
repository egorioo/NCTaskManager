package ua.edu.sumdu.j2se.rudenko.tasks;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskEditDialogController;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.model.*;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application{

    private Stage primaryStage;
    private AbstractTaskList list = new ArrayTaskList();
    private ObservableList<Task> tasksData = FXCollections.observableArrayList();

    /**
     * Конструктор
     */
    public Main() {
        TaskIO.readBinary(list,new File("data.txt"));

        list.add(new Task("test",LocalDateTime.now(), LocalDateTime.now().plusDays(2),1000));
        for (Task task : list) {
            tasksData.add(task);
        }

        System.out.println(LocalTime.parse("11:22:39", DateTimeFormatter.ofPattern("HH:mm:ss")).toString());


    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public ObservableList<Task> getData() {
        return tasksData;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/view/TaskOverview.fxml"));
        Parent root = loader.load();
        TaskOverviewController controller = loader.getController();

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Hello JavaFX");
        stage.setWidth(600);
        stage.setHeight(400);

        stage.show();
        System.out.println(controller);
        DateUtil date = new DateUtil();
        System.out.println(date.dateToString(LocalDateTime.now()));
        controller.setMainApp(this);
    }

    @Override
    public void stop() throws Exception {
        AbstractTaskList tempList = new ArrayTaskList();
        for (Task task : tasksData) {
            tempList.add(task);
        }
        TaskIO.writeBinary(tempList,new File("data.txt"));
        super.stop();
    }

    public boolean showTaskEditDialog(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TaskEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}