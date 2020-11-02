import java.util.ArrayList;
import java.util.LinkedList;

public class Buffer {
    private LinkedList<Integer> _buff = new LinkedList<>();
    private int maxSize;
    private int actualSize = 0;

    public Buffer(int size) {
        maxSize = size;
    }
    synchronized void putMany(ArrayList<Integer> list) {

        for (int i=0;i<list.size();i++) {
            put(list.get(i));
        }
    }

    synchronized ArrayList<Integer> getMany(int num) {
        ArrayList<Integer> to_return = new ArrayList<>(num);

        for (int i=0;i<num;i++) {
            to_return.add(this.get());
        }

        return to_return;
    }

    synchronized private Integer get() {
        while (actualSize == 0) {
            try {
                wait();
            } catch (Exception e) {}
        }
        Integer val = _buff.poll();
        actualSize--;

        notify();

        return val;
    }

    synchronized private void put(Integer i) {
        while (actualSize >= maxSize) {
            try {
                wait();
            } catch (Exception ex) {}
        }
        _buff.offer(i);
        actualSize++;

        notify();
    }
}
