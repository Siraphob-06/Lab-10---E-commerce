package ObserverPattern;

import DataModels.Order;

public class InventoryService implements OrderObserver{
    public void update(Order order){
        System.out.println("[Inventory Service] Stock has been update for order "
        + order.customerEmail() + "for order: " + order.orederId());
    }
}
