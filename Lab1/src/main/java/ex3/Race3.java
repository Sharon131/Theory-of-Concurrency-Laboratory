package ex3;


import org.knowm.xchart.CategoryChart;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

class Counter2 {
    private int _val;
    public Counter2(int n) {
        _val = n;
    }
    public void inc() {
        _val++;
    }
    public void dec() {
        _val--;
    }
    public int value() {
        return _val;
    }
}

class MySemaphore {
    private boolean semaphoreTaken;
    private LinkedList<Long> queue = new LinkedList<>();

    MySemaphore() {
        semaphoreTaken = false;
    }

    boolean takeSemaphore() {
        if (!semaphoreTaken) {
            semaphoreTaken = true;
            return true;
        } else {
            return false;
        }
    }

    void giveSemaphore() {
        semaphoreTaken = false;

    }
}

// Watek, ktory inkrementuje licznik 100.000 razy
class IThread2 extends Thread {
    private Counter2 _counter;
    private MySemaphore _semaphore;

    IThread2(Counter2 counter, MySemaphore semaphore) {
        _counter = counter;
        _semaphore = semaphore;
    }

    public void run() {
        for (int i=0;i<100000;i++){
            _counter.inc();
        }
    }
}

// Watek, ktory dekrementuje licznik 100.000 razy
class DThread2 extends Thread {
    private Counter2 _counter;
    private MySemaphore _semaphore;

    DThread2(Counter2 counter, MySemaphore semaphore) {
        _counter = counter;
        _semaphore = semaphore;
    }

    public void run() {
        for (int i=0;i<100000;i++) {
            _counter.dec();
        }
    }
}


class Race3 {
    static HashMap <Integer, Integer> hist = new HashMap<Integer, Integer>();

    public static void main(String[] args) {

        for (int i=0;i<100;i++) {

            MySemaphore semaphore = new MySemaphore();
            Counter2 cnt = new Counter2(0);

            DThread2 dThread = new DThread2(cnt, semaphore);
            IThread2 iThread = new IThread2(cnt, semaphore);

            iThread.start();
            dThread.start();

            try{
                iThread.join();
                dThread.join();
            } catch (InterruptedException ex) {
                System.out.println("error");
            }

            Integer val = hist.get(cnt.value());

            hist.remove(cnt.value());

            if (val == null) {
                hist.put(cnt.value(), 1);
            } else {
                hist.put(cnt.value(), val+1);
            }

            System.out.println("stan=" + cnt.value());
        }

        System.out.println("Histogram:");

        Integer [] array = hist.keySet().toArray(new Integer[0]);
        Arrays.sort(array);

        for (Integer i: array) {
            System.out.println(i + ": " + hist.get(i));
        }
        CategoryChart chart = null;
    }
}