package ex3;

import java.util.concurrent.TimeUnit;

class PThread extends Thread{
    private SemaforLicz _sem;

    public PThread(SemaforLicz sem) {
        _sem = sem;
    }

    public void run () {
        _sem.P();
        System.out.println("Semaphore taken.");
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception ex) {
            ;
        }
        _sem.V();
        System.out.println("Semaphore returned.");
    }
}

public class Race {
    public static void main(String[] args) {
        int sem_no = 5;
        SemaforLicz sem = new SemaforLicz(sem_no-1);

        PThread pt[] = new PThread[sem_no];

        for (int i = 0; i < sem_no; i++) {
            pt[i] = new PThread(sem);
            pt[i].start();
        }

        try {
            for (int i = 0; i < sem_no; i++) {
                pt[i].join();
            }
        } catch (InterruptedException ie) {
        }
    }
}