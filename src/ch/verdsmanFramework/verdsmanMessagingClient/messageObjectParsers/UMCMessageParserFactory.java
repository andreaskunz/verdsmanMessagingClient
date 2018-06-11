package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageFactory;

public class UMCMessageParserFactory {
	private UMCMessageFactory messageFactory;
	
	public UMCMessageParserFactory(UMCMessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}
	
	public IUMCMessageParser createMessageParser(String parserName) throws Exception {
		switch (parserName) {
		case "UMCCommandMessageParser":
			return new UMCCommandMessageParser(this.messageFactory);

		default:
			throw new Exception("Parser with name " + parserName + "does not exist @UMCMessageParserFactory::createMessageParser()");

		}
	}
}
