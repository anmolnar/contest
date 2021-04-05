package org.dolphy;

import java.util.*;

class ReaderWriter {
    final Object R_LCK = new Object();
    final Object W_LCK = new Object();

    int numReaders = 0;
    boolean writer = false;
    
    void aquireReader() throws InterruptedException {
        synchronized (W_LCK) {}
        synchronized (R_LCK) {
            while (writer)
                R_LCK.wait();
            numReaders++;
        }
    }

    void releaseReader() {
        synchronized (R_LCK) {
            numReaders--;
            R_LCK.notify();
        }
    }

    void aquireWriter() throws InterruptedException {        
        synchronized (W_LCK) {
            while (writer)
                W_LCK.wait();            
            synchronized (R_LCK) {
                while (numReaders > 0)
                    R_LCK.wait();
                writer = true;
            }
        }
    }

    synchronized void releaseWriter() {
        synchronized (R_LCK) {
            synchronized (W_LCK) {
                writer = false;
                W_LCK.notify();
                R_LCK.notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReaderWriter lokk = new ReaderWriter();

        for (int i=0; i<10; i++) {
            ReaderThread reader = new ReaderThread(lokk);
            reader.start();
        }

        WriterThread writer = new WriterThread(lokk);
        writer.start();

        for (int i=0; i<10; i++) {
            ReaderThread reader = new ReaderThread(lokk);
            reader.start();
        }

        writer = new WriterThread(lokk);
        writer.start();
        
        writer.join();
    }

    static class ReaderThread extends Thread {
        final ReaderWriter lokk;
        final Random rnd = new Random();

        ReaderThread(ReaderWriter lokk) {
            this.lokk = lokk;
        }

        public void run() {
            try {
                //Thread.sleep(Math.abs(rnd.nextInt()) % 15 * 1000);
                lokk.aquireReader();
                System.out.println(Thread.currentThread().getId() + " reading...");
                Thread.sleep(Math.abs(rnd.nextInt()) % 10 * 1000);
                System.out.println(Thread.currentThread().getId() + " finished reading");
                lokk.releaseReader();
            } catch (InterruptedException e) {}
        }
    }

    static class WriterThread extends Thread {
        final ReaderWriter lokk;
        final Random rnd = new Random();

        WriterThread(ReaderWriter lokk) {
            this.lokk = lokk;
        }

        public void run() {
            try {
                lokk.aquireWriter();
                System.out.println(Thread.currentThread().getId() + " writing...");
                Thread.sleep(Math.abs(rnd.nextInt()) % 10 * 1000);
                System.out.println(Thread.currentThread().getId() + " finished writing");
                lokk.releaseWriter();
            } catch (InterruptedException e) {}
        }
    }

}
