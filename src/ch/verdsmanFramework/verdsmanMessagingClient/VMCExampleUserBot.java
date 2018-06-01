package ch.verdsmanFramework.verdsmanMessagingClient;

import java.util.Date;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageEnvelope;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCStringMessage;

public class VMCExampleUserBot implements IVMCMessageReceiver {
	private String name;
	private VerdsmanMessagingClient messagingClient = null;
	private VMCFactory vmcFactory;
	private VMCMessageJSONParser parser;
	
	public VMCExampleUserBot(String name, VMCFactory vmcFactory) {
		this.name = name;
		this.vmcFactory = vmcFactory;
		this.parser = this.vmcFactory.createVMCMessageJSONParser();
	}
	
	public void setVerdsmanMessagingClient(VerdsmanMessagingClient messagingClient) {
		this.messagingClient = messagingClient;
	}
	
	public void listenForTopic(String topic) {
		if(messagingClient != null) {
			this.messagingClient.registerTopic(topic);
		} else {
			System.err.println("VerdsmanMessagingClient not set! @VMCExampleUserBot::listenForTopic()");
		}
	}
	
	@Override
	public void messageArrived(UMCMessageEnvelope message) {
		System.out.println(this.parser.messageToPrintableString(message));
	}
	
	public void startBot() {
		Runnable botTask = () -> {
			while(true) {
				UMCStringMessage message = this.vmcFactory.createUMCStringMessage();
				UMCMessageEnvelope envelope = this.vmcFactory.createUMCMessageEnvelope("a Bot", "all interested parties", "vmcFunTopic");
				message.value = this.name  + " : " + new Date().toString();
				envelope.message = message;
				envelope.parsertype = "UMCStringMessage";
				this.messagingClient.postMessage(envelope);
				System.out.println("Bot: message posted");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		};
			new Thread(botTask).start();
	}
}
