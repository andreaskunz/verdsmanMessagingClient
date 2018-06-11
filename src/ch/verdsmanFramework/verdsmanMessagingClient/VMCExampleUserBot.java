package ch.verdsmanFramework.verdsmanMessagingClient;

import java.util.Date;

import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCCommandMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessage;
import ch.verdsmanFramework.verdsmanMessagingClient.messageObjects.UMCMessageFactory;

public class VMCExampleUserBot implements IVMCMessageReceiver {
	private String name;
	private VerdsmanMessagingClient messagingClient = null;
	private UMCMessageFactory messageFactory;
	
	public VMCExampleUserBot(String name, UMCMessageFactory messageFactory) {
		this.name = name;
		this.messageFactory = messageFactory;
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
	public void messageArrived(UMCMessage message) {
		System.out.println(((UMCCommandMessage) message).stringParams[0]);
	}
	
	public void startBot() {
		Runnable botTask = () -> {
			while(true) {
				UMCCommandMessage commandMessage = this.messageFactory.createUMCCommandMessage("bot", "all", "vmcFunTopic","UMCCommandMessageParser");
				commandMessage.command = "ping";
				commandMessage.stringParams= new String[] {this.name  + " : " + new Date().toString()};
				this.messagingClient.postMessage(commandMessage);
				System.out.println("Bot: message posted");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		};
			new Thread(botTask).start();
	}
}
