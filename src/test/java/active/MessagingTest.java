package active;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.jms.JMSException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test whether producer produces and consumer consumes messages
 * type following url to see whether "MessageQueue" queue is created or not
 *              -- http://localhost:8161/admin/queues.jsp
 */
public class MessagingTest {
    private static String queueName = "MessageQueue";
    private static Producer producer;
    private static Consumer consumer;

    @BeforeClass
    public static void setUpBeforeClass() throws JMSException {
        producer = new Producer();
        producer.createMessage(queueName);

        consumer = new Consumer();
        consumer.receiveMessage(queueName);
    }

    @AfterClass
    public static void cleanUpAfterClass() throws JMSException {
        producer.close();
        consumer.close();
    }


    @Test
    public void testConsumeMessage() {
        try {
            producer.sendMessage("Alice", "Bob");
            String message = consumer.consumeMessage(1000);
            assertEquals("Alice Bob", message);
        } catch (JMSException e) {
            fail(" error while consuming message");
        }
    }

    @Test
    public void testNoMessage(){
        try {
            String message = consumer.consumeMessage(1000);
            assertEquals("no message", message);
        } catch (JMSException e) {
            fail(" error while consuming message");
        }
    }


}
