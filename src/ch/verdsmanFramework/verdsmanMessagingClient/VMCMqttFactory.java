package ch.verdsmanFramework.verdsmanMessagingClient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class VMCMqttFactory {

	
	public MqttMessage createMqttMessage() {
		return new MqttMessage();
	}
	
	public MqttClient createMqttClient(String brokerURI, String clientID, MemoryPersistence memoryPersistence) throws MqttException {
		return new MqttClient(brokerURI, clientID,memoryPersistence);
	}
	
	public MemoryPersistence createMemoryPersistence() {
		return new MemoryPersistence();
	}

}
