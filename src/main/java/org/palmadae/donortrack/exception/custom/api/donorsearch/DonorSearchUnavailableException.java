package org.palmadae.donortrack.exception.custom.api.donorsearch;

public class DonorSearchUnavailableException extends RuntimeException {
    public DonorSearchUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}