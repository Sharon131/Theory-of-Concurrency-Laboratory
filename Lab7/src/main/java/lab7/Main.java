package lab7;

public class Main {

    private static int PROD_COUNT = 3;
    private static int CONS_COUNT = 5;
    private static int ACTIVE_OBJECTS = 10;

    public static void main(String[] argv) {
        ActiveObject activeObject = new ActiveObject(ACTIVE_OBJECTS);
        Proxy proxy = activeObject.proxy;
        Producer[] producers = new Producer[PROD_COUNT];
        lab7.Consumer[] consumers = new lab7.Consumer[CONS_COUNT];

        for (int i = 0; i < PROD_COUNT; i++) {
            producers[i] = new Producer(i, proxy);
        }

        for (int i = 0; i < CONS_COUNT; i++) {
            consumers[i] = new lab7.Consumer(i, proxy);
        }

        for (int i = 0; i < CONS_COUNT; i++) {
            consumers[i].start();
        }

        for (int i = 0; i < PROD_COUNT; i++) {
            producers[i].start();
        }

        try
        {
            for (int i = 0; i < CONS_COUNT; i++) {
                consumers[i].join();
            }
            for (int i = 0; i < PROD_COUNT; i++) {
                producers[i].join();
            }
        } catch (Exception e) {}


    }
}
