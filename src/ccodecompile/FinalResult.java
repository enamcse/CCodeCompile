/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ccodecompile;

/**
 * FinalResult class contains the whole verdicts of the c code files of the
 * students. It has five other method to query about the verdicts. The queries 
 * are comes mainly in writing the verdicts in file. It has some private
 * variables to keep the name and number of problems, number and registration 
 * number of the students and the result table. All of these variables are final
 * and initiated in the constructor.
 * @author      Enamul Hassan    <enamsustcse@gmail.com>
 * @version     1.0
 * @since       2014-03-19
 */
public class FinalResult {
    private final String[] regOfStudents;
    private final String[] nameOfProblems;
    private final int[][] verdictTable;
    private final int numberOfStudents;
    private final int numberOfProblems;
    /**
     * The constructor sets the all verdicts and the corresponding data.
     * @param regOfStudents     The array of registration number of the students
     *                          as string. The number of students is determined
     *                          by the size of this array.
     * @param nameOfProblems    The array of the name of the problems or tasks.
     *                          The number of assignment problems is determined
     *                          by the size of this array.
     * @param verdictTable      This two dimensional verdict array shows the
     *                          status of every assignment of every student.
     *                          there are 6 possible integer verdict in every 
     *                          element. The meaning of these are:
     *                          0 - File not found
     *                          1 - Output is correct.
     *                          2 - Output is wrong.                        
     *                          3 - Runtime error.                    
     *                          4 - Time limit exceeded.                    
     *                          5 - Compile error.                    
     */
    FinalResult(String[] regOfStudents,String[] nameOfProblems,int[][] verdictTable){
        this.regOfStudents = regOfStudents;
        this.nameOfProblems = nameOfProblems;
        this.verdictTable = verdictTable;
        this.numberOfStudents = regOfStudents.length;
        this.numberOfProblems = nameOfProblems.length;
    }
    /**
     * Returns the registration number of the specified student.
     * @param studentNo     the serial number of the specified student 
     * @return              the registration number of the corresponding student
     */
    public String getStudentReg(int studentNo){
        return regOfStudents[studentNo];
    }
    
    /**
     * Returns the name of the specified problem/assignment.
     * @param problemNo     the serial number of the specified problem
     * @return              the name of the specified problem/assignment
     */
    public String getProblemName(int problemNo){
        return nameOfProblems[problemNo].substring(0, nameOfProblems[problemNo].length()-4);
    }
    
    /**
     * Returns the number of students in the list.
     * @return              the number of students in the list.
     */
    public int getNumberOfStudents(){
        return numberOfStudents;
    }
    
    /**
     * Returns the number of problems in assignment list.
     * @return              the number of problems in assignment list.
     */
    public int getNumberOfProblems(){
        return numberOfProblems;
    }
    
    /**
     * Returns the verdict of a specified problem of a specified student.
     * @param studentNo the serial number of the student
     * @param problemNo the specific problem/assignment number
     * @return 
     */
    public int getResult(int studentNo, int problemNo){
        return verdictTable[studentNo][problemNo];
    }
}
