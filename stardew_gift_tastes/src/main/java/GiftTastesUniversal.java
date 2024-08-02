import java.io.*;
import java.util.*;

public class GiftTastesUniversal {

    public static void main(String[] args) {
    	String inputFile = "F:\\Stardew Modding Data\\gifttastes_giftinput.txt";  // Output file path
        String outputFile = "F:\\Stardew Modding Data\\gifttastes_universal.txt";  // Output file path

        try {
            // Open the input file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            // Read the list of items from the input file
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim()).append(", "); // Trim the line and add a space
            }
            reader.close();

            // Write the formatted list to the output file
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(sb.toString().trim()); // Write the formatted list to the output file
            writer.close();

            System.out.println("Formatted list has been written to " + outputFile);
        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }
}