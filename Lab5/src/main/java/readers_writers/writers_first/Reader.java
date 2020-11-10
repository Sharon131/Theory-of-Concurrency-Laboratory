package readers_writers.writers_first;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Reader extends Thread {
    private Random gen = new Random();
    private Semaphore _writers_sem;
    private Semaphore _readers_sem;
    private SynchData _synchData;

    public Reader(Semaphore readers_sem, Semaphore writers_sem, SynchData synchData) {
        _writers_sem = writers_sem;
        _readers_sem = readers_sem;
        _synchData = synchData;
    }

    public void run() {
        try {
            sleep(gen.nextInt(25));
        } catch (InterruptedException e) {;}

        _synchData.lock.lock();
        try {
            while (!_synchData.canRead)
            {
                _synchData.read_cond.await();
            }
        } catch (Exception e) {}
        _synchData.lock.unlock();

        while (!_readers_sem.tryAcquire()) {}
        _synchData.reading_no++;
        if (_synchData.reading_no == 1)
        {
//            System.out.println("Reader waiting for obtaining writer semaphore.");
            while (!_writers_sem.tryAcquire()) {}
        }
        _readers_sem.release();

        try {
//            System.out.println("Reading...");
            int time_to_sleep = 10;
            sleep(time_to_sleep);
//            System.out.println("Reading finished");
        } catch (Exception e) {}

        while (!_readers_sem.tryAcquire()) {}
        _synchData.reading_no--;
        if (_synchData.reading_no == 0) {
            _writers_sem.release();
        }
        if (_synchData.waiting_writers_no != 0)
        {
            _synchData.canRead = false;
        } else {
            _synchData.lock.lock();
            _synchData.read_cond.signal();
            _synchData.lock.unlock();
        }
        _readers_sem.release();
    }
}
