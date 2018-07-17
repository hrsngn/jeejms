package producer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Producer {
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;
    private ConnectionFactory connectionFactory;

    private void setUp() throws JMSException{
        //create a connection factory
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

        //create a connection
        connection = connectionFactory.createConnection();

        //create a session
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
    }

    public void createQueue(String destinationName) throws JMSException{
            setUp();
            //create destination to which messages will be sent
            Destination destination1 = session.createQueue(destinationName);

            //create a nessage oridycer for sending messages
            messageProducer = session.createProducer(destination1);
    }

    public void close() throws JMSException{
        connection.close();
    }

    public void sendMessage(String message) throws JMSException{
        //create a jms TextMessage
        TextMessage textMessage = session.createTextMessage(message);
        //send message to the queue destination
        messageProducer.send(textMessage);
        System.out.println("PRODUCER SENT MESSAGE : "+message);
    }
}
