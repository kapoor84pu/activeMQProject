package active;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Consumes text message from a queue
 */
public class Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    private static String No_Message = "no message";

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageConsumer consumer = null;

    public void receiveMessage(String destinationQueue) throws JMSException {
            //create a connection factory
            factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_BROKER_URL);

            //create a connection
            connection = factory.createConnection();
            connection.start();

            //create a session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //create destination from where it will consume message
            destination = session.createQueue(destinationQueue);

            //create message consumer
            consumer = session.createConsumer(destination);
    }

    public void close() throws JMSException {
        connection.close();
    }

    public String consumeMessage(int timeout) throws JMSException {
        String textMessage = No_Message;
        Message message = consumer.receive(timeout);

        if (message instanceof TextMessage) {
            TextMessage text = (TextMessage) message;
            textMessage = text.getText();

            LOGGER.debug("Consumer : {}", textMessage);
        } else {
            LOGGER.debug(" received no message");
        }
        return textMessage;
    }


}


