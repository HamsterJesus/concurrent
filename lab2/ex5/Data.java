package lab2.ex5;

public class Data {
    private double[] theData;

    //data constructor
    public Data(int size, double range){
        theData = new double[size];
        fillData(size, range);
    }

    //generate some random data
    public void fillData(int size, double range){
        for(int i=0; i<size;i++){
            theData[i] = Math.random()*range;
        }
    }

    public double sum(){
        System.out.println(Thread.currentThread() + " sum starts at " + System.currentTimeMillis()); //thread sum start time
        double sum = 0.0; //sum is initially 0
        for(int i = 0; i <theData.length; i++){ //iterate through all the data
            sum+=theData[i]; //add theData value at index i to sum of all data
        }

        System.out.println(Thread.currentThread() + " sum ends at " + System.currentTimeMillis()); //thread sum end time
        return sum; 
    }
}
