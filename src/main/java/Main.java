import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BufferedReader reader;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(System.getProperty("line.separator"));

        ArrayList<String> attribuesLines = new ArrayList<>();
        try {
            String filePath = args[0];

            reader = new BufferedReader(new FileReader(
                    filePath));
            String l = reader.readLine();
            while (l != null) {

                if (l.contains(";") && !l.contains("import") && !l.contains("package")) {
                    attribuesLines.add(l);
                }

                l = reader.readLine();
            }
            reader.close();

            for (int i = 0; i < attribuesLines.size(); i++) {
                String line = attribuesLines.get(i);
                String[] words = line.split(" ");
                int length = words.length;
                String attributeName = words[length - 1];
                attributeName = attributeName.substring(0, attributeName.length() - 1);
                stringBuilder.append("\t");
                stringBuilder.append(attributeName);
                stringBuilder.append(": ");
                stringBuilder.append("\"");

                String value = "";
                String attributeType = words[length - 2];
                if (attributeType.equals("String")) {
                    value = "str";
                } else if (attributeType.equals("LocalDate")) {
                    value = "2019-05-16";
                } else if (attributeType.equals("Boolean")) {
                    value = "true";
                }

                stringBuilder.append(value);
                stringBuilder.append("\"");

                if (i != attribuesLines.size() - 1) {
                    stringBuilder.append(",");
                }

                stringBuilder.append(System.getProperty("line.separator"));
            }

            stringBuilder.append("}");
            String output = stringBuilder.toString();
            System.out.println(output);
            // from string to clipboard
            StringSelection selection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);

            System.out.println("JSON sample set to clipboard");

            // moras ostavit otvoren proces da bi radili na Ubuntu
            // https://stackoverflow.com/questions/14242719/copying-to-global-clipboard-does-not-work-with-java-in-ubuntu
            //System.in.read();
            Thread.sleep(15000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



