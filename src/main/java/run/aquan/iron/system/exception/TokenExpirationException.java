package run.aquan.iron.system.exception;

/**
 * @Class TokenExpirationException
 * @Description TODO 自定义Token过期异常
 * @Author Aquan
 * @Date 2019/12/26 15:21
 * @Version 1.0
 **/
public class TokenExpirationException extends RuntimeException {

    public TokenExpirationException() {
        super("Token Expiration");
    }

    public TokenExpirationException(String message) {
        super(message);
    }

}
