package ch.verdsmanFramework.verdsmanMessagingClient;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;

public interface IVMCMessageReceiver {
	void messageArrived(UMCMessage message);
}
