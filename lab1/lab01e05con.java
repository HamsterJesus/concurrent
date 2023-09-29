package lab1;
import java.time.*;

public class lab01e05con {
    public static final long Max = 1000000L;
    private static int tasksLeft;
    public static final int numberOfTasks = 4;
    private static LocalTime mainstart;
    public static void main(String[] args) {
        mainstart = LocalTime.now();
        System.out.println("Main thread started at " + mainstart);
        System.out.println("This machine has " + java.lang.Runtime.getRuntime().availableProcessors() + " processors avilable");
        tasksLeft = numberOfTasks;

        Thread[] tasks = new Thread[numberOfTasks];
        for(int i = 0; i<numberOfTasks; i++){
            tasks[i] = new Thread(new task(i));
        }

        for(Thread task:tasks){
            task.start();
        }
    }

    static class task extends Thread{

        private int id;

        public task(int id) {
            this.id = id;
        }

        @Override public void run(){
             LocalTime start = LocalTime.now(); //note current time
            System.out.println("Task " + id + " started at " + start); //print start time and task id
            long sum = 0;
            for(long i = 0; i < Max; i++){ //long ass loop
                sum++;
                if(id == 0) Thread.yield(); //this makes the task take a fuck of lot longer 
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
}
