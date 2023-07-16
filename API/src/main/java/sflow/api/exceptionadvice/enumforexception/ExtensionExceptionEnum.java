package sflow.api.exceptionadvice.enumforexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 확장자관련 예외 정보를 저장하는 Enum
 */
@Getter
@AllArgsConstructor
public enum ExtensionExceptionEnum {

    DUPLICATED_NAME("duplicated_name_exception", "중복된 확장자명은 등록할 수 없습니다.",
        HttpStatus.BAD_REQUEST),
    NAME_VALIDATION("name_validation_exception",
        "확장자명은 20자 이하의 영어소문자와 숫자만 등록이 가능합니다.",
        HttpStatus.BAD_REQUEST),
    OVER_SIZE("over_size_exception", "커스텀 확장자는 200개 이하로 등록이 가능합니다.",
        HttpStatus.BAD_REQUEST);

    private final String errorType;
    private final String errorMessage;
    private final HttpStatus httpStatus;
}
