package lab2.ex5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Ex6 {
    public static final int SIZE = 20000000;
    public static final double RANGE = 1000.0;
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); //start time to run all three sum methods
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Callable<Double> task1 = new SumCallable();
        Future<Double> future1 = executor.submit(task1);
        Callable<Double> task2 = new SumCallable();
        Future<Double> future2 = executor.submit(task2);
        Callable<Double> task3 = new SumCallable();
        Future<Double> future3 = executor.submit(task3);

        try{
            System.out.println("sum1 = " + future1.get() + "\nsum2 = " + future2.get() + "\nsum3 = " + future3.get());
        } 
        catch (InterruptedException exc){exc.printStackTrace();}
        catch (ExecutionException exc){exc.printStackTrace();}

        System.out.println("Calculations for three sets of " + SIZE + " values took " + (System.currentTimeMillis() - startTime) + "ms");
        executor.shutdown();
    }

    public static class SumCallable implements Callable<Double>{
        private Data data = new Data(SIZE, RANGE);
        private double theSum = 0;

        @Override
        public Double call() throws Exception {
            // TODO Auto-generated method stub
            theSum = data.sum();
            return theSum;
        }
        
    }
}
