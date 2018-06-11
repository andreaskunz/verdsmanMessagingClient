package ch.verdsmanFramework.verdsmanMessagingClient.messageObjects;

public class UMCMessageFactory {
	
	public UMCCommandMessage createUMCCommandMessage(String from, String to, String topic, String parsertype) {
		UMCCommandMessage commandMessage = new UMCCommandMessage();
		
		commandMessage.from = from;
		commandMessage.to = to;
		commandMessage.topic = topic;
		commandMessage.parsertype = parsertype;

		return commandMessage;
	}
}
