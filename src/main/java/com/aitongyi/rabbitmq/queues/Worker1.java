/**
 * TODO
 * 
 */
package com.aitongyi.rabbitmq.queues;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Worker1 {
	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		System.out.println("Worker1 [*] Waiting for messages. To exit press CTRL+C");
		// 每次从队列中获取数量
		channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");

				System.out.println("Worker1 [x] Received '" + message + "'");
				try {
					System.out.println("Worker1 [x] Received '" + message + "'");
					doWork(message);
					System.out.println("Worker1 [x] Received '" + message + "'");
				} finally {
					System.out.println("Worker1 [x] Done");
					System.out.println("Worker1 [x] Done");
					// 消息处理完成确认
					System.out.println("basicACK 这个方法调用了没有呢？");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		// 消息消费完成确认
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
	}

	private static void doWork(String task) {
		try {
			System.out.println("休眠5秒钟开始");
			Thread.sleep(5000); // 暂停1秒钟
			System.out.println("休眠5秒钟结束");
		} catch (InterruptedException _ignored) {
			Thread.currentThread().interrupt();
		}
	}
}
