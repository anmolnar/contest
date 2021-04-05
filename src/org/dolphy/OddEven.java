package org.dolphy;

import java.util.*;
import java.util.concurrent.*;
import java.lang.*;

class OddEven {
    static final CountDownLatch latch = new CountDownLatch(2);



    static class OddThread extends Thread {
        Thread evenThread;

        void setEvenThread(Thread evenThread) {
            this.evenThread = evenThread;
        }

        public synchronized void run() {
            try {
                for (int i = 1; i<=99; i+=2) {
                    System.out.println(i);                    
                    evenThread.notifyAll();
                    wait();
                }
            } catch (InterruptedException e) {}
            latch.countDown();
        }
    }

    static class EvenThread extends Thread {
        Thread oddThread;

        void setOddThread(Thread oddThread) {
            this.oddThread = oddThread;
        }

        public synchronized void run() {
            try {
                for (int i=2; i<=100; i+=2) {
                    System.out.println(i);
                    oddThread.notifyAll();
                    wait();
                }
            } catch (InterruptedException e) {}
            latch.countDown();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        OddThread oddThread = new OddThread();
        EvenThread evenThread = new EvenThread();
        oddThread.setEvenThread(evenThread);
        evenThread.setOddThread(oddThread);

        oddThread.start();
        evenThread.start();

        latch.await();
    }
}

