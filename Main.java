import java.util.*;
import java.sql.Timestamp;

class Main {
    public static void main(String arg[]) {
        Scanner input = new Scanner(System.in);
        HospitalService hospitalService = new HospitalServiceImpl(); // Create an instance of HospitalService
        String currentUser = "";

        while (true) {
            System.out.println("Enter loginUser: ");
            currentUser = input.next(); // Get current user input

            int userChoice;

            while (true) {
                // Display menu options
                System.out.println("-------------------------------------------");
                System.out.println("---------Hospital Management System---------");
                System.out.println("1. Add Hospital");
                System.out.println("2. Update Hospital");
                System.out.println("3. Display Hospitals");
                System.out.println("4. Delete Hospital");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                userChoice = input.nextInt(); // Get user's choice

                switch (userChoice) {
                    case 1:
                        addHospital(input, hospitalService, currentUser);// Calls addHospital method
                        break;

                    case 2:
                        updateHospital(input, hospitalService, currentUser);// Calls updateHospital method
                        break;

                    case 3:
                        // Display sub-menu for displaying hospitals
                        System.out.println("1. Display by ID");
                        System.out.println("2. Display by Name");
                        System.out.println("3. Display by Location");
                        System.out.println("4. Display all hospitals");
                        int displayChoice = input.nextInt();
                        switch (displayChoice) {
                            case 1:
                                System.out.println("Enter Hospital ID:");
                                int hospitalId = input.nextInt();
                                hospitalService.displayByHospitalId(hospitalId); // Display by ID
                                break;
                            case 2:
                                System.out.println("Enter Hospital Name:");
                                String hospitalName = input.next();
                                hospitalService.displayByHospitalName(hospitalName); // Display by Name
                                break;
                            case 3:
                                System.out.println("Enter Hospital Location:");
                                input.nextLine(); // Consume newline character
                                String hospitalLocation = input.nextLine().trim();
                                hospitalService.displayByHospitalLocation(hospitalLocation); // Display by Location
                                break;
                            case 4:
                                hospitalService.displayHospitals(); // Display all hospitals
                                break;
                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                        break;

                    case 4:
                        deleteHospital(input, hospitalService);// Calls deleteHospital method
                        break;

                    case 5:
                        System.out.println("Exiting...");// Exit the program
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid Choice");
                }

                // Ask user if they want to continue
                System.out.println("Do You want to continue? y/n");
                char ch = input.next().charAt(0);
                if (ch != 'y' && ch != 'Y') {
                    System.exit(0);
                }
                if (userChoice == 5) {
                    break;
                }
            }
        }
    }

    // Method to add a new hospital
    private static void addHospital(Scanner input, HospitalService hospitalService, String currentUser) {
        try {
            Map<String, Object> hospitalDetails = new HashMap<>(); // Map to store the hospitalDetails
            System.out.println("Enter Hospital Id:");
            int hospitalId = input.nextInt();
            input.nextLine(); // Consume newline character
            System.out.println("Enter Hospital Name:");
            String hospitalName = input.nextLine();
            System.out.println("Enter License Number:");
            String licenseNumber = input.nextLine();
            System.out.println("Enter Hospital Location:");
            String hospitalLocation = input.nextLine();

            // (optional) field
            System.out.println("Enter Hospital Specialization:");
            String hospitalSpecialization = input.nextLine().trim(); // Trim whitespace
            if (hospitalSpecialization.isEmpty()) {
                hospitalSpecialization = null; // Set to null if no value provided
            }

            // Ensure that the user doesn't leave any compulsory field empty
            while (true) {
                if (hospitalName.isEmpty()) {
                    System.out.println("Hospital Name cannot be empty. Please re-enter:");
                    hospitalName = input.nextLine();
                } else if (licenseNumber.isEmpty()) {
                    System.out.println("License Number cannot be empty. Please re-enter:");
                    licenseNumber = input.nextLine();
                } else if (hospitalLocation.isEmpty()) {
                    System.out.println("Hospital Location cannot be empty. Please re-enter:");
                    hospitalLocation = input.nextLine();
                } else {
                    break; // Break out of the loop if all fields are non-empty
                }
            }

            System.out.println("Is Multispeciality Hospital? (y/n):");
            char choice = input.next().charAt(0);
            boolean isMultispeciality = (choice == 'y' || choice == 'Y');

            // Set createdBy and modifiedBy to the current user
            String createdBy = currentUser;
            String modifiedBy = currentUser;

            // Set currentDateTime to the current timestamp
            Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());

            // Populate hospitalDetails map with user inputs
            hospitalDetails.put("hospitalId", hospitalId);
            hospitalDetails.put("hospitalName", hospitalName);
            hospitalDetails.put("licenseNumber", licenseNumber);
            hospitalDetails.put("hospitalLocation", hospitalLocation); // Compulsory
            hospitalDetails.put("hospitalSpecialization", hospitalSpecialization); // May be null
            hospitalDetails.put("isMultispeciality", isMultispeciality);
            hospitalDetails.put("createdBy", createdBy);
            hospitalDetails.put("currentDateTime", currentDateTime); // Set currentDateTime
            hospitalDetails.put("modifiedBy", modifiedBy);
            hospitalDetails.put("modifiedDateTime", currentDateTime);

            // Call HospitalService to add hospitalDetails
            hospitalService.addHospital(hospitalDetails);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
            input.nextLine(); // Clear input buffer
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    // Method to update hospital details
    public static void updateHospital(Scanner input, HospitalService hospitalService, String currentUser) {
        try {
            System.out.println("Enter Hospital ID:");
            int hospitalId = input.nextInt();
            input.nextLine(); // Consume newline character

            System.out.println("Enter 1 to update Hospital Name");
            System.out.println("Enter 2 to update Hospital Location");
            System.out.println("Enter 3 to update Hospital Specialization");
            System.out.println("Enter 4 to update all three details");
            int choice = input.nextInt();
            input.nextLine(); // Consume newline character

            String newHospitalName = null;
            String newHospitalLocation = null;
            String newHospitalSpecialization = null;

            // Based on user choice, get new values for hospital details
            switch (choice) {
                case 1:
                    System.out.println("Enter New Hospital Name:");
                    newHospitalName = input.nextLine().trim();
                    break;
                case 2:
                    System.out.println("Enter New Hospital Location:");
                    newHospitalLocation = input.nextLine().trim();
                    break;
                case 3:
                    System.out.println("Enter New Hospital Specialization:");
                    newHospitalSpecialization = input.nextLine().trim();
                    break;
                case 4:
                    System.out.println("Enter New Hospital Name:");
                    newHospitalName = input.nextLine().trim();

                    System.out.println("Enter New Hospital Location:");
                    newHospitalLocation = input.nextLine().trim();

                    System.out.println("Enter New Hospital Specialization:");
                    newHospitalSpecialization = input.nextLine().trim();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            // Populate hospitalUpdateDetails map with updated values
            Map<String, Object> hospitalUpdateDetails = new HashMap<>();
            hospitalUpdateDetails.put("hospitalId", hospitalId);
            hospitalUpdateDetails.put("newHospitalName", newHospitalName);
            hospitalUpdateDetails.put("newHospitalLocation", newHospitalLocation);
            hospitalUpdateDetails.put("newHospitalSpecialization", newHospitalSpecialization);
            hospitalUpdateDetails.put("currentUser", currentUser);

            // Call HospitalService to update hospital
            hospitalService.updateHospital(hospitalUpdateDetails);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for hospital ID. Please enter a valid integer.");
            input.nextLine(); // Clear input buffer
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    // Method to delete a hospital
    private static void deleteHospital(Scanner input, HospitalService hospitalService) {
        try {
            Map<String, Object> hospitalDeleteDetails = new HashMap<>();
            System.out.println("Enter Hospital ID to delete:");
            int hospitalId = input.nextInt();

            // Store hospitalId in the map
            hospitalDeleteDetails.put("hospitalId", hospitalId);

            // Call the deleteHospital method with the map containing hospitalId
            hospitalService.deleteHospital(hospitalDeleteDetails);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for hospital ID. Please enter a valid integer.");
            input.nextLine(); // Clear input buffer
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
