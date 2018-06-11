package ch.verdsmanFramework.verdsmanMessagingClient;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;

public abstract class VerdsmanMessagingClient {
	private IVMCMessageReceiver messageReceiver;
	
	public VerdsmanMessagingClient(IVMCMessageReceiver receiver){
		this.messageReceiver = receiver;
	}
	
	//TODO use generics for this method to make it generic for VMCMessage subtypes.
	public abstract void postMessage(UMCMessage message);
	
	public abstract void registerTopic(String topic);
	
	protected abstract void unregisterTopic(String topic);
	
	protected void deliverMessage(UMCMessage message) {
		messageReceiver.messageArrived(message);
	}
}
