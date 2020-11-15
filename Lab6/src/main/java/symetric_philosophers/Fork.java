package symetric_philosophers;

import java.util.concurrent.Semaphore;

public class Fork {
    private Semaphore _sem = new Semaphore(1);

    public void pick_up()
    {
        try {
            _sem.acquire();
        } catch (Exception e) {;}
    }

    public void put_down()
    {
        _sem.release();
    }

}
