package ch.verdsmanFramework.verdsmanMessagingClient;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCDoubleMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCIntegerMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageEnvelope;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCStringMessage;

public class VMCMessageJSONParser {
	private VMCFactory factory;
		
	public VMCMessageJSONParser(VMCFactory factory) {
		this.factory = factory;
	}
	
	public String messageToJsonString(UMCStringMessage message) { 
		JsonObject jsonObject = this.createGenericJsonObject(message);
		jsonObject.add("content", message.content);
		return jsonObject.toString();
	}
	
	public String messageToJsonString(UMCIntegerMessage message) {
		JsonObject jsonObject = this.createGenericJsonObject(message);
		jsonObject.add("content", message.content);
		return jsonObject.toString();
	}
	
	public String messageToJsonString(UMCDoubleMessage message) {
		JsonObject jsonObject = this.createGenericJsonObject(message);
		jsonObject.add("content", message.content);
		return jsonObject.toString();
	}
	
	public UMCMessageEnvelope jsonStringToMessage(String jsonString) {
		JsonValue value = Json.parse(jsonString); //TODO improve to ensure testability.
		UMCMessageEnvelope message;
		if(value.isObject()) {
			message = buildVMCMessageObject(value.asObject());
		} else message = null;
		
		
		if (message == null) { //TODO throw appropriate exception.
			System.err.println("JSON string invalid. @VMCMessageJSONParser::jsonStringToMessage()");
		}
			return message;	
	}
	
	public String messageToPrintableString(UMCMessageEnvelope message) {
		return "---------------- Message ------------------------------------------------\n" +
			   "From: " + message.from + "\n" +
			   "To: " + message.to + "\n" +
			   "Topic: " + message.topic + "\n" +
			   "Content: " + message.content + "\n";
	}
	
	
	public UMCMessageEnvelope buildVMCMessageObject(JsonObject obj) {
		if(obj.contains("from") && 
				obj.contains("to") && 
				obj.contains("topic") &&
				obj.contains("contenttype") &&
				obj.contains("content")) {
		
			EVMCMessageContentType mContentType;
			
			String contenttype = obj.get("contenttype").isString() ? obj.get("contenttype").asString() : "DEFAULT";
			try {
				mContentType = EVMCMessageContentType.valueOf(contenttype);
			} catch(IllegalArgumentException iae) {
				System.err.println("message content type is illegal! Using DEFAULT therefore. @VMCMessageJSONParser::buildVMCMessageObject(");
				mContentType = EVMCMessageContentType.DEFAULT;
			}
			
			
			UMCMessageEnvelope message = null;
			
			switch (mContentType) {
				case STRING:
					message = factory.createUMCStringMessage();
					message.content = obj.get("content").isString() ? obj.get("content").asString() : null;
					break;
					
				case INTEGER:
					message = factory.createVMCIntegerMessage();
					message.content = obj.get("content").isNumber() ? obj.get("content").asInt() : null;
					break;
					
				case DOUBLE:
					message = factory.createVMCDoubleMessage();
					message.content = obj.get("content").isNumber() ? obj.get("content").asDouble() : null;
					break;
					
				case DEFAULT:
					message = factory.createUMCStringMessage();
					message.content = "Content couldn't be evaluated due to unknown contenttype.";
					break;
			}
			
		
			// fill the the generic fields.
			message.from = obj.get("from").isString() ? obj.get("from").asString() : "N/A";
			message.to = obj.get("to").isString() ? obj.get("to").asString() : "N/A";
			message.topic = obj.get("topic").isString() ? obj.get("topic").asString() : "N/A";
			message.contenttype = contenttype;
			
			
			if(message.content != null) {
				return message;
			}
			
		}
		return null;	 	
	}
	
	private JsonObject createGenericJsonObject(UMCMessageEnvelope message) {
		JsonObject jsonObject = Json.object(); //TODO improve to ensure testability.
		jsonObject.add("from", message.from);
		jsonObject.add("to", message.to);
		jsonObject.add("topic", message.topic);
		jsonObject.add("contenttype", message.contenttype);
		return jsonObject;
	}
}
