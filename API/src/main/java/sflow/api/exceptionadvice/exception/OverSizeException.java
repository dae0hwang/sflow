package sflow.api.exceptionadvice.exception;

/**
 * 등록 가능한 Custom 확장자의 총 개수를 초과했을 때 발생하는 예외
 */
public class OverSizeException extends RuntimeException {

    public OverSizeException(String message) {
        super(message);
    }
}
