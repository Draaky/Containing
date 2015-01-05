package containing;

public class Clock extends Thread {

   public static double tijd = 0;
   public static int time = 30;
   public static int minute = 0;
   public static int hour = 0;
   public static int day = 1;
   public static int month = 1;
   public static int speedup = 1000;

    
    public static void main (String[] args)
    {
     new Thread(new Clock()).start();
             }

   @Override
    public void run() {

        while (true) {

            if (time == 60)
            {
                time = 0;
                minute ++;
            }
            if (minute == 60)
            {
                minute = 0;
                hour++;
            }
            if (hour == 24)
            {
                hour = 0;
                day++;
            }
            if (month == 2 && day > 28)
            {
                day = 1;
                month++;
            }
            else if (month == 1 || month == 3 || month == 5 || month == 7|| month == 8 || month ==10 || month == 12)
            {
                if (day > 31)
                {
                    day = 1;
                    month++;    
                }
                
            }
            else if (day > 30)
            {
                day = 1;
                month++;
            }
            minute = minute + 10;
            tijd = hour + (minute/100);
            if (time == 60)
            {
                //System.out.println("Month: " + month + " day: " + day + "  " + hour + ":" + minute + ":" + time);
            }
            try {

                Clock.sleep(1000/speedup);

            } catch (InterruptedException e) {

            }

            // time++;
//this will print from 0-99, which is 100 seconds, or you can have the while be a boolean
//such as while(alive) {..code..}
        }

    }

}
