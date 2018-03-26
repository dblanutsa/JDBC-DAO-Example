package com.gmail.blanutsa.dmitriy.apartments;

import com.gmail.blanutsa.dmitriy.apartments.dao.ApartmentDAO;
import com.gmail.blanutsa.dmitriy.apartments.dao.ApartmentDAOImp;
import com.gmail.blanutsa.dmitriy.apartments.entity.Apartment;
import com.gmail.blanutsa.dmitriy.apartments.entity.SortOptions;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ApartmentDAO apartmentDAO = new ApartmentDAOImp("apartments");

    public static void main(String[] args) {

        while (true){
            System.out.print("1. view apartments\n" +
                    "2. add apartrment\n" +
                    "3. add random apartments\n" +
                    "4. delete apartment\n" +
                    "5. delete all apartments\n" +
                    "6. update apartment\n" +
                    "7. view apartments by district\n" +
                    "8. view apartments by address\n" +
                    "9. view apartments by area\n" +
                    "10. view apartment by rooms amount\n" +
                    "11. view apartments by price\n" +
                    "12. view apartment by area between\n" +
                    "13. view apartment by rooms amount between\n" +
                    "14. view apartment by price between\n" +
                    "15. exit\n" +
                    "->");

            String value = scanner.next();
            switch (value){
                case "1":
                    getAll();
                    break;
                case "2":
                    add();
                    break;
                case "3":
                    addRandom();
                    break;
                case "4":
                    System.out.print("Enter id: ");
                    apartmentDAO.delete(scanner.nextInt());
                    break;
                case "5":
                    apartmentDAO.deleteAll();
                    break;
                case "6":
                    update();
                    break;
                case "7":
                    getAllByOptions(SortOptions.DISTRICT);
                    break;
                case "8":
                    getAllByOptions(SortOptions.ADDRESS);
                    break;
                case "9":
                    getAllByOptions(SortOptions.AREA);
                    break;
                case "10":
                    getAllByOptions(SortOptions.ROOMS_AMOUNT);
                    break;
                case "11":
                    getAllByOptions(SortOptions.PRICE);
                    break;
                case "12":
                    getAllBetween(SortOptions.AREA);
                    break;
                case "13":
                    getAllBetween(SortOptions.ROOMS_AMOUNT);
                    break;
                case "14":
                    getAllBetween(SortOptions.PRICE);
                    break;
                case "15":
                    return;
            }
        }

    }

    private static void printApartments(List<Apartment> apartments){
        if (apartments.isEmpty()){
            System.out.println("List is empty\r\n");
        }else {
            for (Apartment apartment : apartments) {
                System.out.println(apartment);
            }
            System.out.println();
        }
    }

    private static void getAll(){
        List<Apartment> apartments = apartmentDAO.getAll();

        printApartments(apartments);
    }

    private static Apartment getApartmentFromConsole(){
        Apartment apartment = new Apartment();
        System.out.print("Enter district: ");
        apartment.setDistrict(scanner.next());

        System.out.print("Enter address: ");
        apartment.setAddress(scanner.next());

        System.out.print("Enter area: ");
        apartment.setArea(scanner.nextDouble());

        System.out.print("Enter rooms amount: ");
        apartment.setRoomAmount(scanner.nextInt());

        System.out.print("Enter price: ");
        apartment.setPrice(scanner.nextDouble());

        return  apartment;
    }

    private static void add(){
        apartmentDAO.add(getApartmentFromConsole());
    }

    private static void update(){
        System.out.print("Enter id: ");
        int id = scanner.nextInt();

        apartmentDAO.update(id, getApartmentFromConsole());
    }

    private static void addRandom(){
        System.out.print("Enter amount:");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++){
            apartmentDAO.add(new Apartment("district" + getRandomInt(),
                    "address" + getRandomInt(),
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt()));
        }
    }

    private static int getRandomInt(){
        return (int) (Math.random() * 100) %57;
    }

    private static void getAllByOptions(SortOptions sortOptions){
        System.out.print("Enter " + sortOptions.toString() + ":");

        if (sortOptions == SortOptions.DISTRICT || sortOptions == SortOptions.ADDRESS) {
            printApartments(apartmentDAO.getAllByOptions(sortOptions, scanner.next()));
        }else if (sortOptions == SortOptions.ROOMS_AMOUNT){
            printApartments(apartmentDAO.getAllByOptions(sortOptions, scanner.nextInt() + ""));
        }else {
            printApartments(apartmentDAO.getAllByOptions(sortOptions, scanner.nextDouble() + ""));
        }

    }

    private  static void getAllBetween(SortOptions sortOptions){
        System.out.print("Enter start: ");
        double start = scanner.nextDouble();

        System.out.print("Enter end: ");
        List<Apartment> apartments = apartmentDAO.getAllBetween(sortOptions, start, scanner.nextDouble());

        printApartments(apartments);
    }

}
