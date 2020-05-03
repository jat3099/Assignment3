import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BasicFile {

    File f;


    public BasicFile() {

        JFileChooser choose = new JFileChooser(".");
        int status = choose.showOpenDialog(null);

        try {

            if (status != JFileChooser.APPROVE_OPTION) throw new IOException();
            f = choose.getSelectedFile();
            if (!f.exists()) throw new FileNotFoundException();

        } catch (FileNotFoundException e) {
            display(e.toString(), "File not found ");

        } catch (IOException e) {

            display(e.toString(), "Approve option was not selected");
            e.printStackTrace();
        }

    }
    File getFile() {
        return f;
    }

    private void display(String toString, String s) {

        JOptionPane.showMessageDialog(null, toString, s, JOptionPane.ERROR_MESSAGE);

    }



    void listRecursive(ArrayList<String> lines) throws IOException {
        listRecursive(f.getParentFile(),lines);
    }


    void listRecursive(File dir, ArrayList<String> lines) throws IOException {


        if (dir.isDirectory()) {
            File[] f = dir.listFiles();
            System.out.println(f.length);
            for (File i : f) {

                if (i.isFile()) {
                    String s = i.getAbsolutePath()+": Size of the file = "
                            +i.length() / 1024 + "  ***bytes\n";
                    s += readLines(i);
                    lines.add(s);
                }
                else {
                    lines.add(" Directory:  " + i.getName());
                    listRecursive(i, lines);
                }
            }//end of for loop
        }//END OF IF STATEMENT
    }


    void overWrite() throws IOException {
        System.out.println(" Enter input to OverWrite File: ");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            bw.write("Warning; Overwriting existing data");
            bw.write(text);
            bw.newLine();
            bw.close();
            System.out.println(" your input " + text.length() + " was saved ");
        } catch (Exception e) {
            System.out.println(" Error failed overwriting data");
        }
    }


    void appendToFile() throws IOException {

        System.out.println("enter input to append to file : ");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        FileWriter fw = null; //new FileWriter(f,true);
        BufferedWriter bw = null; //new BufferedWriter(fw);

        try {
            fw = new FileWriter(f, true);
            bw = new BufferedWriter(fw);
            bw.write("hello ma freind itve eeeeee");
            bw.write(text);
            bw.newLine();
            bw.close();
            System.out.println(" your inout if " + text.length() + " was saved ");

        } catch (Exception e) {
            System.out.println(" ERROR ");
        }
    }//end of appendToFile method


    void saveFile() throws IOException {

        //Check if the input file is a text file
        if(f.getName().contains(".txt")) {
            String path = f.getAbsolutePath();
            String copyName = path.substring(0,path.length()-4)+"_copy.txt";
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File(copyName)));
            LineNumberReader lnr = new LineNumberReader(new FileReader(getFile()));
            String line = "";

            while ((line = lnr.readLine()) != null) {

                bf.write(line, 0, line.length());
                bf.newLine();
            }

            bf.flush();
            bf.close();
        }
        else {
            FileInputStream fis = new FileInputStream(getFile());
            String path = f.getAbsolutePath();
            int indexPeriod = path.lastIndexOf(".");
            String ext = path.substring(indexPeriod); //.jpg, .png, ...
            String copyName = path.substring(0,indexPeriod)+"_copy"+ext;
            FileOutputStream fos = new FileOutputStream(new File(copyName));
            byte[] buffer = new byte[1024];
            int size;
            while((size = fis.read(buffer)) > 0) {
                fos.write(buffer,0,size);
            }

            fis.close();
            fos.close();
        }
    }



    String readLines(File f)throws IOException// option 4
    {
        BufferedReader bf = new BufferedReader(new FileReader(f));
        LineNumberReader lnr = new LineNumberReader( bf);

        String line =" ",s=" ";

        while ((line = lnr.readLine()) != null) {
            s = s + " line " + lnr.getLineNumber()+ ": " + line + "\n";
        }
        return s;

    }
    void initialized(ArrayList<String> lines){
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        for(int i = 0; i < lines.size(); i++) {
            textArea.append(lines.get(i));
        }
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize( new Dimension( 200, 400 ) ); //whatever size you want
        JOptionPane.showMessageDialog(null,
                scrollPane,
                "Contents:",
                JOptionPane.PLAIN_MESSAGE);
    }

    void displayFIle( ) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(f));
        ArrayList<String> lines = new ArrayList<String>();

        String line;
        while((line= br.readLine()) !=null) {
            lines.add(line+"\n");
        }
        initialized(lines);
    }

    void getLinesForWord() throws IOException {

        //First, ask the user for word
        String msg = "Enter the word to look for: ";
        String word = JOptionPane.showInputDialog(msg);

        if(word != null) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            LineNumberReader lnr = new LineNumberReader( br);
            ArrayList<String> lines = new ArrayList<String>();
            String line;
            while((line= lnr.readLine()) !=null) {
                if(line.contains(word)) {
                    lines.add("line "+lnr.getLineNumber()+": "+line+"\n");
                }
            }
            initialized(lines);
        }
    }

    void TokenizerStream() throws IOException {

       
        FileReader r = new FileReader(f);
        StreamTokenizer st = new StreamTokenizer(r);


        st.eolIsSignificant(true);
        st.wordChars('@','@');
        st.wordChars('@','@');
        st.wordChars(',',',');
        st.wordChars('!','!');
        st.lowerCaseMode(true);

        while(  st.nextToken() !=StreamTokenizer.TT_EOF) {

            switch (st.ttype){

                case StreamTokenizer.TT_WORD:
                    System.out.println(st.sval);
                    break;
                case StreamTokenizer.TT_NUMBER:
                    System.out.println(st.nval);
                    break;
                case StreamTokenizer.TT_EOL:
                    System.out.println("\tNew Line ++ > " + st.sval + (char) st.ttype);
                    break;
                    default:
                        System.out.println((char) st.ttype + " __> not recognized");
            }

        }
    }
}//end of program
