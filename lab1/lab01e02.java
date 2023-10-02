 package lab1;

public class lab01e02 {
    public static void main(String[] args) {
        MyThread t0 = new MyThread("t0");
        MyThread t1 = new MyThread("t1");
        //MyThread t2 = new MyThread("t2");
        //MyThread t3 = new MyThread("t3");
        //t0.setDaemon(true); //background thread
        //result: output not seen (done in background)

        //t2.run();//using run() blocked the other threads from being created while the thread was still active
        t0.start(); 
        t1.start();
        //t2.start();
        //t3.start();

        try{Thread.sleep(10L);} //terminates background thread
        catch(InterruptedException e){}
        t0.interrupt();
        //t2.interrupt();
    }

    static class MyThread extends Thread{
        public MyThread(String name){
            super(name);
        }

        @Override public void run(){
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
