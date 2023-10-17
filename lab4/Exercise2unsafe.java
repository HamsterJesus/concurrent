package lab4;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;


/* File: Example2_unsynchronized.java    
 * Starting point CM3113 Lab3 Exercise 2 */
/**
 * This version of exercise 2 is unsynchronized. The version suffers from "lost
 * updates" to the shared static variables resource and used.
 */
public class Exercise2unsafe {

    public static void main(String[] args) {
        long NUMBER_RESOURCES = 10000000L;
        ResourceUser.setResources(NUMBER_RESOURCES);
        ArrayList<ResourceUser> users = new ArrayList<ResourceUser>();
        int N = 10;
        ReentrantLock lock = new ReentrantLock();

        for(int i = 0 ; i < N; i++){
            users.add(new ResourceUser("user"+i, lock));
        }
        
        ExecutorService exector = Executors.newCachedThreadPool();
        for(ResourceUser user: users) exector.execute(user);
        
        System.out.println("Total should be constant:" + NUMBER_RESOURCES);
        while (ResourceUser.getResourceLeft() > 0) {
            try {
                Thread.sleep(100);
                System.out.println(ResourceUser.getReport());
            } catch (InterruptedException e) {
            }
            
        }
    }

    /* Nested class for our ResourceUser objects */
    static class ResourceUser extends Thread {

        private static long resource = 100000000L;
        private static long used = 0L;
        private static ReentrantLock ruLock;


        public ResourceUser(String name, ReentrantLock lock) {
            super(name);
            ruLock = lock;
        }

        public void run() {
                while (getResourceLeft() > 0) {
                        takeResource();
                        try{Thread.sleep(0L);}catch(InterruptedException ex){}    
                        //useResource();
                }
        }

        public static void takeResource() {
            try{
                ruLock.lock();
                if(resource<1){
                    return;
                }

                resource--;
                used++;
            }finally{
                ruLock.unlock();
            }
           
        }

        public static long getResourceLeft() {
            try{
                ruLock.lock();
                return resource;
            } finally{
                ruLock.unlock();
            }
        }

        public static String getReport() {
            String report = "";
            report = "Remaining = " + resource + "   Used = " 
                + used + "  Total = " + (resource + used);
            return report;
        }

        public static void setResources(long nResources) {
            resource = nResources;
        }
    }
}
