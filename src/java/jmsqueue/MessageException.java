/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsqueue;

/**
 *
 * @author Vane
 */
public class MessageException extends Exception {

    /********************************************************************
    * Constructor.
    ********************************************************************/
    public MessageException() {
      super();
    }

    /********************************************************************
    * Constructor.
    *
    * @param info Information to be sent along with the exception.
    ********************************************************************/
    public MessageException(String info) {
      super(info);
    }
}
