package ex2.b;

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
                }
                _sem.release();
            } catch (Exception e) {}
//            if (_sem.tryAcquire()) {
//                if (buff.size() < maxSize) {
//                    buff.offer(i);
//                    written = true;
//                    System.out.println("Written " + i);
//                }
//                _sem.release();
//            }

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
//            if (_sem.tryAcquire()) {
//                if (buff.size() > 0) {
//                    val = buff.poll();
//                    read = true;
//                }
//                _sem.release();
//            }
//            sleep();
        }

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
            consumers[i] = new Consumer(buff, turns_no / producers_no);
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
