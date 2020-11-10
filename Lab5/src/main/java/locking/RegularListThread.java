package locking;

public class RegularListThread extends Thread{
    RegularList _list;

    public RegularListThread(RegularList list)
    {
        _list = list;
    }

    public void run() {

        for (Integer i=0;i<10;i++)
        {
            _list.add(i);
        }
    }
}
