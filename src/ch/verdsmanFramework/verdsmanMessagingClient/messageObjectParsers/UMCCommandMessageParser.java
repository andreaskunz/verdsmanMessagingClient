package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCCommandMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;

public class UMCCommandMessageParser implements IUMCMessageParser {

	@Override
	public String parseObject(UMCMessage message) {
		if(!(message instanceof UMCCommandMessage)) {
			return "PARSING ERROR: WRONG PARSER SELECTED";
		}
		return null;
	}

	@Override
	public UMCMessage parseString(String messageContent) {
		// TODO Auto-generated method stub
		return null;
	}
}
