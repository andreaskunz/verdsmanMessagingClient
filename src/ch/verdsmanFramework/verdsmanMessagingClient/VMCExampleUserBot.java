package ch.verdsmanFramework.verdsmanMessagingClient;

import java.util.Date;

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
	public void messageArrived(VMCMessage message) {
		System.out.println(this.parser.messageToPrintableString(message));
	}
	
	public void startBot() {
		Runnable botTask = () -> {
			while(true) {
				VMCStringMessage message = this.vmcFactory.createVMCStringMessage();
				message.content = this.name  + " : " + new Date().toString();
				message.from = "a Bot";
				message.topic = "vmcFunTopic";
				message.to = "all interested parties";
				message.contenttype = "STRING";
				this.messagingClient.postMessage(message);
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
