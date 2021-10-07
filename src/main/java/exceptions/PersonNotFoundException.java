/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author olive
 */
public class PersonNotFoundException extends Exception {

    private Object body;

    public PersonNotFoundException(String message, Object body) {
        super(message);
        this.body = body;
    }

    public PersonNotFoundException(String message) {
        super(message);
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
