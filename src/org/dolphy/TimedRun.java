package org.dolphy;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class TimedRun {
    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("TimedRun started for 5 seconds");
        timedRun(new Runnable() {
            @Override
            public void run()  {
                Random random = new Random();
                int progress = 0;
                //Initialize progress property.
                while (progress < 100 && !Thread.currentThread().isInterrupted()) {
                    //Sleep for up to one second.
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    //Make random progress.
                    progress += random.nextInt(10);
                    System.out.printf("Completed %d%% of task.\n", progress);
                }
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Task has been interrupted");
                }
            }
        }, 5, TimeUnit.SECONDS);
    }

    private static void timedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null) {
                    throw Renderer.launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }
}
