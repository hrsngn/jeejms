package consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Consumer {
    private static String NO_MESSAGE = "no message";

    private Connection connection;
    private MessageConsumer messageConsumer;
    private ConnectionFactory connectionFactory;
    private Session session;

    private void setUp() throws JMSException{
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
    }

    public void createQueue(String destinationName) throws JMSException{
        setUp();
        Destination destination = session.createQueue(destinationName);
        messageConsumer = session.createConsumer(destination);
        connection.start();
    }

    public void createTopic(String destinationName) throws JMSException{
        setUp();
        Topic topic= session.createTopic(destinationName);
        messageConsumer = session.createConsumer(topic);
        connection.start();
    }

    public void close() throws JMSException{
        connection.close();
    }

    public String getMessage(int timeout) throws JMSException{

        Message message = messageConsumer.receive(timeout);
        String text = "";
        if(message!=null){
            TextMessage textMessage = (TextMessage) message;
            text = textMessage.getText();
            System.out.println("CONSUMER RECEIVED MESSAGE :"+text);
        }else {
            System.out.println("CONSUMER RECEIVED NO MESSAGE ");
        }
        return text;
    }
}
