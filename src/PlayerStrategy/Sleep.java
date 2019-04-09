package PlayerStrategy;

public class Sleep {

    private static int sleep = 500;

    /**
     * setter
     * @param time new time
     */
    public static void setSleepTime(int time){
        sleep = time;
    }

    /**
     * getter
     * @return time
     */
    public static int getSleepTime(){
        return sleep;
    }

}
