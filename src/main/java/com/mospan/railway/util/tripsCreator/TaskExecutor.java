package com.mospan.railway.util.tripsCreator;

import java.util.Timer;

public class TaskExecutor {
    public static void main (String[] args) {
        Timer timer = new Timer();
        Task task = new Task();
        timer.schedule(task, 1000);
    }
}
