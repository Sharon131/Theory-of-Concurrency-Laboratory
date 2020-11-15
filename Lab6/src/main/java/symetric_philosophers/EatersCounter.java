package symetric_philosophers;

import java.util.concurrent.Semaphore;

public class EatersCounter {
    private int eating_no = 0;
    private Semaphore _sem = new Semaphore(1);
    private int _philosophers_no;

    public EatersCounter(int philosophers_no) {
        _philosophers_no = philosophers_no;
    }

    void increase()
    {
        try
        {
            _sem.acquire();
            eating_no++;
            _sem.release();
        } catch (Exception e) {}
    }

    void decrease()
    {
        try
        {
            _sem.acquire();
            eating_no--;
            _sem.release();
        } catch (Exception e) {}
    }

    boolean canIEat()
    {
        boolean toReturn = false;
        try
        {
            _sem.acquire();
            toReturn = (eating_no < _philosophers_no/2.0f);
            _sem.release();
        } catch (Exception e) {}

        return toReturn;
    }
}
