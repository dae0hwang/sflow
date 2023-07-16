package sflow.api.exceptionadvice;

import static sflow.api.exceptionadvice.enumforexception.ExtensionExceptionEnum.DUPLICATED_NAME;
import static sflow.api.exceptionadvice.enumforexception.ExtensionExceptionEnum.NAME_VALIDATION;
import static sflow.api.exceptionadvice.enumforexception.ExtensionExceptionEnum.OVER_SIZE;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sflow.api.controller.ExtensionManageController;
import sflow.api.dto.ErrorInformationDto;
import sflow.api.exceptionadvice.exception.DuplicatedNameException;
import sflow.api.exceptionadvice.exception.ExtensionNameException;
import sflow.api.exceptionadvice.exception.OverSizeException;
import sflow.api.exceptionadvice.threadlocal.ErrorInformationTlsContainer;

/**
 * 각 예외가 발생했을 때 예외 정보를 ThreadLocal<ErrorInformationDto>에 채워주는 ExceptionAdvice
 */
@RestControllerAdvice(assignableTypes = {ExtensionManageController.class})
@RequiredArgsConstructor
public class ExtensionExceptionAdvice {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;

    @ExceptionHandler(DuplicatedNameException.class)
    public ResponseEntity<Void> duplicatedNameExceptionAdvice() {
        ThreadLocal<ErrorInformationDto> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorType().equals("none")) {
            throw new RuntimeException("errorInformationTls is not Empty");
        }
        threadLocal.set(new ErrorInformationDto(
            DUPLICATED_NAME.getErrorType(), DUPLICATED_NAME.getErrorMessage()));
        return new ResponseEntity<>(DUPLICATED_NAME.getHttpStatus());
    }

    @ExceptionHandler(ExtensionNameException.class)
    public ResponseEntity<Void> extensionNameExceptionAdvice() {
        ThreadLocal<ErrorInformationDto> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorType().equals("none")) {
            throw new RuntimeException("errorInformationTls is not Empty");
        }
        threadLocal.set(new ErrorInformationDto(
            NAME_VALIDATION.getErrorType(), NAME_VALIDATION.getErrorMessage()));
        return new ResponseEntity<>(NAME_VALIDATION.getHttpStatus());
    }

    @ExceptionHandler(OverSizeException.class)
    public ResponseEntity<Void> overSizeExceptionAdvice() {
        ThreadLocal<ErrorInformationDto> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorType().equals("none")) {
            throw new RuntimeException("errorInformationTls is not Empty");
        }
        threadLocal.set(new ErrorInformationDto(
            OVER_SIZE.getErrorType(), OVER_SIZE.getErrorMessage()));
        return new ResponseEntity<>(OVER_SIZE.getHttpStatus());
    }
}
