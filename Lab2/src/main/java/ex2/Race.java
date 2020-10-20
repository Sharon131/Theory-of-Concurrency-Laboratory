package ex2;


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
        for (int i = 0; i < 1000; ++i) {
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
        for (int i = 0; i < 1000; ++i) {
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

        IThread it[] = new IThread[50];
        DThread dt[] = new DThread[50];

        for (int i=0;i<50;i++) {
            it[i] = new IThread(cnt, sem);
            dt[i] = new DThread(cnt, sem);
            it[i].start();
            dt[i].start();
        }

        try {
            for (int i=0;i<50;i++) {
                it[i].join();
                dt[i].join();
            }
        } catch(InterruptedException ie) { }

        System.out.println("value=" + cnt.value());
    }
}