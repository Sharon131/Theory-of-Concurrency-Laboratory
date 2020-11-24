package lab7;

import lab7.requests.AddRequest;
import lab7.requests.RemoveRequest;

import java.util.concurrent.CompletableFuture;

// lab7.Proxy implementation
public class Proxy {
    private Scheduler scheduler;
    private Buffer buffer;

    public Proxy(Scheduler scheduler, Buffer buffer) {
        this.scheduler = scheduler;
        this.buffer = buffer;
    }

    CompletableFuture<Integer> put(Integer toPut) {
        var future = new CompletableFuture<Integer>();
        scheduler.enqueue(new AddRequest(buffer, toPut, future));
        return future;
    }

    CompletableFuture<Integer> get() {
        var future = new CompletableFuture<Integer>();
        scheduler.enqueue(new RemoveRequest(buffer, future));
        return future;
    }
}
