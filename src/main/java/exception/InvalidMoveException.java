package exception;

import session.SessionMove;

public class InvalidMoveException extends IllegalArgumentException {
    String errormessage;
    SessionMove illegalMove;


    public InvalidMoveException(String errormessage, SessionMove illegalMove) {
        this.errormessage = errormessage;
        this.illegalMove = illegalMove;
    }


    public String getErrormessage() {
        return errormessage;
    }
    public SessionMove getIllegalMove() {
        return illegalMove;
    }



}
