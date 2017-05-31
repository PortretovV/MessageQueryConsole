import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * Created by VP on 22.05.2017.
 */
public class Main {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            ActiveMQConnectionFactory connectionFactory = new
                    ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                    ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("amqmsg");
            MessageProducer producer = session.createProducer(destination);
            MessageConsumer consumer = session.createConsumer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = session.createTextMessage("THIS IS THE TEST MESSAGE!");
            producer.send(message);
            TextMessage message1 = session.createTextMessage("THIS IS SECOND TEST MESSAGE Brother!");
            producer.send(message1);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
