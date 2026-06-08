package com.example.order;

import java.util.List;

public class OrderService {

    private static final String CLIENT_TYPE_VIP = "VIP";
    private static final String CLIENT_TYPE_NEW = "NEW";
    private static final double DISCOUNT_VIP = 0.9;
    private static final double DISCOUNT_NEW = 0.95;
    private static final int LARGE_SUM_ORDER = 1000;
    private static final int DISCOUNT_LARGE_SUM_ORDER = 50;
    private static final int TEN = 10;
    private static final double DISCOUNT_TOTAL_ITEM_MORE_THAN_TEN = 0.99;

    /**
     * Рассчитывает итоговую стоимость заказа с учетом скидок по типу клиента
     * ,общей сумме корзины и количества товаров.
     *
     * @param items список товаров в заказе, содержащих цену и количество
     * @param type  тип клиента для определения персональной скидки ("VIP", "NEW" и др.)
     * @return итоговая стоимость заказа после применения всех скидок
     */
    public double calc(List<Item> items, String type) {
        if (items.isEmpty() || items == null) {
            return 0.0;
        }
        double totalSum = totaAmountAllItems(items);
        totalSum = discountDependingTypeCustomer(totalSum, type);
        totalSum = discountDependingOrderAmount(totalSum);
        totalSum = discountNumberProductsMoreThenTen(items, totalSum);
        return totalSum;
    }

    /**
     * Расчет общей суммы заказа без скидок
     *
     * @param items список товаров в заказе, содержащих цену и количество
     * @return итоговая стоимость заказа
     */
    private double totaAmountAllItems(List<Item> items) {
        double totalSum = 0;
        for (Item i : items) {
            totalSum += i.getPrice() * i.getQuantity();
        }
        return totalSum;
    }

    /**
     * Расчет скидки в зависимости от типа клиента
     *
     * @param totalSum общая сумма заказа
     * @param type тип клиента, для определения скидки
     * @return итоговая стоимость заказа с учетом скидки
     */
    private double discountDependingTypeCustomer(double totalSum, String type) {
        if (type.equals(CLIENT_TYPE_VIP)) {
            totalSum = totalSum * DISCOUNT_VIP;
        }

        if (type.equals(CLIENT_TYPE_NEW)) {
            totalSum = totalSum * DISCOUNT_NEW;
        }
        return totalSum;
    }

    /**
     * Расчет скидки в зависимости от суммы заказа
     *
     * @param totalSum общая сумма заказа
     * @return итоговая стоимость заказа с учетом скидки
     */
    private double discountDependingOrderAmount(double totalSum) {
        if (totalSum > LARGE_SUM_ORDER) {
            totalSum = totalSum - DISCOUNT_LARGE_SUM_ORDER;
        }
        return totalSum;
    }

    /**
     * Расчет скидки в зависимости от количества товаров
     *
     * @param items список товаров в заказе, содержащих цену и количество
     * @param totalSum общая сумма заказа
     * @return итоговая стоимость заказа с учетом скидки
     */
    private double discountNumberProductsMoreThenTen(List<Item> items, double totalSum) {
        int totalItem = 0;
        for (Item i : items) {
            totalItem += i.getQuantity();
        }
        return (totalItem > TEN) ? totalSum * DISCOUNT_TOTAL_ITEM_MORE_THAN_TEN : totalSum;
    }
}
