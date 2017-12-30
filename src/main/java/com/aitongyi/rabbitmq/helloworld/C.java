/**
 * TODO
 * 
 */
package com.aitongyi.rabbitmq.helloworld;

import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * ��Ϣ������
 * 
 * @author hushuang
 * 
 */
public class C {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		//�������ӹ���
		ConnectionFactory factory = new ConnectionFactory();
		//����RabbitMQ��ַ
		factory.setHost("localhost");
		//����һ���µ�����
		Connection connection = factory.newConnection();
		//����һ��Ƶ��
		Channel channel = connection.createChannel();
        //����Ҫ��ע�Ķ��У��ݵ��Բ���
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("C [*] Waiting for messages. To exit press CTRL+C");
		//DefaultConsumer��ʵ����Consumer�ӿڣ�ͨ������һ��Ƶ�������߷�����������Ҫ�ĸ�Ƶ������Ϣ��
		//���Ƶ��������Ϣ�ͻ�ִ�лص�����handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("C [x] Received '" + message + "'");
			}
		};
		//�Զ��ظ�����Ӧ��RabbitMQ����Ϣȷ�ϻ���
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
