package org.example;

import java.sql.*;

public class SalesDatabase {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sales_database", "user", "password")) {




            Statement createTable = connection.createStatement();
            createTable.execute("CREATE TABLE sales (id INT AUTO_INCREMENT PRIMARY KEY, product VARCHAR(255), price DECIMAL(10,2), quantity INT)");


            PreparedStatement insertData = connection.prepareStatement("INSERT INTO sales (product, price, quantity) VALUES (?, ?, ?)");
            insertData.setString(1, "Laptop");
            insertData.setDouble(2, 1000);
            insertData.setInt(3, 5);
            insertData.executeUpdate();

            insertData.setString(1, "Phone");
            insertData.setDouble(2, 700);
            insertData.setInt(3, 3);
            insertData.executeUpdate();

            insertData.setString(1, "Tablet");
            insertData.setDouble(2, 500);
            insertData.setInt(3, 2);
            insertData.executeUpdate();

            insertData.setString(1, "Printer");
            insertData.setDouble(2, 300);
            insertData.setInt(3, 4);
            insertData.executeUpdate();


            Statement selectAll = connection.createStatement();
            ResultSet allSales = selectAll.executeQuery("SELECT * FROM sales");
            System.out.println("Всі записи:");
            while (allSales.next()) {
                System.out.println("ID: " + allSales.getInt("id") + ", Product: " + allSales.getString("product") + ", Price: " + allSales.getDouble("price") + ", Quantity: " + allSales.getInt("quantity"));
            }


            Statement selectLimit = connection.createStatement();
            ResultSet firstTwoSales = selectLimit.executeQuery("SELECT * FROM sales LIMIT 2");
            System.out.println("\nПерші два записи:");
            while (firstTwoSales.next()) {
                System.out.println("ID: " + firstTwoSales.getInt("id") + ", Product: " + firstTwoSales.getString("product") + ", Price: " + firstTwoSales.getDouble("price") + ", Quantity: " + firstTwoSales.getInt("quantity"));
            }


            Statement sumValue = connection.createStatement();
            ResultSet totalValue = sumValue.executeQuery("SELECT SUM(price * quantity) AS total_value FROM sales");
            totalValue.next();
            System.out.println("\nЗагальна вартість: " + totalValue.getDouble("total_value"));


            Statement groupByProduct = connection.createStatement();
            ResultSet groupedSales = groupByProduct.executeQuery("SELECT product, SUM(quantity) AS total_quantity, AVG(price) AS average_price FROM sales GROUP BY product");
            System.out.println("\nГрупування за продуктом:");
            while (groupedSales.next()) {
                System.out.println("Product: " + groupedSales.getString("product") + ", Total Quantity: " + groupedSales.getInt("total_quantity") + ", Average Price: " + groupedSales.getDouble("average_price"));
            }

        } catch (SQLException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
