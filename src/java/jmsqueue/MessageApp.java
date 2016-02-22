/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsqueue;

import javax.jms.*;
/**
 *
 * @author Vane
 */
public class MessageApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           try {
        //--------------------------------
        // Establish sender and receiver
        //--------------------------------
        MessageSender sender = new MessageSender();
        sender.connect();

        MessageReceiver receiver = new MessageReceiver();
        receiver.connect();

        //--------------------------------
        // Create a text message
        //--------------------------------
        
        
        
        TextMessage message = sender.createTextMessage();
        message.setText("This is a test text message");

        //--------------------------------
        // Send the message
        //--------------------------------
        System.out.println("Sending message: " + message.getText());
        sender.send(message);

        //--------------------------------
        // Sleep for a few seconds
        //--------------------------------
        try {
          Thread.sleep(3 * 1000);
        } catch (InterruptedException ex) {
        }

        //--------------------------------
        // Retrieve the message
        //--------------------------------
        TextMessage receivedMessage = (TextMessage) receiver.receive();
        System.out.println("Message received: " + receivedMessage.getText());

        //--------------------------------
        // Terminate connections
        //--------------------------------
        sender.disconnect();
        receiver.disconnect();
      } catch (JMSException jmsEx) {
        System.out.println("JMS Exception: " + jmsEx);
      } catch (MessageException msgEx) {
        System.out.println("Message Exception: " + msgEx);
      }
    }
        
}
