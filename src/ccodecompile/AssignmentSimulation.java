/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class simulates the whole result from getting directory to write in
 * file.
 *
 * @author Enamul Hassan <enamsustcse@gmail.com>
 * @version 1.0
 * @since 2014-03-15
 */
public class AssignmentSimulation {

    /**
     * It returns the files under the directory.
     *
     * @param directory the directory whose files to be returned
     * @return files under the given directory
     */
    private File[] getFileLists(File directory) {
        return directory.listFiles();
    }

    /**
     * This returns assignment names.
     *
     * @param numberOfAssignment number of assignment found
     * @param assignmentPaths assignment file paths
     * @return assignment names
     */
    private String[] getAssignmentList(int numberOfAssignment, File[] assignmentPaths) {
        File[] assignmentFile = new File[numberOfAssignment];
        String[] assignmentName = new String[numberOfAssignment];
        for (int i = 0; i < numberOfAssignment; i++) {
            assignmentFile[i] = new File(assignmentPaths[i].getName());
            assignmentName[i] = assignmentFile[i].toString().substring(0, assignmentFile[i].toString().length() - 3) + "cpp";
        }
        return assignmentName;
    }

    /**
     * It returns student list
     *
     * @param numberOfStudents number of students
     * @param sourceFolders c source folders path
     * @return c source folder path as string
     */
    private String[] getStudentList(int numberOfStudents, File[] sourceFolders) {
        String[] regNumbers = new String[numberOfStudents];

        for (int i = 0; i < numberOfStudents; i++) {
            regNumbers[i] = sourceFolders[i].getName();
        }
        return regNumbers;
    }
    int[][] verdictTable;
    int threadRunning;

    /**
     * It examines all files and returns a verdict table.
     *
     * @param sourceDirectory c source folder path
     * @param inputFiles input folder path
     * @param outputFiles output folder path
     * @param regNumbers registration numbers of the students
     * @param sourceFileName c source file names
     * @param numberOfStudents number of students
     * @param numberOfProblems number of assignments
     * @return verdict table
     */
    private int[][] getExamined(File sourceDirectory, File[] inputFiles, File[] outputFiles, String[] regNumbers, String[] sourceFileName, int numberOfStudents, int numberOfProblems) {
        verdictTable = new int[numberOfStudents][numberOfProblems];
        VerdictGiver verd;
        threadRunning = 0;
        for (int i = 0; i < numberOfStudents; i++) {
            for (int j = 0; j < numberOfProblems; j++) {
                verdictTable[i][j] = 0;
                File sourceCode = new File(sourceDirectory.toString() + "\\" + regNumbers[i] + "\\" + sourceFileName[j]);
                if (sourceCode.exists()) {
                    verd = new VerdictGiver(sourceCode, inputFiles[j], outputFiles[j], i, j);
                    verd.verdictThread.start();
                    try {
                        verd.verdictThread.join();
                    } catch (InterruptedException ex) {
//                        Logger.getLogger(AssignmentSimulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return verdictTable;
    }

    /**
     *
     * @param sourceCodeFileDirectory c source folder path
     * @param inputFileDirectory input folder path
     * @param outputFileDirectory output folder path
     * @return result as an instance of FinalResult
     */
    public FinalResult process(File sourceCodeFileDirectory, File inputFileDirectory, File outputFileDirectory) {
        int numberOfProblems;
        int numberOfStudents;

        String sourceFileName[];
        String regNumbers[];

        File inputFiles[] = getFileLists(inputFileDirectory);
        File outputFiles[] = getFileLists(outputFileDirectory);
        File sourceFolders[] = getFileLists(sourceCodeFileDirectory);

        numberOfProblems = inputFiles.length;
        numberOfStudents = sourceFolders.length;

        sourceFileName = getAssignmentList(numberOfProblems, inputFiles);

        regNumbers = getStudentList(numberOfStudents, sourceFolders);
        return new FinalResult(regNumbers, sourceFileName, getExamined(sourceCodeFileDirectory, inputFiles, outputFiles, regNumbers, sourceFileName, numberOfStudents, numberOfProblems));
    }

    /**
     * It manages to give a verdict on the table with new thread.
     */
    public class VerdictGiver implements Runnable {

        private final CodeProcessing codeProcessor = new CodeProcessing();
        private final FileMatching matcher = new FileMatching();
        Thread verdictThread;
        private String[] userOUT;
        File fileCPP;
        File fileSTDIN;
        File fileSTDOUT;
        int i;
        int j;

        /**
         * If the specified file is running then it kills the program and then
         * deletes the file.
         *
         * @param file the file to be deleted
         */
        private void deleteFiles(File file) {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException ex) {
//                Logger.getLogger(AssignmentSimulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * 
         * @param fileCPP       source file path
         * @param fileSTDIN     input file path
         * @param fileSTDOUT    outout file path
         * @param i             student no
         * @param j             problem no
         */
        public VerdictGiver(File fileCPP, File fileSTDIN, File fileSTDOUT, int i, int j) {
            verdictThread = new Thread(this, "Verdict Thread");
            this.fileCPP = fileCPP;
            this.fileSTDIN = fileSTDIN;
            this.fileSTDOUT = fileSTDOUT;
            this.i = i;
            this.j = j;
        }

        @Override
        /**
         * process data and gives verdicts.
         */
        public void run() {

            userOUT = codeProcessor.process(fileCPP, fileSTDIN);

            if (userOUT.length <= 1) {
                if (matcher.process(new File(userOUT[0]), fileSTDOUT)) {
                    deleteFiles(new File(userOUT[0]));
                    verdictTable[i][j] = 1;
                } else {
                    deleteFiles(new File(userOUT[0]));
                    verdictTable[i][j] = 2;
                }
            } else {
                deleteFiles(new File(userOUT[0]));
                verdictTable[i][j] = Integer.parseInt(userOUT[1]);
//                System.out.println("here@here|"+Integer.parseInt(userOUT[1])+"|");
            }
            threadRunning--;
        }
    }
}
