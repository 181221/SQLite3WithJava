package no.pederyo;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class Main {
    public static final String FILENAME = "C:\\Users\\peder\\Documents\\Development\\sqlite\\test.db";
    public static final String DRIVER = "jdbc:sqlite:";

    public static void createNewDatabase(String fileName) {

        String url = DRIVER + FILENAME;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Connection connect() {
        // SQLite connection string
        String url = DRIVER + FILENAME;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = DRIVER + FILENAME;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS bruker (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + " passord text not null,\n"
                + " salt text NOT NULL "
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void getBruker(String name){
        String sql = "SELECT * FROM bruker WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, name);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("passord") + "\t" +
                        rs.getString("salt"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(String name, String passord, String salt) {
        String sql = "INSERT INTO bruker(name, passord, salt) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, passord);
            pstmt.setString(3, salt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static Bruker selectBruker(String navn){
        Bruker b = null;

        String sql = "SELECT * FROM bruker WHERE name = "+navn+" ";

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("passord") + "\t" +
                        rs.getString("salt"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return  b;
    }

    public void selectAll(){
        String sql = "SELECT * FROM bruker";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("passord") + "\t" +
                        rs.getString("salt"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void update(int id, String name, double capacity) {
        String sql = "UPDATE warehouses SET name = ? , "
                + "capacity = ? "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setInt(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createNewDatabase("test.db");
        createNewTable();
        // insert three new rows
        String password = "heipaadeg";
        String salt = BCrypt.gensalt(12);
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        insert("peder", hashed, salt);
        getBruker("peder");
        /*System.out.println(BCrypt.gensalt(12));
        System.out.println(hashed);
        if (BCrypt.checkpw(password, hashed))
            System.out.println("It matches");
        else
            System.out.println("It does not match");*/

    }
}
