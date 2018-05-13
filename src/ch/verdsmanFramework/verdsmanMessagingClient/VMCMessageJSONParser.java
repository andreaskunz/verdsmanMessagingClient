package ch.verdsmanFramework.verdsmanMessagingClient;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class VMCMessageJSONParser {
	private VMCFactory factory;
		
	public VMCMessageJSONParser(VMCFactory factory) {
		this.factory = factory;
	}
	
	public String messageToJsonString(VMCMessage message) {
		JsonObject jsonObject = Json.object(); //TODO improve to ensure testability.
		jsonObject.add("from", message.from);
		jsonObject.add("to", message.to);
		jsonObject.add("topic", message.topic);
		
		
		// supporting primitive types only for now.
		
		//continue HERE
		
		return null;
	}
	
	public VMCMessage jsonStringToMessage(String jsonString) {
		JsonValue value = Json.parse(jsonString); //TODO improve to ensure testability.
		VMCMessage message;
		if(value.isObject()) {
			message = buildVMCMessageObject(value.asObject());
		} else message = null;
		
		
		if (message == null) { //TODO throw appropriate exception.
			System.err.println("JSON string invalid. @VMCMessageJSONParser::jsonStringToMessage()");
		}
			return message;	
	}
	
	public String messageToPrintableString(VMCMessage message) {
		return "---------------- Message ------------------------------------------------\n" +
			   "From: " + message.from + "\n" +
			   "To: " + message.to + "\n" +
			   "Topic: " + message.topic + "\n" +
			   "Content: " + message.content + "\n";
	}
	
	
	public VMCMessage buildVMCMessageObject(JsonObject obj) {
		if(obj.contains("from") && 
				obj.contains("to") && 
				obj.contains("topic") &&
				obj.contains("contenttype") &&
				obj.contains("content")) {
			VMCMessage message = factory.createVMCMessage();
			
			message.from = obj.get("from").isString() ? obj.get("from").asString() : "N/A";
			message.to = obj.get("to").isString() ? obj.get("to").asString() : "N/A";
			message.topic = obj.get("topic").isString() ? obj.get("topic").asString() : "N/A";
			message.contenttype = obj.get("contenttype").isString() ? obj.get("contenttype").asString() : "DEFAULT";
			
			EVMCMessageContentType mContentType;
			
			try {
				mContentType = EVMCMessageContentType.valueOf(message.contenttype);
			} catch(IllegalArgumentException iae) {
				System.err.println("message content type is illegal! Using DEFAULT therefore. @VMCMessageJSONParser::buildVMCMessageObject(");
				mContentType = EVMCMessageContentType.DEFAULT;
			}
			
			switch (mContentType) {
				case STRING:
					message.content = obj.get("content").isString() ? obj.get("content").asString() : null;
					break;
					
				case INTEGER:
					message.content = obj.get("content").isNumber() ? obj.get("content").asInt() : null;
					break;
					
				case DOUBLE:
					message.content = obj.get("content").isNumber() ? obj.get("content").asDouble() : null;
					break;
					
				case VERDSMANCONFIG:
					message.content = obj.get("content").isObject() ? obj.get("content").asObject() : null;
					break;
					
				case DEFAULT:
					message.content = "Content couldn't be evaluated due to unknown contenttype.";
					break;
			}
			
			if(message.content != null) {
				return message;
			}
			
		}
		return null;	 	
	}
	
}
