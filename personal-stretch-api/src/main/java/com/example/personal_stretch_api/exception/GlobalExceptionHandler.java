package com.example.personal_stretch_api.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.personal_stretch_api.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 1. カスタム例外（顧客が見つからない等）のハンドリング -> 404 Not Found を返す
    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerNotFoundException(TrainerNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // 2. 予期せぬシステムエラーのハンドリング -> 500 Internal Server Error を返す
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "システムエラーが発生しました。管理者にお問い合わせください。",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ★ バリデーションエラー（@Validの失敗）をハンドリング
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // どのフィールドでどんなエラーが起きたかをMapに詰める
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        // 例: { "adminName": "ログインID（管理者名）は必須です", "adminPassword": "パスワードは必須です" } 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
