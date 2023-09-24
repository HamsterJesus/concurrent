package lab1;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class clock extends Thread{
    private long interval;
    private DateTimeFormatter time_format;

    public clock(long time){ //cheeky lil constructor
        interval = time;
        time_format = DateTimeFormatter.ofPattern("HH:mm:ss:SSSS"); //format time and date
        this.setPriority(MAX_PRIORITY); //priority 10
        this.setDaemon(true); //background thread
    }

    @Override public void run(){
        while(true){ //run indefinitely
            System.out.println("Clock says: " + LocalTime.now().format(time_format)); //print time in custom format
            try {
                Thread.sleep(interval); //sleep must always be surrounded by try catch 
            } catch(InterruptedException ex){System.out.println("Sleep Interrupted, I'm gonna feel this in the morning.");}
        }
    }
}
