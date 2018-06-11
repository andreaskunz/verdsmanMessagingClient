package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;

public interface IUMCMessageParser { //TODO check if abstract class would make sense to remove duplicated code.
	 String parseObject(UMCMessage message) throws Exception;
	 UMCMessage parseString(String messageContent) throws Exception;	
}
