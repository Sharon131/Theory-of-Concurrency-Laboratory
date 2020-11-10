package readers_writers.readers_first;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Writer extends Thread {
    private Random gen = new Random();
    private Semaphore _sem;
    private SynchData _synchData;

    public Writer(Semaphore sem, SynchData synchData) {
        _sem = sem;
        _synchData = synchData;
    }

    public void run() {
        _synchData.lock.lock();
        try {
            while (!_synchData.canWrite)
            {
                _synchData.write_cond.await();
            }
            while (!_sem.tryAcquire()) {}
//            System.out.println("Writing...");
            int time_to_sleep = 10;
            sleep(time_to_sleep);
//            System.out.println("Writing finished.");
            if (_synchData.waiting_readers_no != 0)
            {
                _synchData.canWrite = false;
            } else {
                _synchData.write_cond.signal();
            }
        } catch (Exception e) {}
        _sem.release();
        _synchData.lock.unlock();
    }
}
