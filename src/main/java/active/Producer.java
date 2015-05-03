package active;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Produces a text message and send it to destinationQueue
 */
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private Connection connection = null;
    private Session session = null;
    private MessageProducer messageProducer = null;

    public void createMessage(String destinationQueue) throws JMSException {
        //create a connection factory
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

        //create a connection
        connection = factory.createConnection();
        connection.start();

        //create a session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create the destination to where messages will be sent
        Destination destination = session.createQueue(destinationQueue);

        //create a message producer for sending messages
        messageProducer = session.createProducer(destination);
    }

    public void close() throws JMSException {
        connection.close();
    }

    public void sendMessage(String firstName, String lastName) throws JMSException {
        String text = firstName + " " + lastName;

        //create a JMS message
        TextMessage message = session.createTextMessage(text);

        //send the message to queue
        messageProducer.send(message);

        LOGGER.debug("Producer : {}", text);
    }


}


