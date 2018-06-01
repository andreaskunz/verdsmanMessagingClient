package ch.verdsmanFramework.verdsmanMessagingClient;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageEnvelope;

public interface IVMCMessageReceiver {
	void messageArrived(UMCMessageEnvelope message);
}
