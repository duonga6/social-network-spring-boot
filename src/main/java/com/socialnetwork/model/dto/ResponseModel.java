package com.socialnetwork.model.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseModel <T> {
    private String status;
    private Integer httpStatus;
    private String message;
    private Map<String, String> error;
    private T data;

    public HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(httpStatus);
    }

    public static <T> ResponseModel<T> ok(T data, String message) {
        return ResponseModel.<T>builder()
                .data(data)
                .status("ok")
                .httpStatus(HttpStatus.OK.value())
                .message(message)
                .build();
    }

    public static <T> ResponseModel<T> notFound(String resource) {
        return ResponseModel.<T>builder()
                .data(null)
                .status("failed")
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .message("Not found " + resource)
                .build();
    }

    public static <T> ResponseModel<T> created(T data) {
        return ResponseModel.<T>builder()
                .data(data)
                .status("ok")
                .httpStatus(HttpStatus.CREATED.value())
                .message("Create success")
                .build();
    }

    public static <T> ResponseModel<T> noContent() {
        return ResponseModel.<T>builder()
                .data(null)
                .status("ok")
                .httpStatus(HttpStatus.NO_CONTENT.value())
                .message("Delete success")
                .build();
    }

    public static <T> ResponseModel<T> unAuthorized(String message) {
        return ResponseModel.<T>builder()
                .data(null)
                .status("failed")
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .message(message)
                .build();
    }

    public static <T> ResponseModel<T> badRequest(String message) {
        return ResponseModel.<T>builder()
                .data(null)
                .status("failed")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();
    }

    public static <T> ResponseModel<T> badRequest(String message, Map<String, String> errorList) {
        return ResponseModel.<T>builder()
                .data(null)
                .status("failed")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .error(errorList)
                .build();
    }

    public static <T> ResponseModel<T> internalError(String message) {
        return ResponseModel.<T>builder()
                .status("failed")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .build();
    }
}
