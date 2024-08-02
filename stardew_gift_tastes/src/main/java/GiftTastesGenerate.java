import java.io.*;
import java.util.*;

public class GiftTastesGenerate {

    public static void main(String[] args) {
    	String inputFile = "F:\\Stardew Modding Data\\gifttastes_output.txt";  // Output file path
        String outputFile = "F:\\Stardew Modding Data\\gifttastes_individual.txt";  // Output file path

        List<String> lines = readFile(inputFile);
        List<String> convertedLines = convertData(lines);
        writeToFile(outputFile, convertedLines);
        System.out.println("Conversion completed. Output written to " + outputFile);
    }

    private static List<String> readFile(String inputFile) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static List<String> convertData(List<String> lines) {
        Map<String, Map<Integer, List<String>>> dataMap = new HashMap<>();
        String currentName = "";
        for (String line : lines) {
            if (line.matches("[A-Za-z]+")) {
                currentName = line;
            } else {
                String[] parts = line.split(" ", 2);
                int quantity = Integer.parseInt(parts[0]);
                String value = parts[1];
                if (!dataMap.containsKey(currentName)) {
                    dataMap.put(currentName, new HashMap<>());
                }
                if (!dataMap.get(currentName).containsKey(quantity)) {
                    dataMap.get(currentName).put(quantity, new ArrayList<>());
                }
                dataMap.get(currentName).get(quantity).add(value);
            }
        }

        List<String> convertedLines = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, List<String>>> entry : dataMap.entrySet()) {
            String name = entry.getKey();
            Map<Integer, List<String>> quantityMap = entry.getValue();
            for (Map.Entry<Integer, List<String>> innerEntry : quantityMap.entrySet()) {
                int quantity = innerEntry.getKey();
                List<String> values = innerEntry.getValue();
                String value = String.join(" ", values);
                convertedLines.add(convertToJSON(name, quantity, value));
            }
        }
        return convertedLines;
    }

    private static String convertToJSON(String name, int quantity, String value) {
        return "{\n" +
                " \"Operation\": \"Append\",\n" +
                "\"Target\": [\"Fields\",\"" + name + "\", " + quantity + "],\n" +
                "\"Value\": \"" + value + "\",\n" +
                " \"Delimiter\": \" \"\n" +
                " },";
    }

    private static void writeToFile(String outputFile, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}