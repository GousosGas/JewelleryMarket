package jewel.seller;

public interface JewelSellerGuiInterface {
  void setAgent(JewelSellerAgent a);
  void show();
  void hide();
  void notifyUser(String message);
  void dispose();
}