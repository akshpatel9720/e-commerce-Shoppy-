package com.category.exception;

public class ProductException
{
    public static class HandleException extends RuntimeException {
        public HandleException(String message) {
            super(message);
        }
    }
}
