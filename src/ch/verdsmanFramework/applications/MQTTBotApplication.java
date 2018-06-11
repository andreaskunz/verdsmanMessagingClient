package ch.verdsmanFramework.applications;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import ch.verdsmanFramework.verdsmanMessagingClient.VMCExampleUserBot;
import ch.verdsmanFramework.verdsmanMessagingClient.VMCFactory;
import ch.verdsmanFramework.verdsmanMessagingClient.VMCMqttFactory;
import ch.verdsmanFramework.verdsmanMessagingClient.VerdsmanMQTTMessagingClient;
import ch.verdsmanFramework.verdsmanMessagingClient.VerdsmanMessagingClient;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers.UMCMessageParserFactory;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers.UMCMessageParserPool;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageFactory;

public class MQTTBotApplication {

	//settings
	private final static String brokerURI = "tcp://broker.hivemq.com:1883";
	//private final static String brokerURI = "tcp://146.136.57.22:1883";
	private final static String clientID = "S.A.I.N.T._No5_aka_VMC_bot";
	private final static String botName = "main";
	
	public void run() throws MqttException {
		VMCMqttFactory mqttFactory = new VMCMqttFactory();
		VMCFactory vmcFactory = new VMCFactory();
		UMCMessageFactory messageFactory = new UMCMessageFactory();
		MqttClient mqttclient = mqttFactory.createMqttClient(brokerURI, clientID, mqttFactory.createMemoryPersistence());
		VMCExampleUserBot bot = new VMCExampleUserBot(botName, messageFactory);
		UMCMessageParserPool parserPool = new UMCMessageParserPool(new UMCMessageParserFactory(messageFactory));
		VerdsmanMessagingClient messagingClient = new VerdsmanMQTTMessagingClient(bot, mqttclient, mqttFactory, vmcFactory, parserPool);
		bot.setVerdsmanMessagingClient(messagingClient);
		bot.listenForTopic("vmcFunTopic");
		bot.startBot();
	}
	
	public static void main (String[] arguments) {
		MQTTBotApplication app = new MQTTBotApplication();
		try {
			app.run();
		} catch (MqttException e) {
			System.err.println("something really baaad happened but the global Exception catcher got it for you ;-)");
			e.printStackTrace();
		}
	}
}
