package ex2;
// Race2.java
// Wyscig

import org.apache.commons.math3.stat.Frequency;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.*;

class Counter2 {
    private int _val;
    public Counter2(int n) {
        _val = n;
    }
    public void inc() {
        _val++;
    }
    public void dec() {
        _val--;
    }
    public int value() {
        return _val;
    }
}

// Watek, ktory inkrementuje licznik 100.000 razy
class IThread2 extends Thread {
    private Counter2 _counter;
    IThread2(Counter2 counter) {
        _counter = counter;
    }

    public void run() {
        for (int i=0;i<100000;i++){
            _counter.inc();
        }
    }
}

// Watek, ktory dekrementuje licznik 100.000 razy
class DThread2 extends Thread {
    private Counter2 _counter;
    DThread2(Counter2 counter) {
        _counter = counter;
    }

    public void run() {

        for (int i=0;i<100000;i++) {
            _counter.dec();
        }
    }
}


class Race2 {
    static HashMap <Integer, Integer> hist = new HashMap<Integer, Integer>();

    public static void main(String[] args) {
        Frequency frequency = new Frequency();

        for (int i=0;i<100;i++) {

            Counter2 cnt = new Counter2(0);

            DThread2 dThread = new DThread2(cnt);
            IThread2 iThread = new IThread2(cnt);

            iThread.start();
            dThread.start();

            try{
                iThread.join();
                dThread.join();
            } catch (InterruptedException ex) {
                System.out.println("error");
            }

            Integer val = hist.get(cnt.value());

            hist.remove(cnt.value());

            if (val == null) {
                hist.put(cnt.value(), 1);
            } else {
                hist.put(cnt.value(), val+1);
            }

            frequency.addValue(cnt.value());
            System.out.println("stan=" + cnt.value());
        }

        System.out.println("Histogram:");

        Integer [] array = hist.keySet().toArray(new Integer[0]);
        Arrays.sort(array);

        for (Integer i: array) {
            System.out.println(i + ": " + hist.get(i));
        }


        List<Integer> datasetList = Arrays.asList(array);

        List yData = new ArrayList();
        datasetList.forEach(d -> yData.add(frequency.getCount(d)));

        CategoryChart chart = new CategoryChartBuilder().width(800).height(600)
                .title("Counter Value Distribution")
                .xAxisTitle("Counter Values")
                .yAxisTitle("Frequency")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(0.99);
        chart.getStyler().setOverlapped(true);
        chart.getStyler().setHasAnnotations(true);

        chart.addSeries("Counter Value series", datasetList, yData);

        new SwingWrapper<>(chart).displayChart();
    }
}