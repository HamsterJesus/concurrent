package lab1;
import java.time.*;

public class lab01e03 {
    public static final long Max = 1000000000L;
    private static int tasksLeft;
    public static final int numberOfTasks = 64;
    private static LocalTime mainstart;
    public static void main(String[] args) {
        mainstart = LocalTime.now();
        System.out.println("Main thread started at " + mainstart);
        tasksLeft = numberOfTasks;
        int totalRun = 0;

        for(int i = 0; i<numberOfTasks; i++){
            task(i);
            LocalTime finish = LocalTime.now();
            System.out.println("Main thread ended at " + finish + " after running for " + Duration.between(mainstart, finish).toMillis() + " ms");
            totalRun += Duration.between(mainstart, finish).toMillis();
        }

        System.out.println("The total runtime is " + totalRun + " ms");
    }

    public static void task(int id){
        LocalTime start = LocalTime.now(); //note current time
        System.out.println("Task " + id + " started at " + start); //print start time and task id
        long sum = 0;
        for(long i = 0; i < Max; i++){ //long ass loop
            sum++;
        }

        LocalTime finish = LocalTime.now(); //stop the timer once loop has terminated
        System.out.println("Task " + id + " started at " + finish + " with sum " + sum + " after running for "
        + Duration.between(start, finish).toMillis() + "ms");
        tasksLeft--;

        if(tasksLeft == 0){
            System.out.println("Last task ended at " + finish + " total run time for " + numberOfTasks + " tasks is " + Duration.between(mainstart, finish).toMillis() + "ms");
        }
    }
}
