package sflow.api.exceptionadvice.exception;

/**
 * 등록 가능한 확장자 이름이 규칙을 위반했을 때 발생하는 예외
 * 확장자 이름 규칙 - 확장자명은 20자 이하의 영어소문자와 숫자만 등록이 가능
 */
public class ExtensionNameException extends RuntimeException{

    public ExtensionNameException(String message) {
        super(message);
    }
}
