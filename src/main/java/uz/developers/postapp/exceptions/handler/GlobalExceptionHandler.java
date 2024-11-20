package uz.developers.postapp.exceptions.handler;


import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.developers.postapp.exceptions.ResourceNotFoundException;
import uz.developers.postapp.payload.CustomApiResponse;

import java.io.IOException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        CustomApiResponse apiResponse = new CustomApiResponse(message,false,null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<CustomApiResponse> handleIOException(IOException ex) {
        String message = "I/O xatolik yuz berdi: " + ex.getMessage();
        CustomApiResponse apiResponse = new CustomApiResponse(
                message,
                false,
                null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DocumentException.class)
    public ResponseEntity<CustomApiResponse> handleDocumentException(DocumentException ex) {
        String message = "Hujjat xatolik yuz berdi: " + ex.getMessage();
        CustomApiResponse apiResponse = new CustomApiResponse(
                message,
                false,
                null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }













}
