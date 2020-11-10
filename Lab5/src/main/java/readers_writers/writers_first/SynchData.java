package readers_writers.writers_first;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchData {
    public Lock lock = new ReentrantLock();
    public Condition read_cond = lock.newCondition();
    public int waiting_writers_no;
    public boolean canRead = true;
    public int reading_no;
}

