package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;

public interface IUMCMessageParser {
	 String parseObject(UMCMessage message);
	 UMCMessage parseString(String messageContent);	
}
