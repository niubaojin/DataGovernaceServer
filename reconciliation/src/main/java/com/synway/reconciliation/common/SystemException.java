package com.synway.reconciliation.common;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Administrator
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public SystemException(ErrorCode errorCode, String errorMessage) {
        super(errorCode.toString() + " - " + errorMessage);
        this.errorCode = errorCode;
    }

    private SystemException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorCode.toString() + " - " + getMessage(errorMessage) + " - " + getMessage(cause), cause);
        this.errorCode = errorCode;
    }

    public static SystemException asSystemException(ErrorCode errorCode, String message) {
        return new SystemException(errorCode, message);
    }

    public static SystemException asSystemException(ErrorCode errorCode, String message, Throwable cause) {
        if (cause instanceof SystemException) {
            return (SystemException) cause;
        }
        return new SystemException(errorCode, message, cause);
    }

    public static SystemException asSystemException(ErrorCode errorCode, Throwable cause) {
        if (cause instanceof SystemException) {
            return (SystemException) cause;
        }
        return new SystemException(errorCode, getMessage(cause), cause);
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    private static String getMessage(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Throwable) {
            StringWriter str = new StringWriter();
            PrintWriter pw = new PrintWriter(str);
            ((Throwable) obj).printStackTrace(pw);
            return str.toString();
        } else {
            return obj.toString();
        }
    }
}
