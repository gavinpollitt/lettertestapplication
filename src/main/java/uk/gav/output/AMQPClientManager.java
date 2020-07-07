package uk.gav.output;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

@Component
@Lazy
public class AMQPClientManager {

	private ConnectionFactory factory;
	private Channel currentChannel;

	@Autowired
	public AMQPClientManager(final OutputConfiguration outputConfig) {
		factory = new ConnectionFactory();
		// "guest"/"guest" by default, limited to localhost connections
		factory.setUsername(outputConfig.getAMQPTarget().getUser());
		factory.setPassword(outputConfig.getAMQPTarget().getPassword());
		factory.setVirtualHost(outputConfig.getAMQPTarget().getVirtualHost());
		factory.setHost(outputConfig.getAMQPTarget().getHost());
		factory.setPort(outputConfig.getAMQPTarget().getPort());
	}
	
	/**
	 * Grab a Rabbit Connection/Channel
	 * 
	 * @param builder
	 */
	public void grabConnection() throws Exception {
		if (this.currentChannel == null) {
			this.currentChannel = factory.newConnection().createChannel();
		}
	}

	public void createQueue(final String qName) throws Exception {

		try {
			grabConnection();
			this.currentChannel.queueDeclare(qName, true, false, false, null);
		} catch (Exception e) {
			throw new Exception("Error creating the queue:" + qName + "," + e);
		}
	}

	public int countMessages(final String queueName) throws Exception {
		grabConnection();
		Queue.DeclareOk response = this.currentChannel.queueDeclarePassive(queueName);
		int mess = response.getMessageCount();
		return mess;
	}

	public void purgeMessages(final String queueName) throws Exception {
		grabConnection();
		this.currentChannel.queuePurge(queueName);
	}

	public void writeMessage(final String qName, final String message) throws Exception {
		grabConnection();
		this.currentChannel.basicPublish("", qName, null, message.getBytes());
	}

	public String readMessage(final String qName) throws Exception {
		grabConnection();

		GetResponse response = this.currentChannel.basicGet(qName, true);

		return new String(response.getBody(), "UTF-8");
	}
	
	public void release() throws Exception {
		this.currentChannel.getConnection().close();
		this.currentChannel = null;
	}
}
