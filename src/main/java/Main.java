import java.io.File;

public class Main {
    public static void main(String[] args) {

        // TODO: Fix formatting. This will require a major refactor of basically the whole program

        if(args.length == 0) {
            System.out.println("Error: failed to provide a filepath!");
            return;
        }

        String filePath = args[0];
        System.out.println("Using the following filepath: " + filePath);
        try {
            File inputFile = new File(filePath);

            if(inputFile.exists()) {
                Reader reader = new Reader(inputFile, filePath);
                reader.editDocument();
            } else {
                System.out.println("Error: the specified file or filepath does not exist");
            }
        } catch (Exception e) {
            System.err.println("An error has occurred while trying to access the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
