/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Enamul
 */
public class FileMatching {

    BufferedReader input1;
    BufferedReader input2;

    /**
     * 
     * @param file1
     * @param file2
     * @return 
     */
    public boolean process(File file1, File file2) {
        String line1, line2;
        try {
            input1 = new BufferedReader(new FileReader(file1));
            input2 = new BufferedReader(new FileReader(file2));
            line1 = input1.readLine();
            line2 = input2.readLine();
            while (line1 != null && line2 != null) {
                if (!line1.equals(line2)) {
                    input1.close();
                    input2.close();
                    return false;
                }
                line1 = input1.readLine();
                line2 = input2.readLine();
            }
            if (line1 != null || line2 != null) {
                input1.close();
                input2.close();
                return false;
            }
            input1.close();
            input2.close();
            return true;

        } catch (IOException ex) {
//            Logger.getLogger(FileMatching.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
