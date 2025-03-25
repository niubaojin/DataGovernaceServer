package com.synway.datarelation.util.v3;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DataxException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public DataxException(ErrorCode errorCode, String errorMessage) {
        super(errorCode.toString() + " - " + errorMessage);
        this.errorCode = errorCode;
    }

    private DataxException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorCode.toString() + " - " + getMessage(errorMessage) + " - " + getMessage(cause), cause);

        this.errorCode = errorCode;
    }

    public static DataxException asDataxException(ErrorCode errorCode, String message) {
        return new DataxException(errorCode, message);
    }

    public static DataxException asDataxException(ErrorCode errorCode, String message, Throwable cause) {
        if (cause instanceof DataxException) {
            return (DataxException) cause;
        }
        return new DataxException(errorCode, message, cause);
    }

    public static DataxException asDataxException(ErrorCode errorCode, Throwable cause) {
        if (cause instanceof DataxException) {
            return (DataxException) cause;
        }
        return new DataxException(errorCode, getMessage(cause), cause);
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
