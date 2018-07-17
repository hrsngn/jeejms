package consumer;

import listen.Listener;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ConsumeWithListener {

    public static void main(String[] args){
        String destinationName = "ggez";
        try{
            Session session;
            ConnectionFactory connectionFactory;
            MessageConsumer messageConsumer;
            Connection connection;
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(destinationName);
            messageConsumer = session.createConsumer(destination);
            Listener listener=new Listener();
            messageConsumer.setMessageListener(listener);
            connection.start();
//            while(true){
//                try{
//                    System.out.println("Receiver1 is ready, waiting for messages...");
//                    System.out.println("press Ctrl+c to shutdown...");
//                    Thread.sleep(1000);
//                }catch(InterruptedException e){
//
//                }
//                if(false){
//                    break;
//                }
//            }
        }catch (JMSException e){

        }

    }
}
