/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ccodecompile;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * File browser class is created for choosing of a directory by user with more 
 * interaction. It opens a new window to browse the desired folder and choose.
 * @author      Enamul Hassan    <enamsustcse@gmail.com>
 * @version     1.0
 * @since       2014-03-15
 */
public class FileBrowser  {
    JFileChooser fileChooser;
    /**
     * It sets all the specifications of a new window to browse folder and opens
     * it on the screen to choose the desired folder of the user.
     * @param mode  Sets the types of files that the JFileChooser can choose.
     *              enum:
     *              FILES_ONLY JFileChooser.FILES_ONLY
     *              DIRECTORIES_ONLY JFileChooser.DIRECTORIES_ONLY
     *              FILES_AND_DIRECTORIES JFileChooser.FILES_AND_DIRECTORIES
     */
    public FileBrowser(int mode){
        fileChooser = new JFileChooser();
        fileChooser.setVisible(true);
        fileChooser.setFileSelectionMode(mode);
        fileChooser.setDialogTitle("Choose the desired file or folder");
        fileChooser.showDialog(null, "Select");
        if(mode==JFileChooser.FILES_ONLY){
            FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "PNG","jpeg", "png");
            fileChooser.addChoosableFileFilter(filter);
        }
    }
    
    /**
     * Returns the path of the directory as string that user chose.
     * @return          the path of the directory which is chosen by user
     */
    public String selectedFileName(){
        return fileChooser.getSelectedFile().toString();
    }
}
