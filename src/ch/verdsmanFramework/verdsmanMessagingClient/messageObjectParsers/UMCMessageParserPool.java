package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

import java.util.HashMap;

public class UMCMessageParserPool {
	
	private HashMap<String,IUMCMessageParser> parserPool = new HashMap<String,IUMCMessageParser>();
	private UMCMessageParserFactory messageParserFactory;
	
	
	public UMCMessageParserPool(UMCMessageParserFactory factory) {
		this.messageParserFactory = factory;
	}
	
	
	public IUMCMessageParser getParser(String parsertype) throws Exception {
		if (parserPool.containsKey(parsertype)) {
			System.out.println("found parser in pool.");
			return parserPool.get(parsertype);
		}
		System.out.println("try to create new parser: " + parsertype);
		IUMCMessageParser newParser = this.messageParserFactory.createMessageParser(parsertype);
		System.out.println("had to create new parser.");
		this.parserPool.put(parsertype, newParser);
		return newParser;
	}
}
