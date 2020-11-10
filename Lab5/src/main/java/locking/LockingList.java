package locking;

public class LockingList {
    private ListElem head = null;
    private int sleep_time;

    public LockingList(int sleeping_time) {
        sleep_time = sleeping_time;
    }

    boolean contains(Object o)
    {
        ListElem indx = head;
        if (indx != null)
        {
            indx._lock.lock();
        }
        while (indx != null)
        {
            if (indx._object == o)
            {
                try {
                    Thread.sleep(sleep_time);
                } catch (Exception e) {}
                indx._lock.unlock();
                return true;
            }
            if (indx._nextElem != null)
            {
                indx._nextElem._lock.lock();
            }
            indx._lock.unlock();
            indx = indx._nextElem;
        }
        return false;
    }

    boolean remove(Object o)
    {
        if (head == null) {
            return false;
        }
        head._lock.lock();
        if (head._object == o)
        {
            try {
                Thread.sleep(sleep_time);
            } catch (Exception e) {}
            ListElem prev_head = head;
            head = head._nextElem;
            prev_head._lock.unlock();
            return true;
        }
        ListElem indx = head;

        while (indx._nextElem != null)
        {
            indx._nextElem._lock.lock();
            if (indx._nextElem._object == o)
            {
                try {
                    Thread.sleep(sleep_time);
                } catch (Exception e) {}
                indx._nextElem = indx._nextElem._nextElem;
                indx._lock.unlock();
                indx._nextElem._lock.unlock();
                return true;
            }
            indx._lock.unlock();
            indx = indx._nextElem;
        }
        return false;
    }

    boolean add(Object o)
    {
        if (head == null)
        {
            try {
                Thread.sleep(sleep_time);
            } catch (Exception e) {}
            head = new ListElem(o, null);
            return true;
        }

        ListElem indx = head;
        indx._lock.lock();
        while (indx._nextElem != null)
        {
            indx._nextElem._lock.lock();
            indx._lock.unlock();
            indx = indx._nextElem;
        }

        try {
            Thread.sleep(sleep_time);
        } catch (Exception e) {}
        indx._nextElem = new ListElem(o, null);
        indx._lock.unlock();

        return true;
    }
}
