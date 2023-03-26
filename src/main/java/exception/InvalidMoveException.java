package exception;

public class InvalidMoveException extends IllegalArgumentException {
    String errormessage;
    String illegalMoveString;


    public InvalidMoveException(String errormessage, String illegalMoveString) {
        this.errormessage = errormessage;
        this.illegalMoveString = illegalMoveString;
    }


    public String getErrormessage() {
        return errormessage;
    }
    public String getIllegalMoveString() {
        return illegalMoveString;
    }



}
