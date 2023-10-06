package lab03;

import java.util.concurrent.atomic.AtomicLong;

/* File: Example2_unsynchronized.java    
 * Starting point CM31133 Lab3 Exercise 2 */
/**
 * This version of exercise 2 is unsynchronized. The version suffers from "lost
 * updates" to the shared static variable resource and used.
 */
public class Example2_unsynchronized {

    public static void main(String[] args) {
        AtomicLong NUMBER_RESOURCES = new AtomicLong(100000000L);
        ResourceUser.setResources(NUMBER_RESOURCES);
        ResourceUser t1 = new ResourceUser("t1");
        ResourceUser t2 = new ResourceUser("t2");
        t1.start();
        t2.start();
        System.out.println("Total should be constant:" + NUMBER_RESOURCES);
        while (ResourceUser.getResourceLeft().longValue() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(ResourceUser.getReport()
            );
        }
    }

    /* Nested class for our ResourceUser objects */
    static class ResourceUser extends Thread {

        private static AtomicLong resource = new AtomicLong(100000000L);
        private static AtomicLong used = new AtomicLong(0L);

        public ResourceUser(String name) {
            super(name);
        }

        public void run() {
                while (resource.longValue() > 0) {
                    takeResource();
                    //Thread.yield();
                    useResource(); 
                    Thread.yield();                   
                    /* Use of yield has no effect on logic of the code, 
                    * but it increase the non-safe
                    * effects observed by introducing more context switches */
                }
        }

        public static synchronized void takeResource() {
            resource.getAndDecrement();
        }

        public static synchronized void useResource() {
            used.getAndIncrement();
        }

        public static AtomicLong getResourceLeft() {
            return resource;
        }

        public static AtomicLong getResourceUsed() {
            return used;
        }

        public static synchronized String getReport() {
            return "Remaining = " + resource + "   Used = " + used + "  Total = " + (resource.longValue() + used.longValue());
        }

        public static void setResources(AtomicLong nResources) {
            resource = nResources;
        }
    }
}
