package sflow.api.exceptionadvice.exception;

/**
 * 중복된 이름의 확장자가 발견되었을 때 발생하는 예외
 */
public class DuplicatedNameException extends RuntimeException {

    public DuplicatedNameException(String message) {
        super(message);
    }
}
