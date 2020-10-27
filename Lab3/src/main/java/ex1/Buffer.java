package ex1;

class Buffer {
    private int value;
    private boolean _to_consume = false;


    public synchronized void put(int i) {
        while (_to_consume) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        _to_consume = true;
        value = i;
        notify();
    }

    public synchronized int get() {
        while (!_to_consume) {
            try {
                wait();
            } catch (Exception ex) { }
        }

        _to_consume = false;
        notify();

        return value;
    }
}

