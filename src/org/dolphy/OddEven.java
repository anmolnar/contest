package org.dolphy;

import java.util.*;
import java.util.concurrent.*;
import java.lang.*;

class OddEven {
    static final CountDownLatch latch = new CountDownLatch(2);

    static final Object syncLock = new Object();


    static class OddThread extends Thread {
        public void run() {
            try {
                int i = 1;
                while (i <= 99) {
                    System.out.println(i);
                    synchronized (syncLock) {
                        syncLock.notify();
                        if (i < 99)
                            syncLock.wait();
                    }
                    i += 2;
                }
            } catch (InterruptedException e) {}
            latch.countDown();
        }
    }

    static class EvenThread extends Thread {
        public void run() {            
            try {
                int i = 2;
                while (i <= 100) {
                    System.out.println(i);
                    synchronized (syncLock) {
                        syncLock.notify();
                        if (i < 100)
                            syncLock.wait();
                    }
                    i += 2;
                }
            } catch (InterruptedException e) {}
            latch.countDown();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        OddThread oddThread = new OddThread();
        EvenThread evenThread = new EvenThread();

        oddThread.start();
        evenThread.start();

        latch.await();
    }
}

