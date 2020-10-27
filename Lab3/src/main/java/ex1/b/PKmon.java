package ex1.b;

// PKmon.java

import java.util.LinkedList;

class Producer extends Thread {
    private Buffer _buff;

    public Producer(Buffer buff) {
        _buff = buff;
    }

    public void run() {
        for (int i = 0; i < 10; ++i) {
            _buff.put(i);
        }
    }
}

class Consumer extends Thread {
    private Buffer _buff;

    public Consumer(Buffer buff) {
        _buff = buff;
    }

    public void run() {
        for (int i = 0; i < 10; ++i) {
            System.out.println("Read value:" + _buff.get());
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
    private static int producers_no = 10;
    private static int consumers_no = 10;

    public static void main(String[] args) {
        Buffer buff = new Buffer(10);
        Producer[] producers = new Producer[producers_no];
        Consumer[] consumers = new Consumer[consumers_no];

        for (int i=0;i<producers_no;i++) {
            producers[i] = new Producer(buff);
            producers[i].start();
        }

        for (int i=0;i<consumers_no;i++) {
            consumers[i] = new Consumer(buff);
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
    }
}