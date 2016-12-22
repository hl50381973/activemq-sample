package bhz.mq.helloworld;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	//第一步：
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, 
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		//第
		Destination desi = session.createQueue("queue1");
		
		MessageProducer messageProducer = session.createProducer(desi);
		
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		for(int i=1;i<5;i++){
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("我是消息内容，id为:"+i);
			messageProducer.send(textMessage);
		}

		connection.close();
	}
	
	
}
