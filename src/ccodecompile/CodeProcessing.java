/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.nio.file.Files;

/**
 * All the verdicts of c codes are created by this class. But the process of 
 * this class made synchronized to avoid errors. It has a static variable named fileNumber which gives a new number every time for avoiding exceptions for overwriting existing running file.
 * @author      Enamul Hassan    <enamsustcse@gmail.com>
 * @version     1.0
 * @since       2014-03-15
 */
public class CodeProcessing {

    private static int fileNumber;
    /**
     * If the specified file is running then it kills the program and then 
     * deletes the file. 
     * @param file      the file to be deleted
     */
    public void deleteFile(File file) {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " + file);
        } catch (IOException ex) {
        }

        while (file.exists()) {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Gives a new file number to avoid creating same name file.
     * @return          new number of file
     */
    protected static int getNewFileNumber(){
        return fileNumber++;
    }
    
    /**
     * Sets the number of file created to zero.
     */
    protected static void setNewFileNumber(){
        fileNumber = 0;
    }
    /**
     * To compile a file, it creates a new object of CodeCompile class with the 
     * specified source code. Then creates an executable file path deleting if
     * the same name file is already exists. It waits for the thread to complete
     * the work. Then detects any compilation error occured or not.
     * @param fileCPP   the c source code file
     * @return          the output file in case of no compilation error, 
     *                  otherwise null
     */
    public File compileCode(File fileCPP) {
        CodeCompile codeCompiler = new CodeCompile(fileCPP);
        File fileEXE = new File(codeCompiler.file.toString().concat(".exe"));
        deleteFile(fileEXE);
        codeCompiler.compilerThread.start();
        try {
            codeCompiler.compilerThread.join();

        } catch (InterruptedException ex) {
        }
        if (codeCompiler.errorHas == true) {
            return null;
        }
        return fileEXE;
    }

    /**
     * To run a file, it creates a new object of codeRun class with the 
     * specified source code, input and output files. Then it waits for the 
     * thread to complete the work within the time, otherwise time limit 
     * exceeded is reported. Then detects any compilation error occured or not.
     * @param fileEXE       the executable program file
     * @param fileSTDIN     the input file
     * @param fileSTDOUT    the output file
     * @return              verdict code for the program
     *                      1 - Correct answer.
     *                      3 - Runtime error.
     *                      4 - Time limit exceeded.
     */
    public int RunProgram(File fileEXE, File fileSTDIN, File fileSTDOUT) {
       boolean timeLimitExceeded;
        CodeRun codeRunner = new CodeRun(fileEXE, fileSTDIN, fileSTDOUT);
        codeRunner.start();
        timeLimitExceeded = false;
        try {
            codeRunner.join(UserOutlook.timeLimit);
            sleep(100);
            if (codeRunner.isAlive() == true) {
                timeLimitExceeded = true;
                sleep(100);
            }
        } catch (InterruptedException ex) {
        }
        codeRunner.pr.destroy();
        deleteFile(fileEXE);
        
        if (timeLimitExceeded == true) {
            return 4;
        }
        if (codeRunner.runtimeError != 0) {
            return 3;
        }
        
        return 1;
    }

    /**
     * This method manages the compilation and running of the program. 
     * It returns an array of string size two containing the verdict short form 
     * and the verdict code if the output is not correct otherwise array of 
     * string size one containing the output file name.
     * @param fileCPP       the c source code file
     * @param fileSTDIN     the input file
     * @return              array of string size two containing the verdict 
     *                      short form and the verdict code if the output is not
     *                      correct otherwise array of string size one 
     *                      containing the output file name.
     */
    public synchronized String[] process(File fileCPP, File fileSTDIN) {
        File fileEXE = compileCode(fileCPP);
        if (fileEXE == null) {
            String returnArray[] = {"WA", "5"};
            return returnArray;
        }

        File fileSTDOUT = new File(String.format("output%d.txt", getNewFileNumber()));
        deleteFile(fileSTDOUT);
        
        int verdictCode = RunProgram(fileEXE, fileSTDIN, fileSTDOUT);
        deleteFile(fileEXE);

        if (verdictCode == 4) {
            String returnArray[] = {"TL", "4"};
            return returnArray;

        } 
        if (verdictCode == 3) {
            String returnArray[] = {"RE", "3"};
            return returnArray;
        }
        
        String returnArray[] = {fileSTDOUT.toString()};
        return returnArray;
    }
}
