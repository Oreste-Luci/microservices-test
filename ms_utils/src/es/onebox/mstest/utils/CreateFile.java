package es.onebox.mstest.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * By Oreste Luci
 */
public class CreateFile {

    public static void main(String[] args) {

        try {
            File file = new File("large_file.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            String content = "aaaaaaaaaaa bbbbbbbbbbbbb ccccccccccccc dddddddddddd eeeeeeeeeeeeeee fffffffffffffffff gggggggggggggg hhhhhhhhhhhhhhhhh iiiiiiiiiiiii jjjjjjjjjjjjjjj kkkkkkkk";
            String line;
            for (int i=0;i<10000000;i++) {
                line = (i+1)+ ". " + content;
                System.out.println(line);
                bw.write(line);
                bw.newLine();
            }

            bw.close();

            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
