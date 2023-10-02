package lab2.ex5;

public class Ex5Nonthreaded {
    public static final int SIZE = 20000000;
    public static final double RANGE = 1000.0;
    
    public static void main(String[] args) {
        Data data1 = new Data(SIZE, RANGE);
        Data data2 = new Data(SIZE, RANGE);
        Data data3 = new Data(SIZE, RANGE);

        long startTime = System.currentTimeMillis(); //start time to run all three sum methods
        double sum1 = data1.sum();
        double sum2 = data2.sum();
        double sum3 = data3.sum();
        long endTime = System.currentTimeMillis(); //end time to run all three sum methods

        System.out.println("Calculations for three sets of " + SIZE + " values took " + (endTime - startTime) + "ms");
        System.out.println("sum1 = " + sum1 + "\nsum2 = " + sum2 + "\nsum3 = " + sum3);
    }
}
