package lab1;

public class lab01e06 {
    public static final int numberOfTasks = 4;
    public static final long MAX = 100000000L;
    public static void main(String[] args) {
        clock myClock = new clock(10);
        myClock.start();

        for(int i = 0; i < numberOfTasks; i++){
            Thread t = new Thread(new Task(i));
            t.start();
        }
    }

    static class Task implements Runnable{
            private int id;

            public Task(int id){ //constucts constructingly
                this.id=id;
            }

            @Override public void run(){
                long sum = 0;
                for(long i=0; i<MAX; i++){
                    sum++;
                    if(sum % 1000000 == 0){
                        System.out.println("Thread " + id + " is at " + sum);
                    }
                }
            }
        }
}
