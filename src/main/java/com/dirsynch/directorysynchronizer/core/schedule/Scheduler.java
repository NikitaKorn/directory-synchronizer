package com.dirsynch.directorysynchronizer.core.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private ScheduledFuture<?> scheduledFuture;
    private final ScheduledExecutorService scheduler;
    private final Runnable task;

    public Scheduler(Runnable task){
        this.task = task;
        this.scheduler = Executors.newScheduledThreadPool(1);;
    }

    public void run(long period){
        scheduledFuture = scheduler.scheduleAtFixedRate(task, period, period, TimeUnit.SECONDS);
    }

    public void cancel(){
        scheduledFuture.cancel(true);
    }

    public long getDelay(){
        return scheduledFuture.getDelay(TimeUnit.SECONDS);
    }

    public ScheduledFuture<?> getScheduledFuture() {
        return scheduledFuture;
    }
}
