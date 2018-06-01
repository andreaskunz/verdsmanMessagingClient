package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

public class UMCMessageParserFactory {
	
	public IUMCMessageParser createMessageParser(String parserName) throws Exception {
		switch (parserName) {
		case "UMCCommandMessageParser":
			return new UMCCommandMessageParser();

		default:
			throw new Exception("Parser with name " + parserName + "does not exist @UMCMessageParserFactory::createMessageParser()");

		}
	}
}
