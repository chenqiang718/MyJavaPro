package cq.send;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class jmsSend {
    public static void init() {
        ConnectionFactory connectionFactory;
        Connection conn=null;
        Session session=null;
        Queue queue=null;
        MessageProducer product=null;

        connectionFactory=new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        try {
            conn=connectionFactory.createConnection();
            session=conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            queue=session.createQueue("CQ_Queue");
            product=session.createProducer(queue);
            product.setDeliveryMode(DeliveryMode.PERSISTENT);
            int i=0;

            while(true){

                Thread.sleep(2000);
                TextMessage textMessage=session.createTextMessage("你好"+i);
                product.send(textMessage);
                System.out.println(textMessage.getText());
                session.commit();
                i++;
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        init();
    }
}
