package ch.verdsmanFramework.verdsmanMessagingClient;

public abstract class VerdsmanMessagingClient {
	private IVMCMessageReceiver messageReceiver;
	
	public VerdsmanMessagingClient(IVMCMessageReceiver receiver){
		this.messageReceiver = receiver;
	}
	
	//TODO use generics for this method to make it generic for VMCMessage subtypes.
	protected abstract void postMessage(VMCMessage message);
	
	protected abstract void registerTopic(String topic);
	
	protected abstract void unregisterTopic(String topic);
	
	protected void messageArrived(VMCMessage message) {
		messageReceiver.messageArrived(message);
	}
}
