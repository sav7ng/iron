package run.aquan.iron.system.exception;

/**
 * @Class IronException
 * @Description TODO
 * @Author Aquan
 * @Date 2020/3/4 18:08
 * @Version 1.0
 **/
public class IronException extends RuntimeException {

    public IronException() {
        super("Iron System Expiration");
    }

    public IronException(String message) {
        super(message);
    }

}