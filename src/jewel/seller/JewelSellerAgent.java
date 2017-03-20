
package jewel.seller;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;

import java.util.*;

public class JewelSellerAgent extends Agent {
  // The catalogue of books available for sale
  private Map catalogue = new HashMap();

  // The GUI to interact with the user
  private JewelSellerGuiInterface myGui;

  /**
   * Agent initializations
  **/
  protected void setup() {
    // Printout a welcome message
    System.out.println("Seller-agent "+getAID().getName()+" is ready.");

    // Create and show the GUI
    myGui = new JewelSellerGui();
    myGui.setAgent(this);
    myGui.show();
    
    // Add the behaviour serving calls for price from buyer agents
    addBehaviour(new CallForOfferServer());

    // Add the behaviour serving purchase requests from buyer agents
    addBehaviour(new PurchaseOrderServer());

    
    // Register the jewellery-selling service in the yellow pages
    DFAgentDescription dfd = new DFAgentDescription();
    dfd.setName(getAID());
    ServiceDescription sd = new ServiceDescription();
    sd.setType("jewellery-trade");
    sd.setName(getLocalName()+"-jewellery-trade");
    dfd.addServices(sd);
    try {
      DFService.register(this, dfd);
    }
    catch (FIPAException fe) {
      fe.printStackTrace();
    }
  }

  /**
   * Agent clean-up
  **/
  protected void takeDown() {
    // Dispose the GUI if it is there
    if (myGui != null) {
      myGui.dispose();
    }

    // Printout a dismissal message
    System.out.println("Seller-agent "+getAID().getName()+"terminating.");

      // Deregister from the yellow pages
    try {
      DFService.deregister(this);
    }
    catch (FIPAException fe) {
      fe.printStackTrace();
    }
  }

  public void putForSale(String title, int price, Date deadline) {
    addBehaviour(new PriceManager(this, title, price, deadline));
  }

  private class PriceManager extends TickerBehaviour {

    private String title;
    private int price;
    private long deadline;

      
    private PriceManager(Agent a, String title, int price, Date date) {
      super(a, 20000); // tick every 20 seconds
      this.title = title;
      this.price = price;
      this.deadline = date.getTime();
 
    }

    public void onStart() {
      // Insert the jewellery in the catalogue of items available for sale
      catalogue.put(title, this);
      super.onStart();
    }

    public void onTick() {
      long currentTime = System.currentTimeMillis();
      if (currentTime > deadline) {
        // Deadline expired
        myGui.notifyUser("Cannot sell jewellery "+title);
        catalogue.remove(title);
        stop();
      }

    }

   public int getCurrentPrice() {
      return price;
    }
  }

  /** 
   * Inner class CallForOfferServer.
   * This is the behaviour used by Jewellery-seller agents to serve
   * incoming call for offer from buyer agents.
   * If the jewellery is in the local catalogue, the seller agent
   * replies with a PROPOSE message specifying the price. Otherwise
   * a REFUSE message is sent back.
  **/
  private class CallForOfferServer extends CyclicBehaviour {
    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

    public void action() {
      
      ACLMessage msg = myAgent.receive(mt);
      
      if (msg != null) {
          
        // CFP Message received. Process it
        String title = msg.getContent();
        myGui.notifyUser("Received Proposal to buy "+title);
        ACLMessage reply = msg.createReply();
        PriceManager pm = (PriceManager) catalogue.get(title);
        
        if (pm != null) {
          // The requested jewellery is available for sale. Reply with the price
          reply.setPerformative(ACLMessage.PROPOSE);
          reply.setContent(String.valueOf(pm.getCurrentPrice()));
        }
        else {
          // The requested jewellery is NOT available for sale.
          reply.setPerformative(ACLMessage.REFUSE);
          myGui.notifyUser("Refused Proposal: "+title+ " is not for sale");
        }
        myAgent.send(reply);
      }
      else {
        block();
      }
    }
  } // End of inner class CallForOfferServer


	
    private class PurchaseOrderServer extends CyclicBehaviour {
    private MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);

    public void action() {
        
      ACLMessage msg = myAgent.receive(mt);
      
      if (msg != null) {
          
        // CFP Message received. Process it
        String title = msg.getContent();
        ACLMessage reply = msg.createReply();
        
        PriceManager pm = (PriceManager) catalogue.remove(title);
        
        if (pm != null) {
          // The requested jewellery is available for sale. Reply with the price
          reply.setPerformative(ACLMessage.INFORM);
          myGui.notifyUser(title+" Sold to "+msg.getSender().getName());
        
        }
        else {
          // The requested jewellery is NOT available for sale.
          reply.setPerformative(ACLMessage.FAILURE);
        }
        myAgent.send(reply);
      }
      else {
        block();
      }
    }
  } // End of inner class PurchaseOrderServer
    
}