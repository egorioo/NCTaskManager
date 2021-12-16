package ua.edu.sumdu.j2se.rudenko.tasks.util;

import javafx.application.Platform;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.rudenko.tasks.Main;
import ua.edu.sumdu.j2se.rudenko.tasks.model.Task;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class Notification {
    private static final Logger logger = Logger.getLogger(Notification.class);
    private Main main;
    private final Timer notificationTimer = new Timer();
    private TrayIcon trayIcon;
    public void setMain(Main main) {
        this.main = main;
    }
    public void createIcon() {
        try {
            Platform.setImplicitExit(false);

            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = null;

                image = ImageIO.read(new File("src/main/resources/images/icon.png"));

                trayIcon = new TrayIcon(image, "Task Manager");

                trayIcon.addActionListener(event -> Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        main.getPrimaryStage().show();
                        main.getPrimaryStage().toFront();
                    }
                }));

                MenuItem showItem = new MenuItem("Открыть");
                showItem.addActionListener(event -> Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        main.getPrimaryStage().show();
                        main.getPrimaryStage().toFront();
                    }
                }));

                MenuItem exitItem = new MenuItem("Выход");
                exitItem.addActionListener(event -> {
                    notificationTimer.cancel();
                    Platform.exit();
                    tray.remove(trayIcon);
                });

                PopupMenu popup = new PopupMenu();
                popup.add(showItem);
                popup.add(exitItem);
                trayIcon.setPopupMenu(popup);

                tray.add(trayIcon);
                logger.debug("added tray icon");
            } else
                logger.error("system tray is not support");
        }
          catch (IOException e) {
            logger.error(e);
        } catch (AWTException e) {
            logger.error(e);
        }
    }

    public void enableNotifications() {
        notificationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                List<Task> list = new ArrayList();

                for (Task task : main.getData()) {
                    if (task.nextTimeAfter(LocalDateTime.now()) != null &&
                            task.nextTimeAfter(LocalDateTime.now()).isBefore(LocalDateTime.now().plusMinutes(15))) {
                        list.add(task);
                    }
                }
                if (list.size() != 0) {
                    if (list.size() == 1) {
                        logger.debug("received notification");
                        trayIcon.displayMessage("Напоминание", "Скоро наступит время выполнения задачи: "
                                + list.get(0).getTitle(), java.awt.TrayIcon.MessageType.INFO);
                    }
                    if (list.size() > 1) {
                        String titles = "";
                        for (Task task : list) {
                            titles += task.getTitle() + ", ";
                        }
                        titles = titles.substring(0, titles.length() - 2 );
                        logger.debug("received notification");
                        trayIcon.displayMessage("Напоминание", "Скоро наступит время выполнение задач: "
                                + titles , java.awt.TrayIcon.MessageType.INFO);
                    }
                }
            }
        }, 5_000, 120_000);
    }
}
