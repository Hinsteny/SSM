package com.hisoka.exception;

/**
 * 应用级别的RunTime异常
 *
 * @author Hinsteny
 * @date 2015年8月11日
 * @copyright: 2015 All rights reserved.
 */
public class ApplicationRunTimeException extends RuntimeException{

    public ApplicationRunTimeException() {
        super();
    }

    public ApplicationRunTimeException(String message) {
        super(message);
    }

    public ApplicationRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationRunTimeException(Throwable cause) {
        super(cause);
    }
}
