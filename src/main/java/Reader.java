import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

    private File document;
    private String filePath;

    // OOP Boilerplate

    public Reader(File document, String filePath) {
        this.document = document;
        this.filePath = filePath;
    }

    public void setDocument(File document) {
        this.document = document;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public File editDocument() {
        ArrayList<String> headers = new ArrayList<String>();

        try (Scanner sc = new Scanner(document)) {
            while (sc.hasNextLine()) {
                String currentLine = sc.nextLine();

                if (isValidHeader(currentLine)) {
                    String preparedHeader = prepareHeader(currentLine);
                    headers.add(preparedHeader);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("It seems an error has occurred!: ");
            e.printStackTrace();
        }

        addHeadersToDocument(headers);
        return document;
    }

    private boolean isValidHeader(String currentLine) {
        // Any valid Markdown header is at least three characters long
        final int MIN_VALID_LENGTH = 3;

        if (currentLine.length() >= MIN_VALID_LENGTH) {
            int i = 0;
            if (currentLine.charAt(i++) == '#') {
                while (currentLine.charAt(i) == '#') {
                    i++;
                    if (currentLine.length() == i - 1) {
                      // Candidate header is empty
                      return false;
                    }
                }

                if (currentLine.charAt(i++) == ' ') {
                    // Candidate header is empty
                    return currentLine.length() != i - 1;
                } else {
                    // Candidate header doesn't follow up with space after last hashtag
                    return false;
                }
            } else {
                // Candidate header didn't started with a hashtag
                return false;
            }

        } else {
            // Candidate header had less than 3 characters
            return false;
        }

    }

    private String prepareHeader(String validHeader) {
        String hashtagLength = "";

        for (int i = 0; i < validHeader.length(); i++) {
            if (validHeader.charAt(i) == '#')
                hashtagLength+= "#";
        }

        String hashtaglessHeader = validHeader.substring(hashtagLength.length()+1);
        return hashtagLength + " [[" + hashtaglessHeader + "]]\n";
    }

    private void addHeadersToDocument(ArrayList<String> headers) {
        ArrayList<String> originalText = new ArrayList<String>();

        try (Scanner sc = new Scanner(document)) {
            while (sc.hasNextLine())
                originalText.add(sc.nextLine() + "\n");

        } catch(FileNotFoundException e) {
            System.out.println("It seems an error has occurred!: ");
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(document);
            for (String temp : headers)
                fw.write(temp);
            // Line break to separate index from the rest of the file
            fw.write("\n");
            for (String temp : originalText)
                fw.write(temp);
            fw.close();

        } catch(IOException e) {
            System.out.println("It seems an error has occurred!: ");
            e.printStackTrace();
        }

    }

}