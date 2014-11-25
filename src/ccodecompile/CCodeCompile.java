/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

/**
 * The work of this program is to calculate the statistics of assignment of a 
 * programming class which is based on c/cpp language. There are four field of 
 * the program for choosing input files, output files, students\' source code
 * folder and The directory where the statistics file to be created as .csv 
 * (comma separated value). This fields either can be edited manually or a 
 * browser button is also available to browse and choose the directory.
 * <p>
 * Input folder and output folder  contain only input files and with the same 
 * name corresponding output files as text format respectively. On the other 
 * hand, the student\'s source code folder contains several folders named after 
 * the registration numbers of the students. All of these students folder should
 * contain their program as the same name of the input file with the extention
 * .cpp. The number of assignment task(s) should be determined after the number 
 * of file(s) in the input folder and similarly the number of student(s) should 
 * be determined by the number of directory in source code folder.
 * <p>
 * The program is divided into several classes to simplify the work. This class
 * contains only the main class and in this only an object of UserOutlook class
 * is created and all these work is shifted to the constructor of UserOutlook 
 * class for keep the program more readable.
 * <p>
 * In the new version(1.2), the menu bar feature has been added. So, in the main 
 * function only creating an instance of the class CreatingMenuBar instead of 
 * UserOutlook class.UserOutlook is now handling by this CreatingMenuBar class.
 * 
 * @author      Enamul Hassan    <enamsustcse@gmail.com>
 * @version     1.0
 * @since       2014-02-15
 */
public class CCodeCompile {

    /**
     * @param   args    An optional parameter from the system. There is no 
     *                  effect of this parameter in the program. This is the
     *                  main method which is the only method called by the 
     *                  System. So, for making the program more readable, this 
     *                  class is kept neat and clean. Only the object of 
     *                  UserOutlook class is created. Further works will be done
     *                  there, in the constructor of the UserOutlook class.
     */
    public static void main(String[] args) {
        new CreatingMenuBar();
    }
}
