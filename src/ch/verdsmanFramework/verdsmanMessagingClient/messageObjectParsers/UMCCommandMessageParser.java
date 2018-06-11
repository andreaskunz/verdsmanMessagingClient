package ch.verdsmanFramework.verdsmanMessagingClient.messageObjectParsers;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCCommandMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageFactory;

public class UMCCommandMessageParser implements IUMCMessageParser {
	
	private UMCMessageFactory messageFactory;
	
	public UMCCommandMessageParser(UMCMessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}
	
	@Override
	public String parseObject(UMCMessage message) throws Exception {
		UMCCommandMessage m;
		
		if(message instanceof UMCCommandMessage) {
			m = (UMCCommandMessage) message;
		} else {
			throw new Exception("PARSING ERROR: WRONG PARSER SELECTED");
		}
		
		JsonObject jsonObject = Json.object(); //TODO does this support testability?
		jsonObject.add("from", m.from);
		jsonObject.add("to", m.to);
		jsonObject.add("topic", m.topic);
		jsonObject.add("parsertype", m.parsertype);
		jsonObject.add("command", m.command);
		
		jsonObject.add("intParams", Json.array(m.intParams != null ? m.intParams : new int[0]));
		jsonObject.add("doubleParams", Json.array(m.doubleParams != null ? m.doubleParams : new double[0]));
		jsonObject.add("stringParams", Json.array(m.stringParams != null ? m.stringParams : new String[0]));
		
		return jsonObject.toString();
	}

	@Override
	public UMCMessage parseString(String messageContent) throws Exception {
		JsonValue value = Json.parse(messageContent); //TODO does this support testability?
		if(!value.isObject()) {
			throw new Exception("PARSING ERROR: parser did not return a JSON object.");
		}
		JsonObject mainObject = value.asObject();
		
		if(mainObject.contains("from") && //check if fields exist before access.
				mainObject.contains("to") && 
				mainObject.contains("topic") &&
				mainObject.contains("parsertype") &&
				mainObject.contains("command") &&
				mainObject.contains("intParams") &&
				mainObject.contains("doubleParams") &&
				mainObject.contains("stringParams")
				) {
			
			String from = mainObject.get("from").isString() ? mainObject.get("from").asString() : "N/A";
			String to = mainObject.get("to").isString() ? mainObject.get("to").asString() : "N/A";
			String topic = mainObject.get("topic").isString() ? mainObject.get("topic").asString() : "N/A";
			String parsertype = mainObject.get("parsertype").isString() ? mainObject.get("parsertype").asString() : "N/A";
			String command = mainObject.get("command").isString() ? mainObject.get("command").asString() : "N/A";

			UMCCommandMessage commandMessage = this.messageFactory.createUMCCommandMessage(from, to, topic, parsertype);
			commandMessage.command = command;
			
			JsonArray intArray = mainObject.get("intParams").isArray() ? mainObject.get("intParams").asArray() : null;
			
			if(intArray != null) {
				commandMessage.intParams = new int[intArray.size()];
				int index = 0;
				for (JsonValue intValue : intArray) {
					commandMessage.intParams[index] = intValue.asInt(); //will throw exception if not int!
					index++;
				} 
			}
			
			JsonArray doubleArray = mainObject.get("doubleParams").isArray() ? mainObject.get("doubleParams").asArray() : null;
			
			if(doubleArray != null) {
				commandMessage.doubleParams = new double[doubleArray.size()];
				 int index = 0;
				for (JsonValue doubleValue : doubleArray) {
					commandMessage.doubleParams[index] = doubleValue.asDouble(); //will throw exception if not double!
					index++;
				} 
			}
			
			JsonArray stringArray = mainObject.get("stringParams").isArray() ? mainObject.get("stringParams").asArray() : null;
			
			if(stringArray != null) {
				commandMessage.stringParams = new String[stringArray.size()];
				 int index = 0;
				for (JsonValue stringValue : stringArray) {
					commandMessage.stringParams[index] = stringValue.asString(); //will throw exception if not String!
					index++;
				} 
			}
			return commandMessage;
						
		} else throw new Exception("PARSING ERROR: parsed object is not up to the specification.");
	}
}
