package DecoratorPattern;

import DataModels.*;
import FactoryMethodPattern.*;
/*/
บริการประกันสินค้า
*/
public class InsuranceDecorator extends ShipmentDecorator{
     private final Order order;

     public InsuranceDecorator(Shipment wrappedShipment, Order order) {
        super(wrappedShipment);
        this.order = order;
        
    }

     @Override
    public String getInfo() {
        return super.getInfo() + " + Insurance";
    }

    @Override
    public double getCost() {
         return super.getCost() + (order.getTotalPrice() * 0.10);
    }
    
}
