package it.unige.dibris.TExpRVJade.examples.web_site;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

@SuppressWarnings("serial")
public class ServerAgent extends Agent {
	@Override
	protected void setup() {
		super.setup();
		//String[] args = (String[]) getArguments();
		//String sender = args[0];
		//String content = args[1];

		addBehaviour(new CyclicBehaviour(this) {

			@Override
			public void action() {
				String sender = "client";
				String content_confirm = "return_page";
				String content_404 = "404";

				ACLMessage msgR= blockingReceive();
				if(msgR != null)
					System.out.println("[" + getLocalName() + "]: receive " + msgR.getContent() + " from " + sender);


				doWait(5000);

				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID(sender, AID.ISLOCALNAME));
				Random r = new Random();
				if(r.nextDouble() > 0.5){
						msg.setContent(content_confirm);
						System.out.println("[" + getLocalName() + "]: send " + content_confirm + " to " + sender);
				} else{
					msg.setContent(content_404);
					System.out.println("[" + getLocalName() + "]: send " + content_404 + " to " + sender);
				}

				
				send(msg);
			}
		});
	}
}
