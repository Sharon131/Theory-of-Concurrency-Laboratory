package locking;

public class LockingListThread extends Thread {
    private LockingList _list;

    public LockingListThread(LockingList list)
    {
        _list = list;
    }

    public void run()
    {
        for (Integer i=0;i<10;i++)
        {
            _list.add(i);
        }
    }
}
