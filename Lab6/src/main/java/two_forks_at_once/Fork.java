package two_forks_at_once;

import java.util.concurrent.Semaphore;

public class Fork {
    private Semaphore _sem = new Semaphore(1);
    private boolean isPickedUp = false;

    public void pick_up()
    {
        try {
            _sem.acquire();
            isPickedUp = true;
        } catch (Exception e) {;}
    }

    public void put_down()
    {
        isPickedUp = false;
        _sem.release();
    }

    public boolean isPickedUp()
    {
        return isPickedUp;
    }

}
