// Fil5mon.java

class Widelec {
    public void podnies() {

    }
    public void odloz() {

    }
}

class Filozof extends Thread {
    private int _licznik = 0;

    public void run() {
        while (true) {

            // jedzenie
            ++_licznik;
            if (_licznik % 100 == 0) {
                System.out.println("Filozof: " + Thread.currentThread() +
                        "jadlem " + _licznik + " razy");
            }
            // koniec jedzenia

        }
    }
}

public class Fil5mon {
    public static void main(String[] args) {

    }
}
