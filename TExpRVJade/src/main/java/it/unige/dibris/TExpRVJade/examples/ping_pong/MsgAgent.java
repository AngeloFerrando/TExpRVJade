package it.unige.dibris.TExpRVJade.examples.ping_pong;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class MsgAgent extends Agent {

  protected void setup() {

    System.out.println("Hello! Msg-agent "+getAID().getName()+" is ready.");

    Object[] args = getArguments();
    if (args != null && args.length > 0) {
      addBehaviour(new TickerBehaviour(this, 5000){
        @Override
        protected void onTick() {
          if(Boolean.parseBoolean(args[1].toString())){
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContent("ping");
            msg.addReceiver(new AID(args[0].toString(), AID.ISLOCALNAME));
            send(msg);
            System.out.println("Msg-agent " + getAID().getLocalName() + " has sent " + msg.getContent() + " to " + args[0]);
            msg = blockingReceive();
            if (msg != null){
              System.out.println("Msg-agent " + getAID().getLocalName() + " has received " + msg.getContent() + " from " + msg.getSender().getLocalName());
            }
          } else{
            ACLMessage msg = blockingReceive();
            if (msg != null && msg.getSender().getLocalName().equals(args[0].toString())){
              System.out.println("Msg-agent " + getAID().getLocalName() + " has received " + msg.getContent() + " from " + msg.getSender().getLocalName());
            }
            msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContent("pong");
            msg.addReceiver(new AID(args[0].toString(), AID.ISLOCALNAME));
            send(msg);
            System.out.println("Msg-agent " + getAID().getLocalName() + " has sent " + msg.getContent() + " to " + args[0]);

          }
        }
      });
    }
    else {
      // Make the agent terminate immediately
      System.out.println("No args specified");
      doDelete();
    }
  }
  // Put agent clean-up operations here
  protected void takeDown() {
      // Printout a dismissal message
      System.out.println("Msg-agent "+getAID().getName()+" terminating.");
  }
}
