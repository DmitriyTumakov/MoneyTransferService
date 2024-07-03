package ru.netology.moneytransferservice.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileLogger implements Logger {
    private final File logFile = new File("log.txt");

    public void log(String message, boolean append) {
        if (logFile.exists()) {
            try (FileOutputStream fos = new FileOutputStream(logFile.getName(), append)) {
                fos.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (logFile.createNewFile()) {
                    log(message, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}