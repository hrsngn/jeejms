import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Subscriber {
    private String clientId;
    private Connection connection;
    private Session session;
    private MessageConsumer messageConsumer;

    private String subscriptionName;

    public void createTopic(String clientId,String topicName, String subscriptionName)throws JMSException{
        this.clientId = clientId;
        this.subscriptionName = subscriptionName;

        //create connection factory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        messageConsumer = session.createDurableSubscriber(topic,subscriptionName);

        connection.start();
    }

    public void removeDurableSubscriber() throws JMSException{
        messageConsumer.close();;
        session.unsubscribe(subscriptionName);
    }

    public void closeConnection() throws JMSException{
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
