import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producer extends Thread {
    private Random gen = new Random();
    private Buffer _buff;
    private Semaphore _sem;
    private int _turns_no;
    private int _maxGetNo;
    private int _minGetNo;

    public Producer (Buffer buff, int minGetNo, int maxGetNo, int turns_no, Semaphore sem) {
       _buff = buff;
       _maxGetNo = maxGetNo;
       _turns_no = turns_no;
       _sem = sem;
       _minGetNo = minGetNo;
    }

    public void run() {
        for (int i=0;i<_turns_no;i++) {
            int num = gen.nextInt(_maxGetNo - _minGetNo) + _minGetNo;
            ArrayList<Integer> list = new ArrayList<>(num);

            for (int j=0;j<num;j++) {
                list.add(gen.nextInt(100));
            }

            _buff.putMany(list);
        }

        try {
            sleep(1);
//            System.out.println("Producer thread ended.");
            _sem.acquire();
        } catch (Exception e) {}
    }
}
