package ch.verdsmanFramework.verdsmanMessagingClient;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers.IUMCMessageParser;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers.UMCMessageParserPool;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCCommandMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class VerdsmanMQTTMessagingClient extends VerdsmanMessagingClient implements MqttCallback {
	private  MqttClient mqttclient;
	private VMCMqttFactory mqttfactory;
	private UMCMessageParserPool messageParserPool;
	
	public VerdsmanMQTTMessagingClient(
			IVMCMessageReceiver receiver,
			MqttClient mqttclient,
			VMCMqttFactory mqttFactory,
			VMCFactory vmcFactory,
			UMCMessageParserPool messageParserPool) {
		
		super(receiver);
		this.mqttclient = mqttclient;
		this.mqttfactory = mqttFactory;
		this.messageParserPool = messageParserPool;

		try {
			this.connectToMQTTBroker();
		} catch (MqttSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void connectToMQTTBroker() throws MqttSecurityException, MqttException {
		mqttclient.connect();
		mqttclient.setCallback(this);
	}
	
	
	@Override
	public void postMessage(UMCMessage message) {
		if(this.clientIsConnected()) {
			MqttMessage mqttMessage = this.mqttfactory.createMqttMessage();
			mqttMessage.setQos(2); // TODO use from config.
			mqttMessage.setRetained(false); // TODO use from config.
			
			String messageString = null;
			IUMCMessageParser parser = null;
			
			try {
				parser = this.messageParserPool.getParser(message.parsertype);
			} catch (Exception e) {
				System.err.println("could not get a matching parser from parser pool.\npost failed.\n @VerdsmanMQTTMessagingClient::postMessage()");
				e.printStackTrace();
				return;
			}
			
			if (parser != null) { //TODO check if this case can really happen.
				try {
					messageString = parser.parseObject(message);
				} catch (Exception e) {
					System.err.println("message was not parsed.\npost failed.\n @VerdsmanMQTTMessagingClient::postMessage()");
					e.printStackTrace();
				}
			}			
			
			try {
			mqttMessage.setPayload(messageString.getBytes("UTF-8")); // TODO use UTF-8 etc. from config.
			} catch (UnsupportedEncodingException uee) {
				//TODO handle exception here
				System.err.println("message string could not be encoded into a sequence of bytes.\npost failed.\n @VerdsmanMQTTMessagingClient::postMessage()");
				uee.printStackTrace();
				return;
			}
			try {
				String topic = message.topic;
				if (topic == null || topic.length() == 0) {
					System.err.println("topic to post to is invalid. @VerdsmanMQTTMessagingClient::postMessage()");
					topic = "a38749324753845839124a"; //TODO solve this with throwing exception.
				}
				
				mqttclient.publish(topic, mqttMessage);
				System.out.println("message published");
			} catch(MqttException me) {
				System.err.println("MQTT message could not be published.\npost failed.\n @VerdsmanMQTTMessagingClient::postMessage()");
				me.printStackTrace();
				return;
			}	
		}
	}


	@Override
	public void registerTopic(String topic) {
		if(this.clientIsConnected()) {
			try {
				String topicToSubscribe = topic;
				if(topicToSubscribe == null || topicToSubscribe.length() == 0) {
					System.err.println("topic to subscribe to is invalid. @VerdsmanMQTTMessagingClient::registerTopic()");
					topicToSubscribe = "a38749324753845839124a"; //TODO solve this with throwing exception.
				}
				this.mqttclient.subscribe(topicToSubscribe, 2); // TODO take QoS from config.
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public boolean clientIsConnected() {
		boolean result = this.mqttclient.isConnected();
		if (!result) {
			System.err.println("MQTT Client is not connected. @VerdsmanMQTTMessagingClient::clientIsConnected()");
		}
		return result;
	}

	@Override
	protected void unregisterTopic(String topic) {
		System.err.println("method not implemented!");
	}


	@Override
	public void connectionLost(Throwable arg0) {
		System.err.println("MQTT Client lost its connection. @VerdsmanMQTTMessagingClient::connectionLost()");
		System.err.println(arg0.toString());
	}


	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		System.out.println("delivery completed");		
	}


	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception { //TODO use parser
		System.out.println("message Arrived");
		// parser will catch cases of an invalid MqttMessage.
		//TODO we need to improve pasers with a base class parsing UMCMessages only. So we can then decide which parser
		// we will need to parse the concrete message.
		// for now we assume there are only UMCCommandMessages sent..
		IUMCMessageParser parser = null;
		UMCCommandMessage message = null;
		try {
			parser = this.messageParserPool.getParser("UMCCommandMessageParser");
		} catch (Exception e) {
			System.err.println("could not get a matching parser from parser pool.\npost failed.\n @VerdsmanMQTTMessagingClient::postMessage()");
			e.printStackTrace();
			return;
		}
		
		if (parser != null) { //TODO check if this case can really happen.
			try {
				String rawMessage = new String(arg1.getPayload());
				System.out.println("raw message: " + rawMessage);
				message = (UMCCommandMessage) parser.parseString(rawMessage);
			} catch (Exception e) {
				System.err.println("message was not parsed.\npost failed.\n @VerdsmanMQTTMessagingClient::postMessage()");
				e.printStackTrace();
			}
		}	
		System.out.println("message from: " + message.from);
		this.deliverMessage(message);
	}
}
