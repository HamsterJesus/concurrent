/* File: Example1_unsynchronizedExercise4.java    Starting point CM3113 Lab4 Exercise 1 */
package lab03;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This version of exercise 1 is unsynchronized. The version suffers from "lost
 * updates" to the shared object countShared. The effect of lost updates is
 * easily verified by running each of the CountingThread threads for a fixed
 * number of iterations and comparing the final sum of theLoopCount for each
 * thread and theTotalLoopCount
 */
public class Example1_synchronized {

    public static void main(String[] args) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Counter sharedCounter = new Counter();
        CountingThread t1 = new CountingThread(counter1, sharedCounter, "t1");
        CountingThread t2 = new CountingThread(counter2, sharedCounter, "t2");
        t1.start();
        t2.start();

        for (;;) { // wake up main() occasionally and test state of counters
            AtomicLong count1, count2, countTotal = new AtomicLong();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }

            /* capture state of current counts */
            synchronized(sharedCounter){
                count1 = counter1.getCount();
            }

            synchronized(sharedCounter){
                count2 = counter2.getCount();
            }
            
            synchronized(sharedCounter){
                countTotal = sharedCounter.getCount();
            }
                
            

            System.out.println("Actual C1 + C2: " + (count1.longValue() + count2.longValue())
                    + ", Recorded C1 + C2 " + countTotal
                    + ", Lost: " + (count1.longValue() + count2.longValue() - countTotal.longValue()));
        }
    }
    
    static class Counter { /* Nested class for Counter objects */

        
        private volatile AtomicLong theCount = new AtomicLong();

        public Counter() {
            theCount.set(0L);
        }

        public synchronized void increment() {
            theCount.getAndIncrement();
        }

        public AtomicLong getCount() {
            return theCount;
        }
    }
    
    /* creating a nested class for the CountingThread - could make as 
     * separate file but since CountingClass is only used in Example 1
     * saves having multiple files and naming conflicts if we make changes */
    static class CountingThread extends Thread {

        private Counter thisCounter;
        private Counter sharedCounter;

        public CountingThread(Counter counter, Counter shared, String name) {
            super(name);
            thisCounter = counter;
            sharedCounter = shared;
        }

        @Override public void run() {
            for (;;) {   // start of another loop
                //try {Thread.sleep(10L);} catch (InterruptedException e) {}
                synchronized(sharedCounter){

                
                    thisCounter.increment();  // count one more loop for this thread 
                    sharedCounter.increment(); 
                } // count one more loop for all threads
            }
        }
    }
}





