package com.basinda.exceptions;

public class FileUploadFailedException extends RuntimeException {
    public FileUploadFailedException() {
        super("Some went wrong.");
    }

    public FileUploadFailedException(String message) {
        super(message);
    }
}