package ex1.c;

// PKmon.java

import java.util.LinkedList;
import java.util.Random;

class Producer extends Thread {
    private Buffer _buff;
    private int _turns_no;
    private Random gen = new Random();

    public Producer(Buffer buff, int turns_no) {
        _buff = buff;
        _turns_no = turns_no;
    }

    public void run() {
        for (int i = 0; i < _turns_no; ++i) {
            _buff.put(i);
            try {
                sleep(gen.nextInt(50));
            } catch (Exception ex) {}
        }
    }
}

class Consumer extends Thread {
    private Buffer _buff;
    private int _turns_no;
    private Random gen = new Random();

    public Consumer(Buffer buff, int turns_no) {
        _turns_no = turns_no;
        _buff = buff;
    }

    public void run() {
        for (int i = 0; i < _turns_no; ++i) {
            System.out.println("Read value:" + _buff.get());
            try {
                sleep(gen.nextInt(50));
            } catch (Exception ex) {}
        }
    }
}

class Buffer {
    private LinkedList<Integer> buff = new LinkedList<>();
    private int maxSize;

    public Buffer(int size) {
        maxSize = size;
    }

    public synchronized void put(int i) {
        while (buff.size() >= maxSize) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        buff.offer(i);
        notify();
    }

    public synchronized int get() {
        while (buff.size() == 0) {
            try {
                wait();
            } catch (Exception ex) { }
        }

        int val = buff.poll();
        notify();

        return val;
    }
}

public class PKmon {
    private static int turns_no = 100;
    private static int producers_no = 10;
    private static int consumers_no = 10;

    public static void main(String[] args) {
        long start = System.nanoTime();
        Buffer buff = new Buffer(10);
        Producer[] producers = new Producer[producers_no];
        Consumer[] consumers = new Consumer[consumers_no];

        for (int i=0;i<producers_no;i++) {
            producers[i] = new Producer(buff, turns_no / producers_no);
            producers[i].start();
        }

        for (int i=0;i<consumers_no;i++) {
            consumers[i] = new Consumer(buff, turns_no / consumers_no);
            consumers[i].start();
        }

        try {
            for (int i=0;i<producers_no;i++) {
                producers[i].join();
            }
            for (int i=0;i<consumers_no;i++) {
                consumers[i].join();
            }
        } catch (Exception ex) { }

        long end = System.nanoTime();

        float elapsed = (end - start)/1000000;
        System.out.println("Elapsed time in ms:" + elapsed);
    }
}
