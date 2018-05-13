package ch.verdsmanFramework.verdsmanMessagingClient;

import java.util.Date;

public class VMCExampleUserBot implements IVMCMessageReceiver {
	private String name;
	private VerdsmanMessagingClient messagingClient = null;
	private VMCFactory vmcFactory;
	
	public VMCExampleUserBot(String name, VMCFactory vmcFactory) {
		this.name = name;
		this.vmcFactory = vmcFactory;
	}
	
	public void setVerdsmanMessagingClient(VerdsmanMessagingClient messagingClient) {
		this.messagingClient = messagingClient;
	}
	
	public void listenForTopic(EVMCTopic topic) {
		if(messagingClient != null) {
			this.messagingClient.registerTopic(topic);
		} else {
			System.err.println("VerdsmanMessagingClient not set! @VMCExampleUserBot::listenForTopic()");
		}
	}
	
	@Override
	public void messageArrived(VMCMessage message) {
		System.out.println(message.toString());
	}
	
	public void startBot() {
		Runnable botTask = () -> {
			while(true) {
				VMCMessage message = this.vmcFactory.createVMCMessage();
				message.content = this.name  + " : " + new Date().toString();
				message.from = "Bot";
				this.messagingClient.postMessage(EVMCTopic.ALL, message);
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
