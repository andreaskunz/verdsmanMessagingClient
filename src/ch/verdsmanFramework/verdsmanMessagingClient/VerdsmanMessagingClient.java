package ch.verdsmanFramework.verdsmanMessagingClient;

public abstract class VerdsmanMessagingClient {
	private IVMCMessageReceiver messageReceiver;
	
	public VerdsmanMessagingClient(IVMCMessageReceiver receiver){
		this.messageReceiver = receiver;
	}
	
	protected abstract void postMessage(VMCTopic topic, VMCMessage message);
	
	protected abstract void registerTopic(VMCTopic topic);
	
	protected abstract void unregisterTopic(VMCTopic topic);
	
	protected void messageArrived(VMCMessage message) {
		messageReceiver.messageArrived(message);
	}
}
