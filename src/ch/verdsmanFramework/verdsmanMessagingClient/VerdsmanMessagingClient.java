package ch.verdsmanFramework.verdsmanMessagingClient;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageEnvelope;

public abstract class VerdsmanMessagingClient {
	private IVMCMessageReceiver messageReceiver;
	
	public VerdsmanMessagingClient(IVMCMessageReceiver receiver){
		this.messageReceiver = receiver;
	}
	
	//TODO use generics for this method to make it generic for VMCMessage subtypes.
	public abstract void postMessage(UMCMessageEnvelope message);
	
	public abstract void registerTopic(String topic);
	
	protected abstract void unregisterTopic(String topic);
	
	protected void messageArrived(UMCMessageEnvelope message) {
		messageReceiver.messageArrived(message);
	}
}
