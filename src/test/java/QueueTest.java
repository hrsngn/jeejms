import consumer.Consumer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import producer.Producer;

import javax.jms.JMSException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class QueueTest {
    private static Producer producer;
    private static Consumer consumer;
    private static Consumer consumer2;

    @BeforeClass
    public static void setUpBeforeClass() throws JMSException{
        producer = new Producer();
        producer.createQueue("ggez");

        consumer = new Consumer();
        consumer2 = new Consumer();
        consumer.createQueue("helloworld.q");
        consumer2.createQueue("helloworld.q");
    }

    @AfterClass
    public static void tearDownAfterClass() throws JMSException{
        producer.close();
        consumer.close();
        consumer2.close();
    }

    @Test
    public void testProducer(){
        try {
            producer.sendMessage("Hello ggez !");
        }catch (JMSException e){
            fail("a JMS Exception occurred !");
        }
    }

    @Test
    public void testConsumerQueue(){
        try{
            String text = consumer.getMessage(1000);
            String text2 = consumer2.getMessage(1000);
            assertEquals("Hello Consumer !",text);
        }catch (JMSException e){
            fail("a JMS Exception occurred !");
        }
    }
}