package by.epam.task6.exception;

public class ProxyPoolException extends Exception{
    public ProxyPoolException(String message) {
        super(message);
    }

    public ProxyPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProxyPoolException(Throwable cause) {
        super(cause);
    }

    protected ProxyPoolException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
