package nimblix.in.HealthCareHub.exception;

import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - User not found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 404 - Admin not found
    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAdminNotFound(AdminNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // PaymentException handling
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentException(PaymentException ex) {

        if (ex.getMessage().equals(HealthCareConstants.APPOINTMENT_NOT_FOUND) ||
                ex.getMessage().equals(HealthCareConstants.PAYMENT_NOT_FOUND)) {

            return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        if (ex.getMessage().equals(HealthCareConstants.PAYMENT_NOT_ALLOWED)) {
            return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
        }

        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400 - Other bad requests
    @ExceptionHandler({
            IllegalArgumentException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<Map<String, Object>> handleBadRequest(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 500 - Generic fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                HealthCareConstants.INTERNAL_ERROR);
    }

    // Common Response Builder
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);

        return new ResponseEntity<>(response, status);
    }
}