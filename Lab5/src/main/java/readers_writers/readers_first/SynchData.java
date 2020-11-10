package readers_writers.readers_first;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchData {
    public Lock lock = new ReentrantLock();
    public Condition write_cond = lock.newCondition();
    public int waiting_readers_no;
    public boolean canWrite = true;
    public int reading_no;
}
