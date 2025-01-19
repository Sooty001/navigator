import navigator.Navigator;
import navigator.domain.Route;
import navigator.impl.NavigatorImpl;
import struct.impl.ArrayList;

import java.util.Scanner;

public class Main {
    private static final Navigator navigator = new NavigatorImpl();
    public static void main(String[] args) {
        createRoutes();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addRoute(scanner);
                case 2 -> removeRouteById(scanner);
                case 3 -> routeExistenceCheck(scanner);
                case 4 -> printSize();
                case 5 -> getRouteById(scanner);
                case 6 -> increaseRoutePopularity(scanner);
                case 7 -> findRoutesBetweenPoints(scanner);
                case 8 -> getFavoriteRoutes(scanner);
                case 9 -> getTop5Routes();
                case 0 -> {
                    System.out.println("Выход");
                    return;
                }
                default -> System.out.println("Такого пункта в меню нету!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nМеню навигатора: \n1. Добавить маршрут \n2. Удалить маршрут \n3. Проверить наличие маршрута" +
                " \n4. Получить количество маршрутов \n5. Получить маршрут по id \n6. Увеличить популярность \n7. " +
                "Найти маршруты по начальной и конечной точке \n8. Получить избранные маршруты \n9. Получить топ 5 маршрутов \n0. Выход");
        System.out.print("Введите ваш выбор: ");
    }

    private static void createRoutes() {
        navigator.addRoute(new Route("1", 100, 5, true, createPoints("Москва", "Тула", "Рязань")));
        navigator.addRoute(new Route("2", 300, 8, false, createPoints("Воронеж", "Липецк", "Тамбов")));
        navigator.addRoute(new Route("3", 180, 6, false, createPoints("Москва", "Владимир", "Рязань", "Тула")));
        navigator.addRoute(new Route("5", 405, 6, false, createPoints("Питер", "Гатчина", "Новгород", "Псков")));
        navigator.addRoute(new Route("6", 335, 6, false, createPoints("Питер", "Тверь", "Псков")));
        navigator.addRoute(new Route("7", 573, 5, false, createPoints("Питер", "Тверь", "Москва", "Новгород", "Псков")));
        navigator.addRoute(new Route("8", 476, 4, true, createPoints("Питер", "Гатчина", "Новгород", "Псков")));
        navigator.addRoute(new Route("9", 298, 5, false, createPoints("Питер", "Новгород", "Псков")));
        navigator.addRoute(new Route("10", 320, 6, true, createPoints("Москва", "Тула", "Рязань", "Саратов")));
        navigator.addRoute(new Route("11", 380, 3, true, createPoints("Питер", "Тверь", "Рязань", "Псков")));
        navigator.addRoute(new Route("12", 380, 7, true, createPoints("Казань", "Екатеринбург", "Рязань", "Тамбов", "Новгород")));
        navigator.addRoute(new Route("13", 380, 4, true, createPoints("Владимир", "Рязань", "Тула", "Москва")));
        navigator.addRoute(new Route("14", 180, 6, false, createPoints("Москва", "Гатчина", "Владимир", "Тула", "Рязань")));
        navigator.addRoute(new Route("15", 200, 3, false, createPoints("Питер", "Москва", "Псков")));
        navigator.addRoute(new Route("16", 280, 7, false, createPoints("Нижний Новгород", "Казань", "Екатеринбург", "Тамбов", "Липецк")));
    }


    private static ArrayList<String> createPoints(String... points) {
        ArrayList<String> locationPoints = new ArrayList<>();
        for (String point : points) {
            locationPoints.add(point);
        }
        return locationPoints;
    }

    private static void addRoute(Scanner scanner) {
        System.out.print("Введите id маршрута: ");
        String id = scanner.nextLine();
        System.out.print("Введите расстояние маршрута: ");
        double distance = Double.parseDouble(scanner.nextLine());
        System.out.print("Введите популярность маршрута: ");
        int popularity = Integer.parseInt(scanner.nextLine());
        System.out.print("Является ли маршрут избранным? (true/false): ");
        boolean isFavorite = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Введите точки маршрута (через запятую): ");
        String[] points = scanner.nextLine().split(",");
        ArrayList<String> locationPoints = createPoints(points);

        Route route = new Route(id, distance, popularity, isFavorite, locationPoints);
        navigator.addRoute(route);
        System.out.println("Маршрут был добавлен");
    }

    private static void removeRouteById(Scanner scanner) {
        System.out.print("Введите id маршрута для удаления: ");
        String routeId = scanner.nextLine();
        navigator.removeRoute(routeId);
        System.out.println("Маршрут был удален");
    }

    private static void routeExistenceCheck(Scanner scanner) {
        System.out.print("Введите id маршрута для проверки: ");
        String routeId = scanner.nextLine();
        Route route = navigator.getRoute(routeId);
        if (route != null && navigator.contains(route)) {
            System.out.println("Маршрут найден");
        } else {
            System.out.println("Такого маршрута нету");
        }
    }

    private static void printSize() {
        System.out.println("Общее количество маршрутов: " + navigator.size());
    }

    private static void getRouteById(Scanner scanner) {
        System.out.print("Введите id маршрута: ");
        String routeId = scanner.nextLine();
        Route route = navigator.getRoute(routeId);
        if (route != null) {
            System.out.println("Маршрут: " + route);
        } else {
            System.out.println("Такого маршрута нету");
        }
    }

    private static void increaseRoutePopularity(Scanner scanner) {
        System.out.print("Введите id маршрута: ");
        String routeId = scanner.nextLine();
        navigator.chooseRoute(routeId);
        System.out.println("Популярность маршрута увеличена");
    }

    private static void findRoutesBetweenPoints(Scanner scanner) {
        System.out.print("Введите начальную точку: ");
        String startPoint = scanner.nextLine();
        System.out.print("Введите конечную точку: ");
        String endPoint = scanner.nextLine();
        Iterable<Route> routes = navigator.searchRoutes(startPoint, endPoint);
        System.out.println("Найденные маршруты:");
        routes.forEach(System.out::println);
    }

    private static void getFavoriteRoutes(Scanner scanner) {
        System.out.print("Введите конечную точку: ");
        String destinationPoint = scanner.nextLine();
        Iterable<Route> routes = navigator.getFavoriteRoutes(destinationPoint);
        System.out.println("Избранные маршруты:");
        routes.forEach(System.out::println);
    }

    private static void getTop5Routes() {
        Iterable<Route> routes = navigator.getTop3Routes();
        System.out.println("Топ 5 маршрутов:");
        routes.forEach(System.out::println);
    }
}
