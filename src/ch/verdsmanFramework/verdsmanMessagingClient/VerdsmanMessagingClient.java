package ch.verdsmanFramework.verdsmanMessagingClient;

public abstract class VerdsmanMessagingClient {
	private IVMCMessageReceiver messageReceiver;
	
	public VerdsmanMessagingClient(IVMCMessageReceiver receiver){
		this.messageReceiver = receiver;
	}
	
	protected abstract void postMessage(EVMCTopic topic, VMCMessage message);
	
	protected abstract void registerTopic(EVMCTopic topic);
	
	protected abstract void unregisterTopic(EVMCTopic topic);
	
	protected void messageArrived(VMCMessage message) {
		messageReceiver.messageArrived(message);
	}
}
