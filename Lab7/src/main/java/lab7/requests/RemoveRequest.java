package lab7.requests;

import lab7.Buffer;

import java.util.concurrent.CompletableFuture;

public class RemoveRequest implements MethodRequest {
    private Buffer buffer;
    private CompletableFuture<Integer> future;

    public RemoveRequest(Buffer buffer, CompletableFuture<Integer> future) {
        this.buffer = buffer;
        this.future = future;
    }

    @Override
    public boolean guard() {
        return !buffer.isEmpty();
    }

    @Override
    public void call() {
        future.complete(buffer.remove());
    }
}
