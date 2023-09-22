package lab1;
//implementing runnable

public class lab01{
    public static void main(String[] args) {
        MyRunnable r = new MyRunnable();
        Thread t0 = new Thread(r,"t0");

        t0.start();
    }

    //implement runnable object that implements runnable
    static class MyRunnable implements Runnable {
        @Override public void run(){ //override run
            //require thread name
            String tname = Thread.currentThread().getName();
            int count = 0; //set counter to 0

            for(;;){ //print indefintely
                System.out.println("This thread is " + tname + " " + count++);
                
                try{Thread.sleep(10000);} //sleeps for speficied amount of ms between loops
                catch(InterruptedException e){}
            }
        }
    }
}
