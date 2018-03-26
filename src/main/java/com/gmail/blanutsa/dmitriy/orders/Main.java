package com.gmail.blanutsa.dmitriy.orders;

import com.gmail.blanutsa.dmitriy.orders.dao.OrdersDAO;
import com.gmail.blanutsa.dmitriy.orders.dao.OrdersDAOImp;
import com.gmail.blanutsa.dmitriy.orders.entity.Client;
import com.gmail.blanutsa.dmitriy.orders.entity.Entity;
import com.gmail.blanutsa.dmitriy.orders.entity.Order;
import com.gmail.blanutsa.dmitriy.orders.entity.Product;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static OrdersDAO ordersDAO = new OrdersDAOImp();

    public static void main(String[] args) {
        while (true){
            System.out.print("1. view products\n" +
                    "2. view clients\n" +
                    "3. view orders\n" +
                    "4. add product\n" +
                    "5. add client\n" +
                    "6. add order\n" +
                    "7. delete product\n" +
                    "8. delete client\n" +
                    "9. delere orders\n" +
                    "10. delere all products\n" +
                    "11. delete all clients\n" +
                    "12. delete all orders\n" +
                    "13. delete all\n" +
                    "-> ");
            String value = scanner.next();

            switch (value){
                case "1":
                    printList(ordersDAO.getAllFrom(Entity.PRODUCTS));
                    break;
                case "2":
                    printList(ordersDAO.getAllFrom(Entity.CLIENTS));
                    break;
                case "3":
                    printList(ordersDAO.getAllFrom(Entity.ORDERS));
                    break;
                case "4":
                    ordersDAO.addProduct(scannObjectFromConsole(Product.class));
                    break;
                case "5":
                    ordersDAO.addClient(scannObjectFromConsole(Client.class));
                    break;
                case "6":
                    ordersDAO.addOrder(scannObjectFromConsole(Order.class));
                    break;
                case "7":
                    System.out.print("Enter id: ");
                    ordersDAO.deleteProduct(scanner.nextInt());
                    break;
                case "8":
                    System.out.print("Enter id: ");
                    ordersDAO.deleteClient(scanner.nextInt());
                    break;
                case "9":
                    System.out.print("Enter id: ");
                    ordersDAO.deleteOrder(scanner.nextInt());
                    break;
                case "10":
                    ordersDAO.deleteAllFrom(Entity.ORDERS);
                    ordersDAO.deleteAllFrom(Entity.PRODUCTS);
                    break;
                case "11":
                    ordersDAO.deleteAllFrom(Entity.ORDERS);
                    ordersDAO.deleteAllFrom(Entity.CLIENTS);
                    break;
                case "12":
                    ordersDAO.deleteAllFrom(Entity.ORDERS);
                    break;
                case "13":
                    ordersDAO.deleteAll();
                    return;
            }
        }
    }

    private static <T> void printList(List<T> list){
        if (list.isEmpty()){
            System.out.println("List is empty\r\n");
        }else {
            for (T element : list) {
                System.out.println(element);
            }
            System.out.println();
        }
    }

    private static <T> T scannObjectFromConsole(Class<T> tClass){
        Field[] fields = tClass.getDeclaredFields();
        T element = null;
        try {
            element = tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.getName().equals("id")) {
                System.out.print("Enter " + field.getName() + ": ");
                try {
                    if (field.getType() == String.class) {
                        field.set(element, scanner.next());
                    } else if (field.getType() == int.class) {
                        field.set(element, scanner.nextInt());
                    } else {
                        field.set(element, scanner.nextDouble());
                    }
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
        return element;
    }
}
