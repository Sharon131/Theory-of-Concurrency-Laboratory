package lab7;

public class ActiveObject {
    private Buffer buffer;
    private Scheduler scheduler;
    public Proxy proxy;

    public ActiveObject(int queueSize){
        buffer = new Buffer(queueSize);
        scheduler = new Scheduler(queueSize);
        proxy = new Proxy(scheduler, buffer);
        scheduler.start();
    }
}
