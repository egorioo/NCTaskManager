package ua.edu.sumdu.j2se.rudenko.tasks;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.controller.TaskOverviewController;
import ua.edu.sumdu.j2se.rudenko.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.rudenko.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import ua.edu.sumdu.j2se.rudenko.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.rudenko.tasks.services.StageManager;
import ua.edu.sumdu.j2se.rudenko.tasks.util.Notification;
import ua.edu.sumdu.j2se.rudenko.tasks.view.TaskOverviewView;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
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

            StageManager.getInstance().setPrimaryStage(stage);
            Platform.setImplicitExit(false);

            Notification notification = new Notification();
            notification.setMain(this);
            notification.createIcon();
            notification.enableNotifications();

            FXMLLoader loader = TaskOverviewView.createMainWindow(stage);

            TaskOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            logger.error(e);
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
            logger.error(e);
        }
    }

    public ObservableList<Task> getData() {
        return tasksData;
    }
}