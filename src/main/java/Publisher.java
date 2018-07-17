import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Publisher {
    private String clientId;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;
    private ConnectionFactory connectionFactory;

    private void setUp(String clientId) throws JMSException{
        //create a connection factory
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

        //create a connection
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);

        //create a session
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
    }

    public void createTopic(String clientId,String topicName) throws JMSException {
        setUp(clientId);
        this.clientId = clientId;
        //create destination to which messages will be sent
        Topic topic = session.createTopic(topicName);

        //create a nessage oridycer for sending messages
        messageProducer = session.createProducer(topic);
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
