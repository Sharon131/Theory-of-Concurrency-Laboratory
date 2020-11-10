package readers_writers.readers_first;

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
            sleep(gen.nextInt(10));
        } catch (InterruptedException e) {;}
        while (!_readers_sem.tryAcquire()) {}
        _synchData.waiting_readers_no++;
        _synchData.reading_no++;
        if (_synchData.reading_no == 1)
        {
//            System.out.println("Reader waiting for aquiring writer semaphore.");
            while (!_writers_sem.tryAcquire()) {}

            _synchData.canWrite = false;
        }
        _synchData.waiting_readers_no--;
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
            if (_synchData.waiting_readers_no == 0) {
                _synchData.lock.lock();
                _synchData.canWrite = true;
                _synchData.write_cond.signal();
                _synchData.lock.unlock();
//                System.out.println("No more waiting readers. Writers can write.");
            }
        }
        _readers_sem.release();
    }
}
