package jewel.buyer;

public interface JewelBuyerGuiInterface {
  void setAgent(JewelBuyerAgent a);
  void show();
  void hide();
  void notifyUser(String message);
  void dispose();
}