package run.aquan.iron.system.exception;

/**
 * @Class UserNameAlreadyExistException
 * @Description
 * @Author Saving
 * @Date 2019/12/19 18:04
 * @Version 1.0
 **/
public class UserNameAlreadyExistException extends RuntimeException {

    public UserNameAlreadyExistException() {
    }

    public UserNameAlreadyExistException(String message) {
        super(message);
    }

}
