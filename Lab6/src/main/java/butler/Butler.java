package butler;

import symetric_philosophers.Fork;

import java.util.concurrent.Semaphore;

public class Butler {
    private int _forks_no;
    private Fork [] _forks;
    private boolean [] _pickedUpForks;
    private Semaphore _semaphore = new Semaphore(1);

    public Butler(int forks_no, Fork [] forks)
    {
        _forks_no = forks_no;
        _forks = forks;
        _pickedUpForks = new boolean[forks_no];

        for (int i=0;i<forks_no;i++)
        {
            _pickedUpForks[i] = false;
        }
    }

    public boolean canIEat(int philosopherID)
    {
        boolean toReturn = false;
        try {
            while (!_semaphore.tryAcquire());

            if (!_pickedUpForks[philosopherID] && !_pickedUpForks[(philosopherID+1)%_forks_no])
            {
                _pickedUpForks[philosopherID] = true;
                _pickedUpForks[(philosopherID+1)%_forks_no] = true;
                toReturn = true;
                System.out.println("Butler: Philosopher with id " + philosopherID + " will start to eat.");
            }
            else
            {
                toReturn = false;
            }
            _semaphore.release();
        } catch (Exception e) {;}

        return toReturn;
    }

    public void returnForks(int philosopherID)
    {
        try {
            _semaphore.acquire();

            _pickedUpForks[philosopherID] = false;
            _pickedUpForks[(philosopherID+1)%_forks_no] = false;

            _semaphore.release();
            System.out.println("Butler: Philosopher with id " + philosopherID + " returned forks.");
        } catch (Exception e) {;}
    }
}
