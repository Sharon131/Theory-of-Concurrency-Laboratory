package lab7.requests;

import lab7.Buffer;

import java.util.concurrent.CompletableFuture;

public class AddRequest implements MethodRequest {
    private Buffer buffer;
    private Integer value;
    private CompletableFuture<Integer> future;

    public AddRequest(Buffer buffer, Integer value, CompletableFuture<Integer> future) {
        this.buffer = buffer;
        this.value = value;
        this.future = future;
    }

    @Override
    public boolean guard() {
        return !buffer.isFull();
    }

    @Override
    public void call() {
        buffer.add(value);
        future.complete(value);
    }
}
