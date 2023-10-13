package lab3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Exercise5 {

    // N is shared data 
    private static AtomicInteger N = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
       int numTasks = 2;
       int numPerTask = 10000;
       
       ExecutorService e = Executors.newFixedThreadPool(numTasks);
       for(int i = 0; i < numTasks; i++)  e.execute(new Task(i, numPerTask));
       System.out.println("Threads running = " + Thread.activeCount());      
       e.shutdown();
       e.awaitTermination(1, TimeUnit.SECONDS);
       System.out.print("Actual Total recorded was N = " + N);
       System.out.println(", Total expected was = " + numTasks*numPerTask);

    }

    static class Task extends Thread {
        int n;
        int id;
        int number;

        public Task(int id, int number){
            this.id = id;
            this.number = number;
        }
        @Override public void run() {
            for (int i = 0; i < number ; i++) {
                N.getAndIncrement(); /* adds 1 to overall total */
                n++; /* adds 1 for this thread */
            }
            System.out.println("Thread " + id + " counted n = " + n);         
        }
    }
}

