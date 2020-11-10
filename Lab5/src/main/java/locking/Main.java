package locking;

import java.io.File;
import java.io.FileWriter;

public class Main {

    static float reg_iteration(int sleeping_time, int regular_threads_no)
    {
        RegularListThread [] regLists = new RegularListThread[regular_threads_no];
        RegularList _regList = new RegularList(sleeping_time);

        long start1 = System.nanoTime();
        for (int i=0;i<regular_threads_no;i++)
        {
            regLists[i] = new RegularListThread(_regList);
            regLists[i].start();
        }

        for (int i=0;i<regular_threads_no;i++)
        {
            try
            {
                regLists[i].join();
            } catch (Exception e) {}
        }

        long end1 = System.nanoTime();
        float elapsed_time1 = ((end1-start1) / 10000)/100.0f;
        System.out.println("Elapsed time in ms for regular list: " + elapsed_time1);

        return elapsed_time1;
    }

    static float lock_iteration(int sleeping_time, int locking_threads_no)
    {
        LockingListThread [] lockLists = new LockingListThread[locking_threads_no];
        LockingList _lockList = new LockingList(sleeping_time);
        long start2 = System.nanoTime();
        for (int i=0;i<locking_threads_no;i++)
        {
            lockLists[i] = new LockingListThread(_lockList);
            lockLists[i].start();
        }

        for (int i=0;i<locking_threads_no;i++)
        {
            try
            {
                lockLists[i].join();
            } catch (Exception e) {}
        }

        long end2 = System.nanoTime();
        float elapsed_time2 = ((end2-start2) / 10000)/100.0f;
        System.out.println("Elapsed time in ms for locking list: " + elapsed_time2);

        return elapsed_time2;
    }

    public static void main(String[] args) {
        System.out.println("Started");

        int min_sleep_time = 1;
        int max_sleep_time = 10;

        int min_threads_no = 1;
        int max_threads_no = 10;

        try {
            File file = new File("locking.txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter("locking.txt");

            for (int i=min_sleep_time;i<=max_sleep_time;i++)
            {
                for (int j=min_threads_no;j<=max_threads_no;j++) {
                    float lock_time = lock_iteration(i, j);
                    float reg_time = reg_iteration(i, j);
                    fileWriter.write(i + " " + j + " " + Math.round((lock_time - reg_time)*100)/100.0f + "\r\n");
                }
                fileWriter.write("\r\n");
            }
            fileWriter.close();
        } catch (Exception e) {}
    }
}
