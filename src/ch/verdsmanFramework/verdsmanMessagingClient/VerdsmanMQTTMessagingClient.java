package ch.verdsmanFramework.verdsmanMessagingClient;

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class VerdsmanMQTTMessagingClient extends VerdsmanMessagingClient implements MqttCallback {
	private  MqttClient mqttclient;
	private VMCMqttFactory mqttfactory;
	private VMCFactory vmcFactory;
	
	public VerdsmanMQTTMessagingClient(IVMCMessageReceiver receiver, MqttClient mqttclient, VMCMqttFactory mqttFactory, VMCFactory vmcFactory) {
		super(receiver);
		this.mqttclient = mqttclient;
		this.mqttfactory = mqttFactory;
		this.vmcFactory = vmcFactory;
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
	public void postMessage(EVMCTopic topic, VMCMessage message) {
		if(this.clientIsConnected()) {
			MqttMessage mqttMessage = this.mqttfactory.createMqttMessage();
			mqttMessage.setQos(2); // TODO use from config.
			mqttMessage.setRetained(false); // TODO use from config.
			try {
			mqttMessage.setPayload(message.toString().getBytes("UTF-8")); // TODO use from config.
			} catch (UnsupportedEncodingException uee) {
				//TODO handle exception here
				uee.printStackTrace();
			}
			try {
				mqttclient.publish("a38749324753845839124a/todo", mqttMessage);
				System.out.println("message published");
			} catch(MqttException me) {
				//TODO handle exception here
				me.printStackTrace();
			}	
		}
	}


	@Override
	protected void registerTopic(EVMCTopic topic) {
		if(this.clientIsConnected()) {
			try {
				this.mqttclient.subscribe("a38749324753845839124a/todo", 2); // TODO take real topic and QoS
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
	protected void unregisterTopic(EVMCTopic topic) {
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
		VMCMessage message = this.vmcFactory.createVMCMessage();
		message.from = this.mqttclient.getClientId();
		message.to = "N/A";
		message.topic = "a38749324753845839124a/todo";
		message.content = new String(arg1.getPayload());  
		this.messageArrived(message);
	}
}
