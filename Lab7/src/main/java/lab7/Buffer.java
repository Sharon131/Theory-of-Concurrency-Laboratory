package lab7;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private int bufSize;
    private Queue<Integer> buffer;

    public Buffer(int bufSize) {
        this.bufSize = bufSize;
        this.buffer = new LinkedList<Integer>();
    }

    public void add(Integer object) {
        if (!this.isFull()) {
            this.buffer.add(object);
        } else {
            System.out.println("Attempt to add to overflow buffer");
        }
    }

    public Integer remove() {
        if (this.isEmpty()) {
            System.out.println("Attempt to remove from empty buffer");
            return null;
        } else {
            return buffer.remove();
        }
    }

    public boolean isFull() {
        return buffer.size() == bufSize;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }
}
