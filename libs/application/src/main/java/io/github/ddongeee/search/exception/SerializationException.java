package io.github.ddongeee.search.exception;

public class SerializationException extends RuntimeException {
    public SerializationException(String errMsg) {
        super(String.format("역직렬화 하는 과정에서 에러가 발생했습니다. error msg: %s", errMsg));
    }
}
