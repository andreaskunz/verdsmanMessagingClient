package ch.verdsmanFramework.verdsmanMessagingClient;

public class VMCMessage {
	public String from;
	public String to;
	public String topic;
	public String contenttype;
	public Object content; // TODO improve typing with subtype polymorphism. Improve VMCMessageJSONParser methods.
	
	
	@Override
	public String toString() {
		return "---------------- Message ------------------------------------------------\n" +
			   "From: " + from + "\n" +
			   "To: " + to + "\n" +
			   "Topic: " + topic + "\n" +
			   "Content: " + content + "\n";
	}
}
