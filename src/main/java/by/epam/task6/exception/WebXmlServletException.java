package by.epam.task6.exception;

public class WebXmlServletException extends Exception {
    public WebXmlServletException() {
    }

    public WebXmlServletException(String message) {
        super(message);
    }

    public WebXmlServletException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebXmlServletException(Throwable cause) {
        super(cause);
    }

    public WebXmlServletException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
