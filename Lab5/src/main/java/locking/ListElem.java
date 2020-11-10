package locking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ListElem {
    public Object _object;
    public ListElem _nextElem;
    public Lock _lock = new ReentrantLock();

    public ListElem(Object object, ListElem next)
    {
        _object = object;
        _nextElem = next;
    }
}
