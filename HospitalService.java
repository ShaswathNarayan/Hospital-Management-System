import java.util.Map;
import java.sql.SQLException; // Import SQLException

interface HospitalService {
    // Method to add a new hospital
    void addHospital(Map<String, Object> hospitalDetails);

    // Method to display hospital information based on hospital ID
    void displayByHospitalId(int hospitalId);

    // Method to display hospital information based on hospital name
    void displayByHospitalName(String hospitalName);

    // Method to display hospital information based on location
    void displayByHospitalLocation(String location);

    // Method to display all hospitals
    void displayHospitals();

    // Method to display detailed information about a hospital
    void displayHospitalDetails(Map<String, Object> hospitalDetails);

    // Method to update hospital information
    void updateHospital(Map<String, Object> hospitalUpdateDetails);

    // Method to delete a hospital
    void deleteHospital(Map<String, Object> hospitalDeleteDetails);
}
