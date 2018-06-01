package ch.verdsmanFramework.verdsmanMessagingClient;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCDoubleMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCIntegerMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageEnvelope;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCStringMessage;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class VerdsmanMQTTMessagingClient extends VerdsmanMessagingClient implements MqttCallback {
	private  MqttClient mqttclient;
	private VMCMqttFactory mqttfactory;
	private VMCFactory vmcFactory;
	private VMCMessageJSONParser vmcMessageJSONParser;
	
	public VerdsmanMQTTMessagingClient(
			IVMCMessageReceiver receiver,
			MqttClient mqttclient,
			VMCMqttFactory mqttFactory,
			VMCFactory vmcFactory,
			VMCMessageJSONParser vmcMessageJSONParser) {
		
		super(receiver);
		this.mqttclient = mqttclient;
		this.mqttfactory = mqttFactory;
		this.vmcFactory = vmcFactory;
		this.vmcMessageJSONParser = vmcMessageJSONParser; 

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
	public void postMessage(UMCMessageEnvelope message) {
		if(this.clientIsConnected()) {
			MqttMessage mqttMessage = this.mqttfactory.createMqttMessage();
			mqttMessage.setQos(2); // TODO use from config.
			mqttMessage.setRetained(false); // TODO use from config.
			
			String messageString = null;
			//TODO use generics instead of type decision making.
			if(message instanceof UMCStringMessage) {
				messageString = this.vmcMessageJSONParser.messageToJsonString((UMCStringMessage) message);
			}
			if(message instanceof UMCIntegerMessage) {
				messageString = this.vmcMessageJSONParser.messageToJsonString((UMCIntegerMessage) message);
			}
			if(message instanceof UMCDoubleMessage) {
				messageString = this.vmcMessageJSONParser.messageToJsonString((UMCDoubleMessage) message);
			}
			if(messageString == null) { //TODO throw appropriate Exception.
				System.err.println("The VMCMessage has a unknown type! @VerdsmanMQTTMessagingClient::postMessage()");
			}
			
			try {
			mqttMessage.setPayload(messageString.getBytes("UTF-8")); // TODO use UTF-8 etc. from config.
			} catch (UnsupportedEncodingException uee) {
				//TODO handle exception here
				uee.printStackTrace();
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
				//TODO handle exception here
				me.printStackTrace();
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
		this.messageArrived(this.vmcMessageJSONParser.jsonStringToMessage(new String(arg1.getPayload())));
	}
}
