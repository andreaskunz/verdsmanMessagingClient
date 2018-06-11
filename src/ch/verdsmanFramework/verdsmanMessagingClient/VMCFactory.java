package ch.verdsmanFramework.verdsmanMessagingClient;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCDoubleMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCIntegerMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageEnvelope;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCStringMessage;

public class VMCFactory {

	public UMCMessageEnvelope createVMCMessage() {
		return new UMCMessageEnvelope();
	}
	
	public UMCStringMessage createUMCStringMessage() {
		return new UMCStringMessage();
	}
	
	public UMCStringMessage createUMCStringMessage(String from, String to, String topic) {
		UMCStringMessage stringMessage = new UMCStringMessage();
		stringMessage.from = from;
		stringMessage.to = to;
		stringMessage.topic = topic;
		return stringMessage;
	}
	
	public UMCIntegerMessage createVMCIntegerMessage() {
		return new UMCIntegerMessage();
	}
	
	public UMCDoubleMessage createVMCDoubleMessage() {
		return new UMCDoubleMessage();
	}
}
