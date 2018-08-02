package cq.cq.jms.receive;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class jmsReceive {
    public static void init(){
        ConnectionFactory connectionFactory;
        Connection conn=null;
        Session session=null;
        MessageConsumer consumer=null;
        Queue queue;

        connectionFactory=new ActiveMQConnectionFactory("admin", "admin", ActiveMQConnection.DEFAULT_BROKER_URL);
        try {
            conn=connectionFactory.createConnection();
            conn.start();
            session=conn.createSession(true, Session.SESSION_TRANSACTED);
            queue=session.createQueue("CQ_Queue");
            consumer=session.createConsumer(queue);
            consumer.setMessageListener((Message message)-> {
                    if(message instanceof TextMessage){
                        try {
                            System.out.println("Consumer:"+((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        init();
    }
}
