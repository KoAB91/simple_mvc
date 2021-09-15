package org.example.app.exceptions;

public class BookShelfFileUploadException extends Exception {

    private final String message;

    public BookShelfFileUploadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
