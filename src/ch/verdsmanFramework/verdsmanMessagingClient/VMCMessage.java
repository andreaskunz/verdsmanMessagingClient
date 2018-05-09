package ch.verdsmanFramework.verdsmanMessagingClient;

public class VMCMessage {
	public String from;
	public String to;
	public String topic;
	public String content;
	
	@Override
	public String toString() {
		return "---------------- Message ------------------------------------------------\n" +
			   "From: " + from + "\n" +
			   "To: " + to + "\n" +
			   "Topic: " + topic + "\n" +
			   "Content: " + content + "\n";
	}
}
