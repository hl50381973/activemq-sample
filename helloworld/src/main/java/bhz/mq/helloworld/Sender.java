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
	
	public static void main(String[] args) throws Exception {
		//第一步：建立ConnectionFactory工厂对象，需要填入用户名、密码、以及要连接的地址，均使用默认即可，默认连接地址为“tcp://localhost:61616”

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER, 
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, 
				"tcp://localhost:61616");
		
		//第二步: 通过ConnectionFactory工厂对象我们创建一个Connection连接，并且调用Connection的start方法开启连接，Conneciton默认是关闭的。
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		//第三步：通过Connection对象创建Session会话（上下文环境对象），用于发送/接收消息，参数配置1为是否启用事务，参数配置2为签收模式，一般我们设置自动签收。
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		
		//第四步：通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费信息来源的对象，在PTP模式中，Destination被称作Queue即是队列：在Pub/Sub模式，Destination被称作Topic即主题。
		Destination desi = session.createQueue("queue1");
		
		//第五步：我们需要通过Session对象创建消息的发送和接受对象（生产者和消费者）MessageProducer/MessageConsumer
		MessageProducer messageProducer = session.createProducer(desi);
		
		//第六步：我们可以使用MessageProducer的setDeliveryMode方法为其设置持久化和非持久化特性（DeliveryMode）
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		//第七步：最后我们使用JMS规范的TextMessage形式创建数据（通过session对象），并用MessageProducer的send方法发送数据，同理客户端使用receive方法进行接受数据。最后不要忘记关闭Connection连接。
		for(int i=1;i<5;i++){
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("我是消息内容，id为:"+i);
			messageProducer.send(textMessage);
		}

		connection.close();
	}
	
	
}
