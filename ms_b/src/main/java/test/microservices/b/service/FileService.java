package test.microservices.b.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * By Oreste Luci
 */
@Service
public class FileService {

    public String readFile() {

        BufferedReader br = null;
        StringBuffer content = new StringBuffer();

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("C:\\Users\\Oluci\\Temp\\large_file.txt"));

            while ((sCurrentLine = br.readLine()) != null) {
                content.append(sCurrentLine).append("\n");
                //System.out.println(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return content.toString();
    }
}
