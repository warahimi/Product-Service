package com.cwc.product_service.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(ProductNotFoundException ex)
    {
        return error(
                HttpStatus.NOT_FOUND,
                "Product not found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(ProductConflictException.class)
    public ResponseEntity<ProblemDetail> handleConflict(
            ProductConflictException ex
    ) {
        return error(
                HttpStatus.CONFLICT,
                "Product conflict",
                ex.getMessage()
        );
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ProblemDetail> handleOptimisticLock(
            OptimisticLockingFailureException ex
    ) {
        return error(
                HttpStatus.CONFLICT,
                "Concurrent modification",
                "The product has changed. Reload it before retrying."
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(
            MethodArgumentNotValidException ex
    ) {

        String details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " +
                                error.getDefaultMessage()
                )
                .collect(Collectors.joining("; "));

        return error(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                details
        );
    }

    private ResponseEntity<ProblemDetail> error(
            HttpStatus status,
            String title,
            String detail
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatusAndDetail(status, detail);

        problem.setTitle(title);
        problem.setType(URI.create("about:blank"));

        return ResponseEntity
                .status(status)
                .body(problem);
    }
}

