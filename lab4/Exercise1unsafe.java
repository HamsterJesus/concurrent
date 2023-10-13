/* File: Example1_unsynchronizedExercise4.java    Starting point CM3113 Lab4 Exercise 1 */
package lab4;

import java.util.concurrent.locks.ReentrantLock;

/**
 * This version of exercise 1 is unsynchronized. The version suffers from "lost
 * updates" to the shared object countShared. The effect of lost updates is
 * easily verified by running each of the CountingThread threads for a fixed
 * number of iterations and comparing the final sum of theLoopCount for each
 * thread and theTotalLoopCount
 */
public class Exercise1unsafe {
    
    public static boolean running;

    public static void main(String[] args) {
        
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Counter sharedCounter = new Counter();
        ReentrantLock lockmain = new ReentrantLock(); 
        CountingThread t1 = new CountingThread(counter1, sharedCounter, "t1", lockmain);
        CountingThread t2 = new CountingThread(counter2, sharedCounter, "t2", lockmain);
        
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();

        running = true;
        while(running) { // wake up main() occasionally and test state of counters
            long count1, count2, countTotal;
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
            }

            /* capture state of current counts */
             try{
                lockmain.lock();
                count1 = counter1.getCount();
                count2 = counter2.getCount();
                countTotal = sharedCounter.getCount();
                
            } finally {
                lockmain.unlock();
            }

            System.out.println("Actual C1 + C2: " + (count1 + count2)
                    + ", Total Recorded " + countTotal
                    + ", Lost: " + (count1 + count2 - countTotal));
            
            if(countTotal > 100000000) running = false;
        }
    }
    
    static class Counter { /* Nested class for Counter objects */

        private volatile long theCount;
        ReentrantLock lockcount = new ReentrantLock();

        public Counter() {
            theCount = 0L;
        }

        public void increment() {
            try{
                lockcount.lock();
                theCount++;
            }finally{
                lockcount.unlock();
            }
            
        }

        public long getCount() {
            try{
                lockcount.lock();
                return theCount;
            }finally{
                lockcount.unlock();
            }
            
        }
    }
    
    /* creating a nested class for the CountingThread - could make as 
     * separate file but since CountingClass is only used in Example 1
     * saves having multiple files and naming conflicts if we make changes */
    static class CountingThread extends Thread {
        ReentrantLock thislockshare = new ReentrantLock(); 
        private Counter thisCounter;
        private Counter sharedCounter;

        public CountingThread(Counter counter, Counter shared, String name, ReentrantLock lockshare) {
            super(name);
            thisCounter = counter;
            thislockshare = lockshare;
            //sharedCounter = shared;
            try{
                thislockshare.lock();
                sharedCounter = shared;
            } finally {
                thislockshare.unlock();
            }
            /* if CoutingThread is non-daemon then it will continue after main
               method has finishes, and prevent program from closing */
            //this.setDaemon(true);
            
        }

        public void run() {
            while(true) {   // start of another loop
                //try {Thread.sleep(10L);} catch (InterruptedException e) {}
                      // count one more loop for this thread 
                      // count one more loop for all threads
                try{
                    thislockshare.lock();
                    thisCounter.increment();
                    sharedCounter.increment();
                } finally {
                    thislockshare.unlock();
                }
            }
        }
    }
}





