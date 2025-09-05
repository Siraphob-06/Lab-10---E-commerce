import java.util.List;

import DataModels.Order;
import DataModels.Product;
import DecoratorPattern.GiftWrapDecorator;
import DecoratorPattern.InsuranceDecorator;
import FactoryMethodPattern.Shipment;
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
        System.out.println("Original Price: "+originalPrice);

        DiscountStrategy tenPercentageOff = new PercentageDiscount(10);
        double priceAfterPercentage = calculator.calculateFinalPrice(myOrder, tenPercentageOff);
        System.out.println("Price with 10% discount: "+priceAfterPercentage);

        DiscountStrategy fiveHundredOff = new FixedDiscount(500);
        double priceAfterFixed = calculator.calculateFinalPrice(myOrder, fiveHundredOff);
        System.out.println("Price with 500 THB discount: "+priceAfterFixed);

        System.out.println("\n--- 3, Testing Factory and Decorator Pattern (Snipment) ---");
        //สร้างการจัดส่งแบบมาตรฐาน
        Shipment standardShipment = shipmentFactory.createShipment("STANDARD");
        System.out.println("Base Shipment: "+ standardShipment.getInfo()+", Cost: "+ standardShipment.getCost());
        
        // "ห่อ" ด้วยบริการห่อของขวัญ
        Shipment giftWrapped = new GiftWrapDecorator(standardShipment);
        System.out.println("Decorated: " + giftWrapped.getInfo() + ", Cost: "+giftWrapped.getCost());


        // "ห่อ" ทับด้วยบริการประกันสินค้า
        Shipment fullyLoaded = new InsuranceDecorator(giftWrapped, myOrder);
        System.out.println("Fully Decorated: " + fullyLoaded.getInfo() + ", Cost: "+ fullyLoaded.getCost());

        System.out.println("\n--- 4.Printing Final Summary ---");
        double finalPrice = priceAfterPercentage; //สมมุติว่าใช้ส่วนลด 10%
        double totalCost = finalPrice + fullyLoaded.getCost();
        System.out.println("Final price after discount: "+finalPrice);
        System.out.println("Final shipment cost: "+ fullyLoaded.getCost());
        System.out.println("TOTAL TO PAY: "+ totalCost);

        // --- 5.Testing Observer Pattern (Processing Order) ---
        OrderProcessor.processOrder(myOrder);
    }   

}
