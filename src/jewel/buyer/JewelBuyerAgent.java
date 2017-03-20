package jewel.buyer;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import java.io.PrintStream;

import java.util.Vector;
import java.util.Date;


/**
 * 
 * @author gousos
 * This is the classs of the Agent Bueyer
 */

public class JewelBuyerAgent extends Agent {
    
    // The list of known seller agents
    private Vector sellerAgents = new Vector();

    // The GUI to interact with the user
    private JewelBuyerGuiInterface myGui;

    // Agent initializations
    protected void setup() {
        // Printout a welcome message
        System.out.println("Buyer-agent " + getAID().getName() + " is ready.");

        // Get names of seller agents as arguments
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; ++i) {
                AID seller = new AID((String) args[i], AID.ISLOCALNAME);
                sellerAgents.addElement(seller);
            }
        }

        // Show the GUI to interact with the user
        myGui = new JewelBuyerGui();
        myGui.setAgent(this);
        myGui.show();

        // Update the list of seller agents every 20 seconds
        addBehaviour(new TickerBehaviour(this, 20000) {
            protected void onTick() {

                // Update the list of seller agents
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("jewellery-trade");
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    sellerAgents.clear();
                    for (int i = 0; i < result.length; ++i) {
                        sellerAgents.addElement(result[i].getName());
                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
            }
        });
    }

    // Agent clean-up
    protected void takeDown() {
        
        // Dispose the GUI if it is there
        if (myGui != null) {
            myGui.dispose();
        }

        // Printout a dismissal message
        System.out.println("Buyer-agent " + getAID().getName() + "terminated.");
    }

    /**
     * This method is called by the GUI when the user inserts a new jewellery to
     * buy
     * @param title The title of the jewellery to buy
     * @param maxPrice The maximum acceptable price to buy the jewellery
     * @param deadline The deadline by which to buy the jewellery
  *
     */
    public void purchase(String title, int maxPrice, Date deadline) {

        addBehaviour(new PurchaseManager(this, title, maxPrice, deadline));
    }

    private class PurchaseManager extends TickerBehaviour {

        private String title;
        private int maxPrice;
        private long deadline;
           
        private PurchaseManager(Agent a, String title, int maxPrice, Date date) {
            super(a, 20000);
            this.title = title;
            this.maxPrice = maxPrice;
            this.deadline = date.getTime();
        }

        public void onTick() {
            long currentTime = System.currentTimeMillis();
            if (currentTime > deadline) {
                // Deadline expired
                myGui.notifyUser("Cannot buy the jewellery " + title);
                stop();
            } else {
                
                //Set acceptable price and start a negotiation
             int acceptablePrice = maxPrice;
                myAgent.addBehaviour(new Negotiator(title, acceptablePrice, this));
            }
        }
    }

    private class Negotiator extends Behaviour {

        private String title;
        private int maxPrice;
        private PurchaseManager manager;
        private AID bestSeller; // The seller agent who provides the best offer
        private int bestPrice; // The best offered price
        private int replies = 0; // The counter of replies from seller agents
        private MessageTemplate mt; // The template to receive replies
        private int step = 0;

        public Negotiator(String title, int price, PurchaseManager manager) {
           super(null);
           this.title = title;
           this.maxPrice = price;
           this.manager = manager;
        }

        public void action() {
            switch (step) {
                case 0:
                    
                    // Send the cfp to all sellers
                    
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < sellerAgents.size(); i++) {
                        cfp.addReceiver((AID) sellerAgents.elementAt(i));
                    }
                    cfp.setContent(title);
                    cfp.setConversationId("jewellery-trade");
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);
                    myGui.notifyUser("Sent Call for Proposal to all Sellers");

                    // Prepare the template to get proposals
                    
                    mt = MessageTemplate.and(
                            MessageTemplate.MatchConversationId("jewellery-trade"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;

                case 1:
                    // Receive all proposals/refusals from seller agents
                    ACLMessage reply = myAgent.receive(mt);

                    if (reply != null) {
                        // Reply received
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            // This is an offer
                            int price = Integer.parseInt(reply.getContent());
                            myGui.notifyUser("Received Proposal at " + price +" from seller agent "+reply.getSender().getName()+
                                    " when the maximum acceptable price of buyer is " + maxPrice);

                            if (bestSeller == null || price <= bestPrice) {
                                // This is the best offer at present
                                bestPrice = price;
                                bestSeller = reply.getSender();
                            }
                        }
                        replies++;//replies counter

                        if (replies >= sellerAgents.size()) {
                            System.out.println("All replies received");
                            // We received all replies
                            step = 2;
                        }
                    } else {
                        block();
                    }
                    break;

                case 2:
                    if (bestSeller != null && bestPrice <= maxPrice) {
                        // Send the purchase order to the seller that provided the best offer

                        ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                        order.addReceiver(bestSeller);
                        order.setContent(title);
                        order.setConversationId("jewellery-trade");
                        order.setReplyWith("order" + System.currentTimeMillis());
                        myAgent.send(order);
                        myGui.notifyUser("sent Accept Proposal");
                        // Prepare the template to get the purchase order reply
                        mt = MessageTemplate.and(
                                MessageTemplate.MatchConversationId("jewellery-trade"),
                                MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                        step = 3;
                    } else {
                        myGui.notifyUser("No acceptable proposals received");
                        // If we received no acceptable proposals, terminate
                        //System.out.println("de irthe protasi gia acdept step = 4 kai done");
                        step = 4;
                    }
                    break;

                case 3:
                    // Receive the purchase order reply
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        // Purchase order reply received
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            // Purchase successful. We can terminate
                            myGui.notifyUser(title + " successfully purchased at Price = " + bestPrice + " from" + reply.getSender().getName());
                            manager.stop();
                        }
                        step = 4;
                    } else {
                        block();
                    }
                    break;
            } // end of switch
        }

        public boolean done() {
            return step == 4;
        }
    } // End of inner class Negotiator
}
