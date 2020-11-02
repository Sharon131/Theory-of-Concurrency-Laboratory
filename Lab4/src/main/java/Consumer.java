import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Consumer extends Thread {
    private Random gen = new Random();
    private Buffer _buff;
    private int _turns_no;
    private int _maxGetNo;
    private Semaphore _sem;
    private int _minGetNo;

    public Consumer (Buffer buff, int minGetNo, int maxGetNo, int turns_no, Semaphore sem) {
        _buff = buff;
        _maxGetNo = maxGetNo;
        _turns_no = turns_no;
        _sem = sem;
        _minGetNo = minGetNo;
    }

    public void run() {

        for (int i=0;i<_turns_no;i++) {
            int num = gen.nextInt(_maxGetNo - _minGetNo) + _minGetNo;
            ArrayList<Integer> list = _buff.getMany(num);
//            list.forEach((n) -> System.out.println("Read value: " + n));
        }

        try {
            sleep(1);
//            System.out.println("Consumer thread ended.");
            _sem.acquire();
        } catch (Exception e) {}
    }
}
