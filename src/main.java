import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {

        boolean done =false;
        BasicFile f = new BasicFile();

        while(!done){

            String menu = "Enter option \n1. Copy a file \n2.Append to a file \n3. Overwrite a file \n4. File attributes \n5.display file \n7. Search word on file \n8 tokenized the file\n6.quit";
            String s = JOptionPane.showInputDialog(menu);

            try{
                int i = Integer.parseInt(s);
                switch (i)
                {

                    case 1:
                        f.saveFile();
                        break;

                    case 2:
                        f.appendToFile();
                        break;
                    case 3:
                        f.overWrite();
                        break;

                    case 4:
                        ArrayList<String> lines = new ArrayList<String>();
                        f.listRecursive(lines);
                        f.initialized(lines);
                        break;

                    case 5:
                        f.displayFIle();
                        break;

                    case 6:
                        done=true;
                        break;

                    case 7:
                        f.getLinesForWord();
                        break;
                    case 8:
                        f.TokenizerStream();
                        break;

                    default:
                        System.out.println(" ");
                        break;



                }

            } catch ( NumberFormatException | NullPointerException | IOException e) {
                System.out.println(e.toString());
            }


        }

    }
}
