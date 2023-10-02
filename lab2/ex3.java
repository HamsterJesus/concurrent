package lab2;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
// import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ex3 {
    private static int numberTasks = 4; //number of tasks total
    private static int numberThreadsInPool = 12; //size of thread pool
    private static int steps = 3;
    private static int stepInterval = 1;

    public static void main(String[] args) {
        System.out.println("This machine has " + Runtime.getRuntime().availableProcessors() + " processors"); //display no of processors
        long mainStart = System.nanoTime(); //start timer of main
        // ArrayList<CallableTask> tasks = new ArrayList<CallableTask>(); //make an array list of object type "RunnableTask"

        ExecutorService e = Executors.newFixedThreadPool(numberThreadsInPool);
        List<Future<Integer>> list = new ArrayList();

        for(int n = 0; n<numberTasks; n++){
            Callable<Integer> r = new CallableTask(n);
            Future<Integer> future = e.submit(r);
            list.add(future);
            
            // tasks.add(r); //add task to task lit
            // e.execute(r); //submit task to executor
        }

        //get result
        Integer result;
        for(Future<Integer> future : list){
            try{
                result = future.get();
                System.out.println(result);
            }
            catch (InterruptedException exc){exc.printStackTrace();}
            catch (ExecutionException exc){exc.printStackTrace();}
        }

        e.shutdown(); //kill thread pool once all tasks are completed

        try{
            e.awaitTermination(20, TimeUnit.SECONDS); //die if it takes 20 seconds to shutdown 
        } catch (InterruptedException ex){
            System.out.println("main: " + (System.nanoTime()-mainStart)/1e6+ "ms");
        }


    }

    static class RunnableTask implements Runnable{
        int id, total;
        long taskStart, calcStart, taskFinish;

        public RunnableTask(int i){
            id = i; total = 0; taskStart = System.nanoTime();
        }

        @Override public void run(){
            calcStart = System.nanoTime();
            for(int j =1; j <= steps; j++){
                work();
                System.out.println(Thread.currentThread() + " running task " + id + " at step " + j);
                pause(stepInterval);
            }
            taskFinish = System.nanoTime();
            System.out.println("Task " + id + " completed, finishing after " + (taskFinish - taskStart) / 1e6 + "ms, running for " + (taskFinish - calcStart) / 1e6 + "ms, of which waiting was " + stepInterval*steps + "ms");
        }

        void work(){
            for(int j = 0; j < 20000; j++){
                total += (int)Math.sqrt(j);
            }
        }

        public int getTotal(){
            return total;
        }

        static void pause(int ms){
            try{
                Thread.sleep(ms);
            }catch (InterruptedException ex){
                System.out.println("Light sleeper " + Thread.currentThread());
            }
        }
    }

    public static class CallableTask implements Callable<Integer>{
        int id, total;
        long taskStart, calcStart, taskFinish;

        public CallableTask(int i){
            id = i; total = 0; taskStart = System.nanoTime();
        }

        @Override
        public Integer call() throws Exception {
            // TODO Auto-generated method stub
            calcStart = System.nanoTime();
            for(int j =1; j <= steps; j++){
                work();
                System.out.println(Thread.currentThread() + " running task " + id + " at step " + j);
                pause(stepInterval);
            }
            taskFinish = System.nanoTime();
            System.out.println("Task " + id + " completed, finishing after " + (taskFinish - taskStart) / 1e6 + "ms, running for " + (taskFinish - calcStart) / 1e6 + "ms, of which waiting was " + stepInterval*steps + "ms");
            return total;
        }

        void work(){
            for(int j = 0; j < 20000; j++){
                total += (int)Math.sqrt(j);
            }
        }

        static void pause(int ms){
            try{
                Thread.sleep(ms);
            }catch (InterruptedException ex){
                System.out.println("Light sleeper " + Thread.currentThread());
            }
        }

    }
}
