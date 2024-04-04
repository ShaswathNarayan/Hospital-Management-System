import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;

// Implementation of the HospitalService interface
class HospitalServiceImpl implements HospitalService {
    private HospitalDAO hospitalDao = new HospitalDAOImpl(); // Instance of the HospitalDAO interface

    // Method to add a new hospital record
    public void addHospital(Map<String, Object> hospitalDetails) {
        try {
            // Extracting details from the map
            int hospitalId = Integer.parseInt(String.valueOf(hospitalDetails.get("hospitalId")));
            String hospitalName = String.valueOf(hospitalDetails.get("hospitalName"));
            String licenseNumber = String.valueOf(hospitalDetails.get("licenseNumber"));
            String hospitalLocation = String.valueOf(hospitalDetails.get("hospitalLocation"));
            String hospitalSpecialization = String.valueOf(hospitalDetails.get("hospitalSpecialization"));
            boolean isMultispeciality = Boolean.valueOf(String.valueOf(hospitalDetails.get("isMultispeciality")));
            String createdBy = String.valueOf(hospitalDetails.get("createdBy"));
            Timestamp currentDateTime = Timestamp.valueOf(String.valueOf(hospitalDetails.get("currentDateTime")));
            String modifiedBy = String.valueOf(hospitalDetails.get("modifiedBy"));
            Timestamp modifiedDateTime = Timestamp.valueOf(String.valueOf(hospitalDetails.get("modifiedDateTime")));

            // Call the DAO method to add the hospital
            int insertRowAffected = hospitalDao.addHospital(hospitalId, hospitalName, licenseNumber, hospitalLocation,
                    hospitalSpecialization, isMultispeciality, createdBy, currentDateTime, modifiedBy,
                    modifiedDateTime);
            if (insertRowAffected > 0) {
                System.out.println("Inserted Record Successfully");
            } else {
                System.out.println("Failed to insert record.");
            }
        } catch (SQLException e) {
            System.out.println("Error while adding hospital: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }

    // Method to display hospital details by ID
    public void displayByHospitalId(int hospitalId) {
        try {
            List<Map<String, Object>> hospitalsList = hospitalDao.displayHospitals();

            boolean found = false;
            for (Map<String, Object> hospitalDetails : hospitalsList) {
                int id = Integer.parseInt(String.valueOf(hospitalDetails.get("Hospital ID")));
                if (id == hospitalId) {
                    displayHospitalDetails(hospitalDetails);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Hospital with ID '" + hospitalId + "' not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while displaying hospital: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }

    // Method to display hospital details by name
    public void displayByHospitalName(String hospitalName) {
        try {
            List<Map<String, Object>> hospitalsList = hospitalDao.displayHospitals();

            boolean found = false;
            for (Map<String, Object> hospitalDetails : hospitalsList) {
                String name = String.valueOf(hospitalDetails.get("Hospital Name"));
                if (name.equalsIgnoreCase(hospitalName)) {
                    displayHospitalDetails(hospitalDetails);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Hospital with name '" + hospitalName + "' not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while displaying hospital: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }

    // Method to display hospital details by location
    public void displayByHospitalLocation(String location) {
        try {
            List<Map<String, Object>> hospitalsList = hospitalDao.displayHospitals();

            boolean found = false;
            for (Map<String, Object> hospitalDetails : hospitalsList) {
                String hospitalLocation = String.valueOf(hospitalDetails.get("Location"));
                if (hospitalLocation.equalsIgnoreCase(location)) {
                    displayHospitalDetails(hospitalDetails);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Hospital with location '" + location + "' not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while displaying hospital: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }

    // Method to display all hospitals
    public void displayHospitals() {
        try {
            List<Map<String, Object>> hospitalsList = hospitalDao.displayHospitals();

            for (Map<String, Object> hospitalDetails : hospitalsList) {
                displayHospitalDetails(hospitalDetails);
            }
        } catch (SQLException e) {
            System.out.println("Error while displaying hospital: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }

    // Method to display hospital details
    public void displayHospitalDetails(Map<String, Object> hospitalDetails) {
        int id = Integer.parseInt(String.valueOf(hospitalDetails.get("Hospital ID")));
        String name = String.valueOf(hospitalDetails.get("Hospital Name"));
        String licenseNumber = String.valueOf(hospitalDetails.get("License Number"));
        String hospitalLocation = String.valueOf(hospitalDetails.get("Location")); // Added hospitalLocation
        String specialization = String.valueOf(hospitalDetails.get("Specialization")); // Added specialization
        boolean isMultispeciality = Boolean.valueOf(String.valueOf(hospitalDetails.get("Is Multispeciality")));
        String createdBy = String.valueOf(hospitalDetails.get("Created By"));
        Timestamp createdTimestamp = (Timestamp) hospitalDetails.get("Created Time");
        String modifiedBy = String.valueOf(hospitalDetails.get("Modified By"));
        Timestamp modifiedTimestamp = (Timestamp) hospitalDetails.get("Modified Time");

        // Format date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        String createdDateTime = createdTimestamp.toLocalDateTime().format(formatter);
        String modifiedDateTime = modifiedTimestamp.toLocalDateTime().format(formatter);

        // Display hospital details
        System.out.println("Hospital ID       : " + id);
        System.out.println("Hospital Name     : " + name);
        System.out.println("License Number    : " + licenseNumber);
        System.out.println("Location          : " + hospitalLocation); // Display hospitalLocation
        System.out.println("Specialization    : " + specialization); // Display specialization
        System.out.println("Is Multispeciality: " + isMultispeciality);
        System.out.println("Created By        : " + createdBy);
        System.out.println("Created Time      : " + createdDateTime);
        System.out.println("Modified By       : " + modifiedBy);
        System.out.println("Modified Time     : " + modifiedDateTime);
        System.out.println();
    }

    // Method to update hospital details
    public void updateHospital(Map<String, Object> hospitalUpdateDetails) {
        try {
            int hospitalId = Integer.valueOf(String.valueOf(hospitalUpdateDetails.get("hospitalId")));
            String newHospitalName = String.valueOf(hospitalUpdateDetails.get("newHospitalName"));
            String newHospitalLocation = String.valueOf(hospitalUpdateDetails.get("newHospitalLocation"));
            String newHospitalSpecialization = String.valueOf(hospitalUpdateDetails.get("newHospitalSpecialization"));
            String currentUser = String.valueOf(hospitalUpdateDetails.get("currentUser"));

            // Call the DAO method to update the hospital
            int updateRowAffected = 0;
            if (!newHospitalName.isEmpty() || !newHospitalLocation.isEmpty() || !newHospitalSpecialization.isEmpty()) {
                updateRowAffected = hospitalDao.updateHospital(hospitalId, newHospitalName, newHospitalLocation,
                        newHospitalSpecialization, currentUser);
            } else {
                System.out.println("No fields provided for update.");
                return;
            }

            if (updateRowAffected > 0) {
                System.out.println("Updated record successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while updating hospital: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }

    // Method to delete a hospital record
    public void deleteHospital(Map<String, Object> hospitalDeleteDetails) {
        try {
            int hospitalId = (int) hospitalDeleteDetails.get("hospitalId");

            // Call the DAO method to delete the hospital
            int deleteRowAffected = hospitalDao.deleteHospital(hospitalId);
            if (deleteRowAffected > 0) {
                System.out.println("Deleted record successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting hospital: " + e.getMessage());
            e.printStackTrace(); // Log the exception
        }
    }
}
