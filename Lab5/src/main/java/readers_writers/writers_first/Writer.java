package readers_writers.writers_first;

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
        try {
            sleep(gen.nextInt(10));
        } catch (InterruptedException e) {;}

        try {
            _synchData.lock.lock();
            _synchData.waiting_writers_no++;
            _synchData.lock.unlock();
//            System.out.println("Writer is waiting to write.");
            while (!_sem.tryAcquire()) {}
            _synchData.lock.lock();
            _synchData.canRead = false;
            _synchData.lock.unlock();
            _synchData.waiting_writers_no--;
//            System.out.println("Writing...");
            int time_to_sleep = 10;
            sleep(time_to_sleep);
//            System.out.println("Writing finished.");

        } catch (Exception e) {}

        if (_synchData.waiting_writers_no == 0) {
            _synchData.lock.lock();
            _synchData.canRead = true;
            _synchData.read_cond.signal();
            _synchData.lock.unlock();
//            System.out.println("No more waiting writers. Readers can read.");
        }

        _sem.release();
    }
}
