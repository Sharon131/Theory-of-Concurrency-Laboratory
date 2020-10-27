package stream;

// PKmon.java

import java.util.ArrayList;
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
        int bufferSize = _buff.getBufferMaxSize();
        for (int i = 0; i < _turns_no; ++i) {
            _buff.put(i);
            try {
                sleep(gen.nextInt(50));
            } catch (Exception ex) {}
        }

        if (_turns_no % bufferSize != 0) {
            for (int i=_turns_no % bufferSize;i< bufferSize;i++) {
                _buff.put(null);
            }
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
            Integer val = _buff.get();
            if (val != null) {
                System.out.println("Read value:" + val);
            }
            try {
                sleep(gen.nextInt(50));
            } catch (Exception ex) {}
        }
    }
}

class Processor extends Thread {
    private Buffer _buff;
    private int _turns_no;
    private int _processor_no;

    public Processor(Buffer buff, int turns_no, int processor_no) {
        _buff = buff;
        _processor_no = processor_no;
        _turns_no = turns_no;
    }

    public void run () {
        for (int i=0;i<_turns_no;i++) {
            _buff.process(_processor_no);
        }

        int buffSize = _buff.getBufferMaxSize();
        if (_turns_no % buffSize != 0) {
            for (int i=_turns_no % buffSize;i < buffSize; i++) {
                _buff.process(_processor_no);
            }
        }
    }
}

class Buffer {
    private ArrayList<Integer> _buff;
    private int indx = 0;
    private int stage = 0;
    private int maxSize;
    private int lastStage;

    public Buffer(int size, int processors_no) {
        maxSize = size;
        lastStage = processors_no + 1;
        _buff = new ArrayList<>(maxSize);
    }

    public int getBufferMaxSize() {
        return maxSize;
    }

    public synchronized void put(Integer i) {
        while (stage != 0) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        _buff.add(i);
        indx++;

        if (indx == maxSize) {
            indx = 0;
            stage++;
        }
        notifyAll();
    }

    public synchronized void process(int processor_no) {
        while (stage != processor_no) {
            try {
                wait();
            } catch (Exception ex) {}
        }

        Integer val = _buff.get(indx);
        if (val != null) {
            _buff.set(indx, val+processor_no);
        }
        indx++;

        if (indx == maxSize) {
            indx = 0;
            stage++;
        }
        notifyAll();
    }

    public synchronized Integer get() {
        while (stage != lastStage) {
            try {
                wait();
            } catch (Exception ex) { }
        }

        Integer val = _buff.get(indx);
        indx++;

        if (indx == maxSize) {
            stage = 0;
            indx = 0;
        }
        notifyAll();

        return val;
    }
}

public class PKmon {
    private static int turns_no = 98;

    public static void main(String[] args) {
        long start = System.nanoTime();

        Buffer buff = new Buffer(10, 5);
        Producer producer = new Producer(buff, turns_no);
        Processor proc1 = new Processor(buff, turns_no, 1);
        Processor proc2 = new Processor(buff, turns_no, 2);
        Processor proc3 = new Processor(buff, turns_no, 3);
        Processor proc4 = new Processor(buff, turns_no, 4);
        Processor proc5 = new Processor(buff, turns_no, 5);
        Consumer consumer = new Consumer(buff, turns_no);

        producer.start();
        proc1.start();
        proc2.start();
        proc3.start();
        proc4.start();
        proc5.start();
        consumer.start();

        try {
            producer.join();
            proc1.join();
            proc2.join();
            proc3.join();
            proc4.join();
            proc5.join();
            consumer.join();
        } catch (Exception ex) { }

        long end = System.nanoTime();

        float elapsed = (end - start)/1000000;
        System.out.println("Elapsed time in ms:" + elapsed);
    }
}

