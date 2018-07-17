import consumer.Consumer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import producer.Producer;

import javax.jms.JMSException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TopicTest {
    private static Publisher publisher;
    private static Subscriber subscriber1;
    private static Subscriber subscriber2;

    @BeforeClass
    public static void setUpBeforeClass() throws JMSException {
        publisher = new Publisher();
        publisher.createTopic("publisher-123","helloworld.topic1");

        subscriber1 = new Subscriber();
        subscriber2 = new Subscriber();
        subscriber1.createTopic("sub1","helloworld.topic1","subscriber1");
        subscriber2.createTopic("sub2","helloworld.topic1","subscriber2");
    }

    @AfterClass
    public static void tearDownAfterClass() throws JMSException{
        publisher.close();
        subscriber1.closeConnection();
        subscriber2.closeConnection();
    }

    @Test
    public void testProducerTopic(){
        try {
            publisher.sendMessage("For all SUBS to topic1 !");
        }catch (JMSException e){
            fail("a JMS Exception occurred !");
        }
    }

    @Test
    public void testConsumerTopic(){
        try{
            String text = subscriber1.getMessage(1000);
            String text2 = subscriber2.getMessage(1000);
//            assertEquals("Hello Consumer !",text);
        }catch (JMSException e){
            fail("a JMS Exception occurred !");
        }
    }
}
