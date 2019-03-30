package Model;

/**
 * <h1>Message</h1>
 * This class stores operation message, like operation succeed or not, error message.
 *
 * @author jiamin_he
 * @version 3.0
 * @since 2019-03-15
 */
public class Message {
    private static boolean success;
    private static String message;
    
    /**
     * This method obtains a signal whether the operation succeed or not.
     *
     * @return A signal whether the operation succeed or not.
     */
    public static boolean isSuccess() {
        return success;
    }

    /**
     * This method updates signal.
     *
     * @param success A signal whether the operation succeed or not.
     */
    public static void setSuccess(boolean success) {
        Message.success = success;
    }

    /**
     * This method obtains error message.
     *
     * @return Error message.
     */
    public static String getMessage() {
        return message;
    }

    /**
     * This method updates error message.
     *
     * @param message A new error message.
     */
    public static void setMessage(String message) {
        Message.message = message;
    }
}
