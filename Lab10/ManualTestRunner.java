import java.util.List;

import DataModels.Order;
import DataModels.Product;
import FactoryMethodPattern.ShipmentFactory;
import ObserverPattern.EmailService;
import ObserverPattern.InventoryService;
import ObserverPattern.OrderProcessor;
import StregyPattern.DiscountStrategy;
import StregyPattern.FixedDiscount;
import StregyPattern.OrderCalculator;
import StregyPattern.PercentageDiscount;

public class ManualTestRunner {
    public static void main(String[] args) {
        System.out.println("-- E-commerce System Simulator ---");

        //---1.setup---
        Product Ipad = new Product("P001", "Ipad", 30000.0);
        Product Vivo = new Product("P002", "Vivo", 20000.0);
        Order myOrder = new Order("ORD-001", List.of(Ipad, Vivo),"customer@example.com");

        OrderCalculator calculator = new OrderCalculator();
        ShipmentFactory shipmentFactory = new ShipmentFactory();

        OrderProcessor OrderProcessor = new OrderProcessor();
        InventoryService inventory = new InventoryService();
        EmailService emailer = new EmailService();
        OrderProcessor.register(inventory);
        OrderProcessor.register(emailer);

        System.out.println("\n--- 2.Testing Strategy Pattern (Discounts) ---");
        double originalPrice = myOrder.getTotalPrice();

        DiscountStrategy tenPercentageOff = new PercentageDiscount(10);
        double priceAfterPercentage = calculator.calculateFinalPrice(myOrder, tenPercentageOff);
        System.out.println("Price with 10% discount: "+priceAfterPercentage);

        DiscountStrategy fiveHundredOff = new FixedDiscount(500);
        double priceAfterFixed = calculator.calculateFinalPrice(myOrder, fiveHundredOff);
        System.out.println("Price with 500 THB discount: "+priceAfterFixed);
    }   

}
