import java.io.*;

public class FileManager {

    public static String readFile(String fileName) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null){
                String s = line;
                s.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static void writeFile(String fileName, String contents) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(contents.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
