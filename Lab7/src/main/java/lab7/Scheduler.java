package lab7;

import lab7.requests.MethodRequest;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread {
    private Queue<MethodRequest> queue;
    private int queueSize;

    public Scheduler(int size) {
        this.queueSize = size;
        this.queue = new ConcurrentLinkedQueue<>();
    }

    void enqueue(MethodRequest request) {
        while(queue.size() == queueSize){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        queue.add(request);
    }

    public void run()
    {
        while(true)
        {
            var request = queue.poll();
            if(request != null && request.guard()){
                request.call();
            } else if(request != null){
                queue.add(request);
            }
        }
    }
}
