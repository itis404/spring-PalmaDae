package org.palmadae.donortrack.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        log.error("Необработанное исключение: ", e);

        ModelAndView modelAndView = new ModelAndView("error");

        modelAndView.addObject("exception", e);

        return modelAndView;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception e) {
        if (!"XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return null;
        }

        log.error("API error", e);

        return ResponseEntity.status(500).body(
                Map.of(
                        "status", "error",
                        "message", e.getMessage()
                )
        );
    }
}
