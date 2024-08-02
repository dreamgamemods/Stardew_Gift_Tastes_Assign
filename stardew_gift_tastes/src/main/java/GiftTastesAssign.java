import java.io.*;
import java.util.*;

public class GiftTastesAssign {

    public static void main(String[] args) throws IOException {
        String idListFile = "F:\\Stardew Modding Data\\gifttastes_giftinput.txt";  // Specify your folder path here
        String giftNamesFile = "F:\\Stardew Modding Data\\gifttastes_nameinput.txt";  // Output file path
        String outputFile = "F:\\Stardew Modding Data\\gifttastes_output.txt";  // Output file path

        List<String> giftNames = readLinesFromFile(giftNamesFile);
        List<String> ids = readLinesFromFile(idListFile);

        // Shuffle the ids list to be randomized
        Collections.shuffle(ids);

        List<List<String>> characterGiftsList = new ArrayList<>();
        Random random = new Random();

        // Assign three gifts from the ids list to each character
        int numberOfGiftsPerCharacter = 5;
        int currentIndex = 0;
        for (String character : giftNames) {
            List<String> selectedGifts = new ArrayList<>();
            for (int i = 0; i < numberOfGiftsPerCharacter; i++) {
                // Get the gift from the current index, and if the index exceeds the size of the ids list, wrap around
                String gift = ids.get(currentIndex % ids.size());
                selectedGifts.add(gift);
                currentIndex++;
            }
            characterGiftsList.add(selectedGifts);
        }

        // Write character names and their corresponding gifts with assigned numbers to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (int i = 0; i < giftNames.size(); i++) {
                String character = giftNames.get(i);
                writer.write(character); // Write character name
                writer.newLine();
                List<String> gifts = characterGiftsList.get(i);
                for (String gift : gifts) {
                    int taste = randomTaste(random);
                    writer.write(taste + " " + gift);
                    writer.newLine();
                }
            }
        
            writer.close();
            System.out.println("Output written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readLinesFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line.trim());
        }
        reader.close();
        return lines;
    }

    private static int randomTaste(Random random) {
    	//#1 = love, #3 = like, #5 = dislike, #7 = hated, #9 = neutral
        List<Integer> possibleTastes = Arrays.asList(1, 3, 5, 7, 9);
        return possibleTastes.get(random.nextInt(possibleTastes.size()));
    }
}