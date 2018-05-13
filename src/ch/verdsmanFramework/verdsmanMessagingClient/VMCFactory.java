package ch.verdsmanFramework.verdsmanMessagingClient;

public class VMCFactory {

	public VMCMessage createVMCMessage() {
		return new VMCMessage();
	}
	
	public VMCStringMessage createVMCStringMessage() {
		return new VMCStringMessage();
	}
	
	public VMCIntegerMessage createVMCIntegerMessage() {
		return new VMCIntegerMessage();
	}
	
	public VMCDoubleMessage createVMCDoubleMessage() {
		return new VMCDoubleMessage();
	}
	
	public VMCMessageJSONParser createVMCMessageJSONParser() {
		return new VMCMessageJSONParser(this);
	}
}
