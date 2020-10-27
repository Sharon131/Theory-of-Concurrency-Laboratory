package ex2.a;

// PKmon.java

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;

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
//                sleep(gen.nextInt(2));
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
//                sleep(gen.nextInt(10));
            } catch (Exception ex) {}
        }
    }
}

class Buffer {
    private LinkedList<Integer> buff = new LinkedList<>();
    private int maxSize;
    private Semaphore _sem = new Semaphore(1);

    public Buffer(int size) {
        maxSize = size;
    }

    public synchronized void put(int i) {
        boolean written = false;
        while (!written) {
            try {
                _sem.acquire();
                if (buff.size() < maxSize) {
                    buff.offer(i);
                    written = true;
                    System.out.println("Written " + i);
                }
                _sem.release();
            } catch (Exception e) {}
        }
    }

    public synchronized int get() {
        boolean read = false;
        int val = 0;

        while (!read) {
            try {
                _sem.acquire();
                if (buff.size() > 0) {
                    val = buff.poll();
                    read = true;
                }
                _sem.release();
            } catch (Exception e) {}
        }
        return val;
    }
}

public class PKmon {
    private static int turns_no = 10;

    public static void main(String[] args) {
        Buffer buff = new Buffer(10);
        Producer producer = new Producer(buff, turns_no);
        Consumer consumer = new Consumer(buff, turns_no);

        producer.start();
        consumer.start();

        System.out.println("Waiting for threads to end.");

        try {
            producer.join();
            consumer.join();
        } catch (Exception ex) { }
    }
}
