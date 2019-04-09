package PlayerStrategy;

/**
 * <h1>Sleep</h1>
 * The sleep class is to set and get the sleep time.
 *
 * @author tianshu_ji
 * @version 3.0
 * @since 2019-04-07
 */
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
