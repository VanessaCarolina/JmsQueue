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
import javax.jms.*;
import javax.naming.*;

public class MessageConnection {
    private String destinationName = "jms/myDestination";
    private String factoryName = "jms/myConnectionFactory";
    private Context context;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer messageConsumer;
    private MessageProducer messageProducer;
    private boolean connected = false;

    public MessageConnection() {
    }
    
        public Context getContext() throws MessageException {
      if (context == null) {
        try {
          //-------------------------------------
          // Initialize JNDI.
          //-------------------------------------
          context = new InitialContext();
        } catch (NamingException e) {
          disconnect();
          e.printStackTrace();
          throw new MessageException("Naming Exception: " + e);
        }
      }
      return context;
    }

    /********************************************************************
    * Attempts to connect to the JMS messaging system.
    *
    * @throws MessageException If a connect failure occurs.
    ********************************************************************/
    public MessageConsumer getConsumer() throws MessageException {
      if (messageConsumer == null) {
        try {
        //-------------------------------------
        // Create a message sender.
        //-------------------------------------
          messageConsumer = getSession().createConsumer(getDestination());
          if (!connected) {
            getConnection().start();
            connected = true;
          }
        } catch (JMSException e) {
          disconnect();
          e.printStackTrace();
          throw new MessageException("JMS Create Receiver Exception: " + e);
        }
       }
       return messageConsumer;
    }

    /********************************************************************
    * Attempts to connect to the JMS messaging system.
    *
    * @throws MessageException If a connect failure occurs.
    ********************************************************************/
    public MessageProducer getProducer() throws MessageException {
      if (messageProducer == null) {
        try {
          //-------------------------------------
          // Create a message sender.
          //-------------------------------------
          messageProducer = getSession().createProducer(getDestination());
          if (!connected) {
            getConnection().start();
            connected = true;
          }
        } catch (JMSException e) {
          disconnect();
          e.printStackTrace();
          throw new MessageException("JMS Create Sender Exception: " + e);
        }
      }
      return messageProducer;
    }

    /********************************************************************
    * Attempts to connect to the JMS messaging system.
    *
    * @throws MessageException If a connect failure occurs.
    ********************************************************************/
    public ConnectionFactory getConnectionFactory() throws MessageException {
      if (connectionFactory == null) {
        try {
          //------------------------------------------
          // Lookup the connection factory using JNDI.
          //------------------------------------------
          connectionFactory = (ConnectionFactory) getContext().lookup(factoryName);
        } catch (NamingException e) {
          disconnect();
          e.printStackTrace();
          throw new MessageException("Naming Lookup Factory Exception: " + e);
        }
      }
      return connectionFactory;
    }

   
    public Connection getConnection() throws MessageException {
      if (connection == null) {
        try {
          //-------------------------------------
          // Use the connection factory to create
          // a JMS connection.
          //-------------------------------------
          connection = getConnectionFactory().createConnection();
        } catch (JMSException e) {
          disconnect();
          e.printStackTrace();
          throw new MessageException("JMS Create Queue Connection Exception: " + e);
        }
       }
       return connection;
    }

  
    public Session getSession() throws MessageException {
      if (session == null) {
        try {
          //-------------------------------------
          //usamor para crear la conexion 
          //-------------------------------------
          session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
          disconnect();
          e.printStackTrace();
          throw new MessageException("JMS Create Session Exception: " + e);
        }
      }
      return session;
    }

    
    public Destination getDestination() throws MessageException {
      if (destination == null) {
        try {
          // Lookup destino (queue) usado
          // JNDI. es par designar un destino ppuede ser administrado por JMS servicio

          destination = (Destination) getContext().lookup(destinationName);
         } catch (NamingException e) {
           disconnect();
           e.printStackTrace();
           throw new MessageException("Naming Lookup Queue Exception: " + e);
         }
       }
       return destination;
    }


    public void connect() throws MessageException {
      // make sure a connection and a session established.
      getSession();
      if (!connected) {
        try {
          getConnection().start();
          connected = true;
        } catch (JMSException e) {
          e.printStackTrace();
          connected = false;
          disconnect();
          throw new MessageException("JMS Connection Exception: " + e);
        }
      }
    }

 
    public void disconnect() throws MessageException {
      if (connection != null) {
        try {
          connection.close();
       } catch (JMSException e) {
          e.printStackTrace();
          throw new MessageException("JMS Exception: " + e);
       }
      }

      connection = null;
      session = null;
      messageProducer = null;
      messageConsumer = null;

      connected = false;
    }

    public boolean isConnected() {
       return connected;
    }

   
    public void send(Message message) throws MessageException {
      try {
        getProducer().send(message);
      } catch (JMSException e) {
        e.printStackTrace();
        throw new MessageException("JMS Send Exception: " + e);
      }
    }

    public Message receive(int n) throws MessageException {
      Message message = null;

      try {
        message = getConsumer().receive(n);
      } catch (JMSException e) {
        e.printStackTrace();
        throw new MessageException("JMS Receive Exception: " + e);
      }
      return message;
    }

}
