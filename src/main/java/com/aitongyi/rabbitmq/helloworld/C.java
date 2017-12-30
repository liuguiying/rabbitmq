/**
 * TODO
 * 
 */
package com.aitongyi.rabbitmq.helloworld;

import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * 消息消费者
 * 
 * @author hushuang
 * 
 */
public class C {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		//创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置RabbitMQ地址
		factory.setHost("localhost");
		//创建一个新的连接
		Connection connection = factory.newConnection();
		//创建一个频道
		Channel channel = connection.createChannel();
        //声明要关注的队列，幂等性操作
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("C [*] Waiting for messages. To exit press CTRL+C");
		//DefaultConsumer类实现了Consumer接口，通过传入一个频道，告诉服务器我们需要哪个频道的信息，
		//如果频道中有消息就会执行回调函数handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("C [x] Received '" + message + "'");
			}
		};
		//自动回复队列应答，RabbitMQ的消息确认机制
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
