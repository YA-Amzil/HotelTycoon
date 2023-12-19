package be.pxl.hotel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import be.pxl.hotel.api.response.ToastrErrorResponseDTO;
import be.pxl.hotel.exception.ConstructionViolationException;
import be.pxl.hotel.exception.DuplicateFacilityException;
import be.pxl.hotel.exception.InvalidBookingException;
import be.pxl.hotel.exception.UnsufficientMoneyException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ToastrErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        var errorList = ex.getBindingResult().getAllErrors();
        return new ResponseEntity<>(new ToastrErrorResponseDTO(errorList.get(0).getDefaultMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ToastrErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ToastrErrorResponseDTO(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsufficientMoneyException.class)
    public ResponseEntity<ToastrErrorResponseDTO> handleUnsufficientMoneyException(UnsufficientMoneyException ex) {
        return new ResponseEntity<>(new ToastrErrorResponseDTO(ex.getMessage()), HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(ConstructionViolationException.class)
    public ResponseEntity<ToastrErrorResponseDTO> handleConstructionViolationException(
            ConstructionViolationException ex) {
        return new ResponseEntity<>(new ToastrErrorResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateFacilityException.class)
    public ResponseEntity<ToastrErrorResponseDTO> handleDuplicateFacilityException(DuplicateFacilityException ex) {
        return new ResponseEntity<>(new ToastrErrorResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ToastrErrorResponseDTO> handleInvalidBookingException(InvalidBookingException ex) {
        return new ResponseEntity<>(new ToastrErrorResponseDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
