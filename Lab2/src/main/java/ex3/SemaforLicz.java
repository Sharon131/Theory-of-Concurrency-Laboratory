package ex3;

public class SemaforLicz {
    private int _state;
    private int _waiting = 0;
    private int _using = 0;
    private Semafor _sems[];

    public SemaforLicz (int number) {
        _state = number;
        _sems = new Semafor[number];

        for (int i=0;i<number;i++) {
            _sems[i] = new Semafor();
        }
    }

    public synchronized void P() {
        while (_using >= _state) {
            _waiting += 1;
            try {
                wait();
            } catch (Exception ex) {
                System.out.println("error");
            }
        }
        _sems[_using].P();
        _using += 1;
    }

    public synchronized void V() {
        if (_waiting != 0) {
            _waiting -= 1;
            notify();
        }
        _using -= 1;
        _sems[_using].V();
    }
}
