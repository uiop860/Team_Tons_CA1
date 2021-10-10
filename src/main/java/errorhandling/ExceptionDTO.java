/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author jobe
 */
public class ExceptionDTO {

    private int code;
    private String message;
    private Object sentBody;

    public ExceptionDTO(int code, String message, Object sentBody) {
        this.code = code;
        this.message = message;
        this.sentBody = sentBody;
    }

    public ExceptionDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
