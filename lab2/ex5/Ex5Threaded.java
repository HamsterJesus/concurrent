package lab2.ex5;

public class Ex5Threaded {
    public static final int SIZE = 20000000;
    public static final double RANGE = 1000.0;
    public static void main(String[] args) {
        sumThread t1 = new sumThread();
        sumThread t2 = new sumThread();
        sumThread t3 = new sumThread();

        long startTime = System.currentTimeMillis(); //start time to run all three sum methods
        t1.start();
        t2.start();
        t3.start();
        
        try {
            t1.join();
            t2.join();
            t3.join();
        }
        catch(InterruptedException e){}
        long endTime = System.currentTimeMillis(); //end time to run all three sum methods

        System.out.println("Calculations for three sets of " + SIZE + " values took " + (endTime - startTime) + "ms");
        System.out.println("sum1 = " + t1.getSum() + "\nsum2 = " + t2.getSum() + "\nsum3 = " + t3.getSum());
    }

    static class sumThread extends Thread{
        private Data data = new Data(SIZE, RANGE);
        private double theSum = 0;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            theSum = data.sum();
        }

        public double getSum(){
            return theSum;
        }
        
    }
}
