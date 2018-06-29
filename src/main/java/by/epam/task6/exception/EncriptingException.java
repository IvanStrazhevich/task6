package by.epam.task6.exception;

public class EncriptingException extends Exception{
    public EncriptingException() {
    }

    public EncriptingException(String message) {
        super(message);
    }

    public EncriptingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncriptingException(Throwable cause) {
        super(cause);
    }

    public EncriptingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
