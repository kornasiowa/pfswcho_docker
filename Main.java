import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS books (id int AUTO_INCREMENT, title varchar(255), author varchar(255), release_year varchar(255), PRIMARY KEY (id));";

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://Full-2020-94877/pfswcho", "dkornas", "dominika");
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE);

            initializeDB(statement);

            String menu;
            do {
                System.out.println("1. Show all books\n2. Add book\n3. Edit book\n4. Delete book\n\nPress 0 to exit");
                menu = in.nextLine();

                switch (menu) {
                    case "1":
                        showAllBooks(statement);
                        break;
                    case "2":
                        addBook(statement);
                        break;
                    case "3":
                        editBook(statement);
                        break;
                    case "4":
                        deleteBook(statement);
                        break;
                }
            } while (!menu.equals("0"));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializeDB(Statement statement) throws SQLException {
        String query1 = "INSERT INTO books (title,author,release_year) VALUES ('Dziady','Adam Mickiewicz','2010')";
        statement.executeUpdate(query1);
        String query2 = "INSERT INTO books (title,author,release_year) VALUES ('Pan Tadeusz','Adam Mickiewicz','1997')";
        statement.executeUpdate(query2);
        String query3 = "INSERT INTO books (title,author,release_year) VALUES ('Lalka','Boleslaw Prus','2001')";
        statement.executeUpdate(query3);
    }

    private static void deleteBook(Statement statement) throws SQLException {
        showAllBooks(statement);

        System.out.println("\nWybierz ID książki do usunięcia: ");
        String id = in.nextLine();

        String query = "DELETE FROM books WHERE id = '" + id + "';";
        statement.executeUpdate(query);
        System.out.println("\n");
    }

    private static void editBook(Statement statement) throws SQLException {
        showAllBooks(statement);

        System.out.println("\nWybierz ID książki do edycji: ");
        String id = in.nextLine();

        System.out.println("Autor: ");
        String author = in.nextLine();

        System.out.println("Tytuł: ");
        String title = in.nextLine();

        System.out.println("Rok wydania: ");
        String year = in.nextLine();

        String query = "UPDATE books SET title = '" + title + "', author = '" + author + "', release_year = '" + year + "' WHERE id = '" + id + "';";
        statement.executeUpdate(query);
        System.out.println("\n");
    }

    private static void addBook(Statement statement) throws SQLException {
        System.out.println("\nPodaj dane o książce:");

        System.out.println("Autor: ");
        String author = in.nextLine();

        System.out.println("Tytuł: ");
        String title = in.nextLine();

        System.out.println("Rok wydania: ");
        String year = in.nextLine();

        String query = "INSERT INTO books (title,author,release_year) VALUES ('" + title + "', '" + author + "', '" + year + "')";
        statement.executeUpdate(query);
        System.out.println("\n");
    }

    private static void showAllBooks(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM books;");
        printToConsole(resultSet);
        resultSet.close();
    }

    private static void printToConsole(ResultSet resultSet) throws SQLException {
        System.out.println("\nID   AUTHOR            TITLE            RELEASE_YEAR");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String year = resultSet.getString("release_year");

            System.out.println(id + "    " + author + "    " + title + "    " + year);
        }
        System.out.println("\n");
    }

}
