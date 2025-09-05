package StregyPattern;
import DataModels.*;
public interface DiscountStrategy {
    double applyDiscount(Order order);
}
