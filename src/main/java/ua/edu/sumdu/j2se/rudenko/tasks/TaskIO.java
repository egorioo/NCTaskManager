package ua.edu.sumdu.j2se.rudenko.tasks;

import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TaskIO {

    /**
     * Writes tasks from the list in stream in the binary format
     * Structure of format:
     * Amount of tasks
     * Length of title
     * Title
     * Active (0/1)
     * Repetition interval
     * If the task is repeated: start time, end time
     * If the task is not repeated: execution time
     *
     * @param tasks - list of tasks to be serialized
     * @param out   - Stream to write to
     */
    public static void write(AbstractTaskList tasks, OutputStream out) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(out)) {
            outputStream.writeInt(tasks.size());
            for (Task task : tasks) {
                outputStream.writeInt(task.getTitle().length());
                outputStream.writeUTF(task.getTitle());
                if (task.isActive())
                    outputStream.writeInt(1);
                else if (!task.isActive())
                    outputStream.writeInt(0);
                outputStream.writeInt(task.getRepeatInterval());
                outputStream.writeLong(task.getStartTime().toEpochSecond(ZoneOffset.UTC));
                if (task.isRepeated())
                    outputStream.writeLong(task.getEndTime().toEpochSecond(ZoneOffset.UTC));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads tasks from given stream and write to given list
     */
    public static void read(AbstractTaskList tasks, InputStream in) {
        Task task;
        LocalDateTime start;
        LocalDateTime end;
        try {
            ObjectInputStream stream = new ObjectInputStream(in);
            int size = stream.readInt();
            for (int i = 0; i < size; i++) {
                int tempLength = stream.readInt();
                String title = stream.readUTF();
                int tempActive = stream.readInt();
                boolean isActive = false;
                if (tempActive == 1) isActive = true;
                int interval = stream.readInt();

                start = LocalDateTime.ofEpochSecond(stream.readLong(), 0, ZoneOffset.UTC);
                task = new Task(title, start);
                if (interval != 0) {
                    end = LocalDateTime.ofEpochSecond(stream.readLong(), 0, ZoneOffset.UTC);
                    task = new Task(title, start, end, interval);
                }
                task.setActive(isActive);
                tasks.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes tasks from the list to a file.
     */
    public static void writeBinary(AbstractTaskList tasks, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            write(tasks, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads tasks from the file to the task list.
     */
    public static void readBinary(AbstractTaskList tasks, File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            read(tasks, fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes tasks from a list to a stream in JSON format.
     */
    public static void write(AbstractTaskList tasks, Writer out) {
        Gson json = new Gson();

        try (BufferedWriter bufferedWriter = new BufferedWriter(out)) {
            bufferedWriter.write(String.valueOf(tasks.size()));
            bufferedWriter.newLine();
            String temp;
            for (Task task : tasks) {
                temp = json.toJson(task);
                bufferedWriter.write(temp);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads tasks from the stream to the list.
     */
    public static void read(AbstractTaskList tasks, Reader in) {
        Gson json = new Gson();
        try (BufferedReader bufferedReader = new BufferedReader(in)) {
            int taskAmount = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < taskAmount; i++) {
                String str = bufferedReader.readLine();
                tasks.add(json.fromJson(str, Task.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes tasks to a file in the format JSON
     */
    public static void writeText(AbstractTaskList tasks, File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            write(tasks, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads tasks from a file
     */
    public static void readText(AbstractTaskList tasks, File file) {
        try (FileReader fileReader = new FileReader(file)) {
            read(tasks, fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
