package ua.edu.sumdu.j2se.rudenko.tasks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.model.*;
import ua.edu.sumdu.j2se.rudenko.tasks.util.DateUtil;
import ua.edu.sumdu.j2se.rudenko.tasks.util.Notification;

import java.io.File;
import java.io.IOException;


public class Main extends Application {
    private Stage primaryStage;
    private AbstractTaskList list = new ArrayTaskList();
    private ObservableList<Task> tasksData = FXCollections.observableArrayList();
    private static final Logger logger = Logger.getLogger(Main.class);

    public Main() {
        TaskIO.readBinary(list, new File("data.txt"));
        for (Task task : list) {
            tasksData.add(task);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            logger.debug("starting the application");

            this.primaryStage = stage;
            // instructs the javafx system not to exit implicitly when the last application window is shut.
            Platform.setImplicitExit(false);

            Notification notification = new Notification();
            notification.setMain(this);
            notification.createIcon();
            notification.enableNotifications();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/TaskOverview.fxml"));
            Parent root = loader.load();
            TaskOverviewController controller = loader.getController();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.setTitle("Task Manager");
            stage.setWidth(600);
            stage.setHeight(400);
            stage.setMinHeight(360);
            stage.setMinWidth(550);

            stage.show();
            controller.setMainApp(this);
        } catch (IOException e) {
            logger.fatal(e);
        }
    }

    @Override
    public void stop() {
        try {
            AbstractTaskList tempList = new ArrayTaskList();
            for (Task task : tasksData) {
                tempList.add(task);
            }
            TaskIO.writeBinary(tempList, new File("data.txt"));
            super.stop();
            logger.debug("termination of the application");
        } catch (Exception e) {
            logger.fatal(e);
        }
    }

    public ObservableList<Task> getData() {
        return tasksData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}