package locking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegularList {
    private Lock _lock = new ReentrantLock();
    private ListElem head = null;
    private int sleep_time;

    public RegularList(int sleeping_time)
    {
        sleep_time = sleeping_time;
    }

    boolean contains(Object o)
    {
        _lock.lock();

        ListElem indx = head;
        while (indx != null)
        {
            if (indx._object == o)
            {
                try {
                    Thread.sleep(sleep_time);
                } catch (Exception e) {}
                _lock.unlock();
                return true;
            }
            indx = indx._nextElem;
        }

        _lock.unlock();
        return false;
    }

    boolean remove(Object o)
    {
        _lock.lock();
        if (head == null) {
            return false;
        }
        if (head._object == o)
        {
            try {
                Thread.sleep(sleep_time);
            } catch (Exception e) {}
            head = head._nextElem;
            _lock.unlock();
            return true;
        }
        ListElem indx = head;

        while (indx._nextElem != null)
        {
            if (indx._nextElem._object == o)
            {
                try {
                    Thread.sleep(sleep_time);
                } catch (Exception e) {}
                indx._nextElem = indx._nextElem._nextElem;
                _lock.unlock();
                return true;
            }
            indx = indx._nextElem;
        }
        _lock.unlock();
        return false;
    }

    boolean add(Object o)
    {
        _lock.lock();
        if (head == null)
        {
            try {
                Thread.sleep(sleep_time);
            } catch (Exception e) {}
            head = new ListElem(o, null);
            _lock.unlock();
            return true;
        }

        ListElem indx = head;
        while (indx._nextElem != null)
        {
            indx = indx._nextElem;
        }

        try {
            Thread.sleep(sleep_time);
        } catch (Exception e) {}
        indx._nextElem = new ListElem(o, null);

        _lock.unlock();
        return true;
    }

}
