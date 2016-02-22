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
public class MessageSender {
    private MessageConnection messageConnection;

   
    public MessageSender() {
    }

    /********************************************************************
    Crea un mensaje de conexion    
    ********************************************************************/
    private MessageConnection getMessageConnection() throws MessageException {
      if (messageConnection == null) {
        messageConnection = new MessageConnection();
      }
      return messageConnection;
    }

    /********************************************************************
    Obtiene la sesion 
    ********************************************************************/
    private Session getSession() throws MessageException {
      if (getMessageConnection() != null) {
        return getMessageConnection().getSession();
      }
      return null;
    }

    /********************************************************************
    Envia el mensaje 
    ********************************************************************/
    public void send(Message message) throws MessageException {
      if (message != null) {
        getMessageConnection().send(message);
      }
    }

    /********************************************************************
    Estblece la conexion obtinen el mensje enviado 
    ********************************************************************/
    public void connect() throws MessageException {
      getMessageConnection().getProducer();
    }

    
    public void disconnect() throws MessageException {
      if (messageConnection != null) {
        try {
          messageConnection.disconnect();
          messageConnection = null;
        } catch (MessageException e) {
          messageConnection = null;
          e.printStackTrace();
          throw e;
        }
      }
    }

    /********************************************************************
    Crea el mensaje a ser enviado 
    ********************************************************************/
    public TextMessage createTextMessage() throws MessageException {
      TextMessage message = null;

      try {
        message = getSession().createTextMessage();
      } catch (JMSException e) {
        throw new MessageException("JMS Create Text Message Exception: " + e);
      }
      return message;
    }

    public BytesMessage createBytesMessage() throws MessageException {
      BytesMessage message = null;

      try {
        message = getSession().createBytesMessage();
      } catch (JMSException e) {
        throw new MessageException("JMS Create Bytes Exception: " + e);
      }
      return message;
    }

    public ObjectMessage createObjectMessage() throws MessageException {
      ObjectMessage message = null;

      try {
        message = getSession().createObjectMessage();
      } catch (JMSException e) {
        throw new MessageException("JMS Create Object Message Exception: " + e);
      }

      return message;
    }

    
    public MapMessage createMapMessage() throws MessageException {
      MapMessage message = null;

      try {
        message = getSession().createMapMessage();
      } catch (JMSException e) {
        throw new MessageException("JMS Create Map Message Exception: " + e);
      }

      return message;
    }

    
    public StreamMessage createStreamMessage() throws MessageException {
      StreamMessage message = null;

      try {
        message = getSession().createStreamMessage();
      } catch (JMSException e) {
        throw new MessageException("JMS Create Stream Message Exception: " + e);
      }

      return message;
    } 
}
