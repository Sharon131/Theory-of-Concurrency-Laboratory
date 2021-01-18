# Teoria Współbieżności
## Laboratorium 7 - Wzorzec projektowy Active Object.
### Magdalena Pastuła
### Data laboratorium: 17.11.2020

## 1. Zadanie do wykonania.

Celem tego laboratorium było zaimplementowanie problemu producentów i konsumentów przy wykorzystaniu wzorca projektowago Active Object.

## 2. Koncepcja rozwiązania.

Rozwiązanie wykorzytuje wzór projektowy Active Object. Dodatkowo, do zaimplementowania części `Future` wzorca wykorzystano klasę Javy o nazwie `CompletableFuture`.

## 3. Implementacja i wyniki.

Poniżej znajduje się implementacja wątku schedulera:

```Java
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
```

Dodatkowo, poniżej znajduje się implenetacja klasy AddRequest, która obsługuje dodawanie elementów do bufora.

```Java
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
```

Również poniżej znajduje sie implementacja klasy Proxy:

```Java
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
```

Poniżej znajduje się klasa ActiveObject:
```Java
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
```

Natomiast cały kod źródłowy znajduje się w załączonym archiwum.

Poniżej znajduje się zdjęcie kilku pierwszych logów do pokazania działania programu. Dla ułatwienia logi producentów wypisuję się w kolorze niebieskim, a konsumentów w kolorze żółtym. 
Wartości czasowe na początku komunikatów to wartości zwrócone przez funkcję `System.nanoTime()`.

![Początkowe logi wykonującej się aplikacji.](./logi.png)

## 4. Wnioski z ćwiczenia.

Wydawać by się mogło, że aplikacja nie działa, jak powinna: na przykład już w trzeciej linijce konsumer pobiera wartość zanim zostanie dodana do bufora. Jednakże, po porównaniu czasu wypisania komunikatu, który znajduje się na samym jego początku, można zauważyć, że tak naprawdę element został najpierw dodany, a potem zdjęty z bufora. Natomiast różnica czasowa między tymi dwoma wydarzeniami jest na tyle mała, że prawdopodobnie konsument jako pierwszy dostał dostęp do wypisania komunikatu.   
Dodatkowo, rozbieżności czasowe między wypisaniem komunikatu o dodaniu danego elementu do bufora a jego zdjęciem mogą wynikać z faktu, że producent wypisuje swój komunikat już po wykonaniu akcji.

## 5. Bibliografia.
1. [Opis wzorca](https://www.dre.vanderbilt.edu/~schmidt/PDF/Active-Objects.pdf)
2. [Przykład implementacji w Javie](https://www.topcoder.com/thrive/articles/Concurrency%20Patterns%20-%20Active%20Object%20and%20Monitor%20Object)
3. [Dokumentacja klasy CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)