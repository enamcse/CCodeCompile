/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * This class manage whole process and decorates the window.
 * @author Enamul Hassan <enamsustcse@gmail.com>
 * @version 1.0
 * @since 2014-02-15
 */
public class UserOutlook extends InputHandler {
    /**
     * it is the static variable which is the time limit for a specific program
     */
    public static long timeLimit = 5000;
    
    
    /**
     * Constructor of the class.
     * Set all initialization
     */
    public UserOutlook() {
        super("C Code Output Matcher");
        setVisible(false);
    }
    
    public void initializer(){
        if(new File("background.style").exists()){
            Scanner backgroundFileStored = null;
            try {
                backgroundFileStored = new Scanner(new File("background.style"));
            } catch (FileNotFoundException ex) {
            }
            String fileName = backgroundFileStored.nextLine();
            background = new ImageIcon(fileName);
        }
        setContentPane(new JLabel(background));
        setFieldsSettings(4);
        setSubmitButtonAndLogo();
       
        setResizable(false);
        setSize(widthOfFrame, heightOfFrame);
        setBounds(300, 120, widthOfFrame, heightOfFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }
    
    /**
     * set submit button and logo
     */
    private void setSubmitButtonAndLogo() {
        JLabel logoLabel = new JLabel(logo);
        submitButton.setBounds(horizontalStartingPoint + textFieldWidth / 5 + 5, verticalStartingPoint + 6 * gapBetweenTwoButtons, textFieldWidth / 3, textFieldHeight);
        logoLabel.setBounds((widthOfFrame - logoSide) / 2, 20, logoSide, logoSide);
        addActionResponse(submitButton);
        showInProgram(submitButton);
        showInProgram(logoLabel);
    }
    /**
     * set Directory settings
     * @param barNumber It is a number which can be 0 to 4 for: 0 - c code
     * folder field 1 - input files folder field 2 - output files folder field 3
     * - result file folder field 4 - Time field
     * @param label     the label of the bar
     */
    private void DirectorySettings(int barNumber, String label) {
        folderPathLabel[barNumber] = new JLabel(label);
        DirectoryField[barNumber] = new JTextField("Enter directory path here...", 40);
    }

    

    /**
     * It set fonts.
     * @param object the label whose font to set.
     * @param colorConstant the desired foreground <code>Color</code>
     * @see java.awt.Component#getForeground
     * @beaninfo
     *    preferred: true
     *        bound: true
     *    attribute: visualUpdate true
     *  description: The foreground color of the component.
     * @param fontSize the point size of the Font
     */
    public static void setFonts(JLabel object, Color colorConstant, int fontSize) {
        object.setFont(new Font("Serif", Font.BOLD, fontSize));
        object.setBorder(null);
        object.setForeground(colorConstant);
    }

    /**
     * It sets all fields.
     * @param barNumber It is a number which can be 0 to 4 for: 0 - c code
     * folder field 1 - input files folder field 2 - output files folder field 3
     * - result file folder field 4 - Time field
     */
    private void setFieldsSettings(int barNumber) {

        String[] labelLines = {"     Cpp folder path:", "   Input folder path:", "Output folder path:", "Result folder path:", };

        for (int i = 0; i < barNumber; i++) {
            DirectorySettings(i, labelLines[i]);

            folderPathLabel[i].setBounds(horizontalStartingPoint - 130, verticalStartingPoint - 2 + i * gapBetweenTwoButtons - 3, 130, 30);
            browserButton[i] = new JButton("...");
            browserButton[i].setBounds(horizontalStartingPoint + textFieldWidth + 2, verticalStartingPoint + i * gapBetweenTwoButtons, textFieldHeight, textFieldHeight);
            DirectoryField[i].setBounds(horizontalStartingPoint, verticalStartingPoint + i * gapBetweenTwoButtons, textFieldWidth, textFieldHeight);
            setFonts(folderPathLabel[i], Color.WHITE,15);
            showInProgram(browserButton[i]);
            showInProgram(DirectoryField[i]);
            showInProgram(folderPathLabel[i]);
            addMouseResponse(DirectoryField[i]);
            addActionResponse(browserButton[i]);
        }
    }

    // all things will be processed later.
    @Override
    /**
     * {@inheritDoc}
     */
    protected void addMouseResponse(JTextField object) {
        object.addMouseListener(new MouseAdapterClass());
    }

    @Override
    /**
     * {@inheritDoc}
     */
    protected void addActionResponse(JButton object) {
        object.addActionListener(new InputHandlerClass());
    }

    /**
     * add the object to the frame
     * @param object object when text field
     */
    protected void showInProgram(JTextField object) {
        add(object);
    }

    /**
     * add the object to the frame
     * @param object object when label
     */
    protected void showInProgram(JLabel object) {
        add(object);
    }

    /**
     * add the object to the frame
     * @param object object when button
     */
    protected void showInProgram(JButton object) {
        add(object);
    }

    /**
     * add the object to the frame
     * @param object object when choice
     */
    protected void showInProgram(Choice object) {
        add(object);
    }
}
