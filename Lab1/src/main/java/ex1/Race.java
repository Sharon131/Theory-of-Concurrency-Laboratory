// Race.java
// Wyscig

package ex1;

class Counter {
    private int _val;

    public Counter(int n) {
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

// Watek, ktory inkrementuje licznik 100.000 razy
class IThread extends Thread {
    private Counter _counter;
    IThread(Counter counter) {
        _counter = counter;
    }

    public void run() {
        for (int i=0;i<100000;i++){
            _counter.inc();
        }
    }
}

// Watek, ktory dekrementuje licznik 100.000 razy
class DThread extends Thread {
    private Counter _counter;
    DThread(Counter counter) {
        _counter = counter;
    }

    public void run() {
        for (int i=0;i<100000;i++) {
            _counter.dec();
        }
    }
}

class Race {
    public static void main(String[] args) {

        Counter cnt = new Counter(0);

        DThread dThread = new DThread(cnt);
        IThread iThread = new IThread(cnt);

        iThread.start();
        dThread.start();

        try {
            iThread.join();
            dThread.join();
        } catch (InterruptedException ex) {
            System.out.println("error");
        }

        System.out.println("stan=" + cnt.value());
    }
}