package ex1.a;

// PKmon.java

import java.util.LinkedList;

class Producer extends Thread {
    private Buffer _buff;

    public Producer(Buffer buff) {
        _buff = buff;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
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
        for (int i = 0; i < 100; ++i) {
            System.out.println(_buff.get());
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

    public static void main(String[] args) {
        Buffer buff = new Buffer(10);
        Producer producer = new Producer(buff);
        Consumer consumer = new Consumer(buff);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (Exception ex) { }
    }
}