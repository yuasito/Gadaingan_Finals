import java.util.Scanner;

class Index {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        User user = new User();
        String username, password, option;
        do {
            System.out.println("Welcome [Ctrl + C to exit]");
            System.out.print("Username: ");
            username = input.nextLine();
            System.out.print("Password: ");
            password = input.nextLine();

            boolean isCorrect = user.getUserLogin(username, password);
            if (username.equals("0") || password.equals("00")) {
                break;
            }
            while (isCorrect) {
                user.dashboardDisplay();
                String first_name, last_name;
                option = input.nextLine();
                if (option.equals("0")) {
                    break;
                }
                switch (option) {
                    case "1":
                        System.out.print("Username: ");
                        username = input.nextLine();
                        System.out.print("Password: ");
                        password = input.nextLine();
                        System.out.print("First Name: ");
                        first_name = input.nextLine();
                        System.out.print("Last Name: ");
                        last_name = input.nextLine();
                        user.createUserAndCredentials(username, password, first_name, last_name);
                        break;
                    case "2":
                        System.out.print("ID: ");
                        option = input.nextLine();
                        System.out.print("Username: ");
                        username = input.nextLine();
                        System.out.print("Password: ");
                        password = input.nextLine();
                        user.updateUser(option, username, password);
                        break;
                    case "3":
                        System.out.print("ID: ");
                        option = input.nextLine();
                        user.deleteUser(option);
                        break;
                    case "4":
                        System.out.print("ID: ");
                        option = input.nextLine();
                        boolean userExist = user.getUser(option);
                        if (!userExist) {
                            System.out.println("User not exist.");
                        } else {
                            System.out.print("First Name: ");
                            first_name = input.nextLine();
                            System.out.print("Last Name: ");
                            last_name = input.nextLine();
                            user.updateUserCredential(option, first_name, last_name);
                        }
                        break;
                    case "5":
                        user.getAllUsers();
                        break;

                    default:
                        System.out.println("You Did not choose anything.");
                        break;
                }
            }
        } while (true);
        input.close();
    }

}