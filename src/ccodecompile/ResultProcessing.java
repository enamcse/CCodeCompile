/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ccodecompile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * ResultProcessing class writes the .csv file showing all the verdicts of the
 * assignments of all students in the directed directory. It also shows the 
 * overall assignment completed by a student in percentage. This class contains
 * only one private variable of FinalResult type. which contains the whole 
 * result of the assignments which is initiated in the constructor.
 * @author      Enamul Hassan    <enamsustcse@gmail.com>
 * @version     1.0
 * @since       2014-03-15
 */
public class ResultProcessing {
    
    private FinalResult finalResult;
    
    /**
     * The constructor initializes the whole result in the form of an object of
     * FinalResult class.
     * @param finalResult   It contains all the information of the students as 
     *                      well as the problem and verdicts of every assignment
     *                      of every student.
     */
    public ResultProcessing(FinalResult finalResult){
        this.finalResult = finalResult;
    }
    
    /**
     * If the .csv file exists in the specified path then deletes the previous 
     * one and creates an new instance of csv file to the specified path.
     * @param csvFile       The specified path where the file is to be created.
     */
    private void createFile(File csvFile){
        try {
            Files.deleteIfExists(csvFile.toPath());
            csvFile.createNewFile();
        } catch (IOException ex) {
//            Logger.getLogger(ResultProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This is the method which writes the summary of the whole process. It 
     * writes having the column serial number of a student i.e. 1,2,3,..., The
     * registration number, sequentially name of the problems (in the first 
     * column) and the verdict of the problem of the corresponding student(in
     * rest columns). There is a last column which shows the assignment 
     * completed of the corresponding student in percentage. The verdicts can be
     * any of the following 6 types. They are:
     * A - File not found
     * AC - Output is correct.
     * WA - Output is wrong.                        
     * RE - Runtime error.                    
     * TLE - Time limit exceeded.                    
     * CE - Compile error. 
     * @param csvFile       The specified path where the file is to be created.
     */
    private void writeResult(File csvFile){
        try {
            try (FileWriter writer = new FileWriter(csvFile)) {
                writer.write("No.,Registration Number,");
                int numberOfProblems = finalResult.getNumberOfProblems();
                int numberOfStudents = finalResult.getNumberOfStudents();
                for(int i = 0; i<numberOfProblems;i++){
                    writer.write(String.format("%s,", finalResult.getProblemName(i)));
                }   writer.write(String.format("Assignment Completed in percentage\n"));
            int verdictCode, assignmentCount;
                double percentage;
                for(int i = 0; i<numberOfStudents;i++){
                    writer.write(String.format("%d,%s,",i+1,finalResult.getStudentReg(i)));
                    assignmentCount = 0;
                    for(int j = 0; j<numberOfProblems;j++){
                        verdictCode = finalResult.getResult(i,j);
                        if(verdictCode==0)
                            writer.write(String.format("A,"));
                        else if(verdictCode==1){
                            writer.write(String.format("AC,"));
                            assignmentCount++;
                        }
                        else if(verdictCode==2)
                            writer.write(String.format("WA,"));
                        else if(verdictCode==3)
                            writer.write(String.format("RE,"));
                        else if(verdictCode==4)
                            writer.write(String.format("TLE,"));
                        else if(verdictCode==5)
                            writer.write(String.format("CE,"));
                    }
                    percentage = (((double)assignmentCount * 100.0 )/(double)numberOfProblems);
                    writer.write(String.format("%.2f\n", percentage));
                }
            }
        } catch (IOException ex) {
//            Logger.getLogger(ResultProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method writes the summary creating a file named Result.csv in the 
     * specified directory.
     * @param csvFile       The specified directory where the file is to be created.
     */
    public File process(File csvFile){
        csvFile = new File(csvFile.toString()+"\\Result.csv");
//        System.out.println(csvFile.toString());
        createFile(csvFile);
        writeResult(csvFile);
        return csvFile;
    }
}
