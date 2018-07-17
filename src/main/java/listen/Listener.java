package listen;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listener implements MessageListener {

    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println("RECEIVED MESSAGE : "+ ((TextMessage) message).getText());
        }catch (JMSException e){
            System.out.println("ERROR OCCURRED : "+e);
        }
    }
}
