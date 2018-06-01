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
	
	public UMCMessageEnvelope createUMCMessageEnvelope(String from, String to, String topic) {
		UMCMessageEnvelope env = new UMCMessageEnvelope();
		env.from = from;
		env.to = to;
		env.topic = topic;
		return env;
	}
	
	public UMCIntegerMessage createVMCIntegerMessage() {
		return new UMCIntegerMessage();
	}
	
	public UMCDoubleMessage createVMCDoubleMessage() {
		return new UMCDoubleMessage();
	}
	
	public VMCMessageJSONParser createVMCMessageJSONParser() {
		return new VMCMessageJSONParser(this);
	}
}
