package ex1;

// Race.java
// wyscig

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

class IThread extends Thread {
    private Counter _cnt;
    private Semafor _sem;

    public IThread(Counter c, Semafor sem) {
        _cnt = c;
        _sem = sem;
    }
    public void run() {
        for (int i = 0; i < 100000000; ++i) {
            _sem.P();
            _cnt.inc();
            _sem.V();
        }
    }
}

class DThread extends Thread {
    private Counter _cnt;
    private Semafor _sem;

    public DThread(Counter c, Semafor sem) {
        _cnt = c;
        _sem = sem;
    }
    public void run() {
        for (int i = 0; i < 100000000; ++i) {
            _sem.P();
            _cnt.dec();
            _sem.V();
        }
    }
}

class Race {
    public static void main(String[] args) {
        Counter cnt = new Counter(0);
        Semafor sem = new Semafor();

        IThread it = new IThread(cnt, sem);
        DThread dt = new DThread(cnt, sem);

        it.start();
        dt.start();

        try {
            it.join();
            dt.join();
        } catch(InterruptedException ie) { }

        System.out.println("value=" + cnt.value());
    }
}