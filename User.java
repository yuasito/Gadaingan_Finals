import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {

    private boolean output;

    public boolean getUserLogin(String username, String passsword) {
        try {
            Connection con = conn.getConnection();
            // 1. Complete this sql query, don't mind the complex code below.
            String query = " SELECT * FROM  users WHERE username = ?"; // Complete this
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet res = stmt.executeQuery();
            String dbPass;
            while (res.next()) {
                dbPass = res.getString("password");
                if (!dbPass.equals(passsword)) {
                    System.out.println("Incorrect Password or username.");
                }
                if (dbPass.equals(passsword)) {
                    output = true;
                }
            }
            con.close();
        } catch (Exception e) {
            System.err.println("getting user error\n" + e);
        }
        return output;
    }

    public void getAllUsers() {
        System.out.println("List of Users");
        try {
            Connection con = conn.getConnection();
            // 2. Add a query here. easy query so far...
            String query = "SELECT users.id, users.username, credentials.first_name, credentials.last_name  FROM users JOIN credentials ON users.id = credentials.user_id";
            ResultSet res = con.prepareStatement(query).executeQuery();
            while (res.next()) {
                System.out.print("User ID: " + res.getInt("id"));
                System.out.print(" | Username: " + res.getString("username"));
                System.out.print(" | First name: " + res.getString("first_name"));
                System.out.println(" | Last name: " + res.getString("last_name"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error at getting users\n" + e);
        }
    }

    public void createUserAndCredentials(String username, String password, String first_name, String last_name) {
        boolean isExist = false;
        try {
            Connection con = conn.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE username = ?"; // 3. Complete this query, tip use COUNT()
            PreparedStatement stmt_is_user_exist = con.prepareStatement(query);
            stmt_is_user_exist.setString(1, username);
            ResultSet rs = stmt_is_user_exist.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) >= 1) {
                    isExist = true;
                    System.out.println("Creation Failed, Username Exist.");
                }
            }
            if (!isExist) {
                query = "INSERT INTO users (username, password) VALUES(?,?)"; // 4. Complete this query
                PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.executeUpdate();
                ResultSet res = stmt.getGeneratedKeys();
                if (res.next()) {
                    int last_row_id = res.getInt(1);
                    // 5. Fill this query, there might be a clue somewhere, you might look above
                    String newQuery = "INSERT INTO credentials (user_id, first_name, last_name) VALUES(?,?,?)";
                    stmt = con.prepareStatement(newQuery);
                    stmt.setInt(1, last_row_id);
                    stmt.setString(2, first_name);
                    stmt.setString(3, last_name);
                    stmt.executeUpdate();
                }
                System.out.println("Created Successfully");
                con.close();
            }
        } catch (Exception e) {
            System.err.println("Create user Error:\n" + e);
        }
    }

    public void updateUser(String id, String username, String password) {

        try {
            Connection con = conn.getConnection();
            String query = "UPDATE users SET username=?,password=? WHERE id=?"; // Complete this query
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, id);
            stmt.executeUpdate();
            System.out.println("Updated Successfully.");
        } catch (Exception e) {
            System.err.println("Update user Error:\n" + e);
        }
    }

    public void deleteUser(String id) {
        try {
            Connection con = conn.getConnection();
            String credQuery = "DELETE FROM credentials WHERE user_id  = ?"; // 6. Complete this query
            PreparedStatement credStmt = con.prepareStatement(credQuery);
            credStmt.setString(1, id);
            credStmt.executeUpdate();

            // 7. Complete this query, there a clue above on how to write this query
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, id);
            stmt.executeUpdate();
            System.out.println("Deleted User Successfully.");
        } catch (Exception e) {
            System.err.println("Delete user Error:\n" + e);
        }
    }

    public void updateUserCredential(String id, String first_name, String last_name) {
        try {
            Connection con = conn.getConnection();
            // 8. Complete this query, by now you know how to query this without any assistance.
            String credQuery = "UPDATE credentials SET first_name = ?, last_name = ? WHERE user_id = ?";
            PreparedStatement credStmt = con.prepareStatement(credQuery);
            credStmt.setString(1, first_name);
            credStmt.setString(2, last_name);
            credStmt.setString(3, id);
            credStmt.executeUpdate();
            System.out.println("Updated User ID " + id + " Credential Successfully");
        } catch (Exception e) {
            System.err.println("Create User Credential Error:\n" + e);
        }
        System.out.println("Updation Failed.");
    }

    public boolean getUser(String id){
        try {
            Connection con = conn.getConnection();
            String query = "SELECT * FROM users WHERE id = ?"; // 9. Complete this query, you can do it.
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, id);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                System.out.print("User ID: " + res.getInt("id"));
                System.out.print(" | Username: " + res.getString("username"));
            }else{
                output = false;
            }
            con.close();
        } catch (Exception e) {
            System.err.println("Getting User Error:\n" + e);
        }
        return output;
    }

    public void dashboardDisplay() {
        System.out.println("\n===============\nInput 0 to logout");
        System.out.println("1. Create User");
        System.out.println("2. Update User");
        System.out.println("3. Delete User");
        System.out.println("4. Update User Credential");
        System.out.println("5. List all Users");
        System.out.print("Input: ");
    }
}
