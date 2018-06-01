package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

import java.util.HashMap;

public class UMCMessageParserPool {
	
	private HashMap<String, IUMCMessageParser> parserPool;
	private UMCMessageParserFactory messageParserFactory;
	
	
	public UMCMessageParserPool(UMCMessageParserFactory factory) {
		this.messageParserFactory = factory;
	}
	
	
	public IUMCMessageParser getParser(String parsertype) throws Exception {
		if (parserPool.containsKey(parsertype)) {
			return parserPool.get(parsertype);
		}
		
		IUMCMessageParser newParser = this.messageParserFactory.createMessageParser(parsertype);
		this.parserPool.put(parsertype, newParser);
		return newParser;
	}
}
