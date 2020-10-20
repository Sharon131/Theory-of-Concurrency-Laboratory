package ex1;

class Semafor {
    private boolean _state = true;
    private int _waiting = 0;

    public Semafor() {
        ;
    }

    public synchronized void P() {
        while (!_state) {
            _waiting += 1;
            try {
                wait();
            } catch (Exception ex) {
                System.out.println("error");
            }
        }
        _state = false;
        _waiting -= 1;
    }

    public synchronized void V() {
        if (_waiting != 0) {
            notify();
        }
        _state = true;
    }
}

