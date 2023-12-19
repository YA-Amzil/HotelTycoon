package be.pxl.hotel.exception;

public class UnsufficientMoneyException extends RuntimeException {
    public UnsufficientMoneyException(String message) {
        super(message);
    }
}
