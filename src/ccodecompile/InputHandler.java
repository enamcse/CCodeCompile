/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JTextField;

/**
 * When a button pressed, mouse clicked or other type of input is encoutered,
 * then this class handles it. It takes the necessary action. It is kept as an
 * abstract class for the relaxation. It extends the JFrame class to give the
 * opportunity to decorate the frame. It also handles time limits and other
 * frame and image constants.
 *
 * @author Enamul Hassan <enamsustcse@gmail.com>
 * @version 1.0
 * @since 2014-03-15
 */
public abstract class InputHandler extends JFrame {



    public FileBrowser browser;
    public JTextField DirectoryField[] = new JTextField[5];

    public final JButton submitButton = new JButton("Submit");
    public final JButton browserButton[] = new JButton[5];

    public File selectedPath[] = new File[5];
    public File csvFile;
    
    public final String folderPath[] = new String[5];

    public JLabel folderPathLabel[] = new JLabel[5];

    public final int horizontalStartingPoint = 220;
    public final int verticalStartingPoint = 180;
    public final int gapBetweenTwoButtons = 30;
    public final int heightOfFrame = 500;
    public final int textFieldHeight = 25;
    public final int widthOfFrame = 690;
    public final int textFieldWidth = 300;
    public final int logoSide = 137;

    public ImageIcon background = new ImageIcon(getClass().getResource("background.jpg"));
    public final ImageIcon yesIcon = new ImageIcon(getClass().getResource("YesIcon.png"));
    public final ImageIcon logo = new ImageIcon(getClass().getResource("cppLogo.png"));
    public final ImageIcon noDirectorySelectedIcon = new ImageIcon(getClass().getResource("noFileSelected.jpg"));
    public final ImageIcon processStarted = new ImageIcon(getClass().getResource("ProcessStarted.png"));

    /**
     * It sets the title of the frame.
     *
     * @param name The title of the frame
     */
    public InputHandler(String name) {
        super(name);
    }

    /**
     * It determines what is to be done if a mouse button is pressed on a field.
     *
     * @param object text field object where the mouse is pressed
     */
    protected abstract void addMouseResponse(JTextField object);

    /**
     * It sets all other action response.
     * @param object        a button which is pressed.
     */
    protected abstract void addActionResponse(JButton object);
    
    /**
     * It adds things.
     * @param <T>           for relaxation of parameter.
     * @param object        the item to be added.
     */
    protected <T> void showInProgram(T object) {
        add((Component) object);
    }

    /**
     *It sets all mouse response.
     */
    public class MouseAdapterClass extends MouseAdapter {

        @Override
        /**
         * {@inheritDoc} 
         * It sets the field empty when clicked.
         */
        public void mouseClicked(MouseEvent mouseEvent) {
            ((JTextField) mouseEvent.getSource()).setText("");
        }
    }

    

    /**
     * This class handles when a button is pressed then what to do. It also
     * process the result if submit button is pressed. Thats why an object of
     * AssignmentSimulator class is created.
     */
    protected class InputHandlerClass implements ActionListener {

        AssignmentSimulation simulator = new AssignmentSimulation();

        /**
         * It shows a massage when user opens the file browser but not select
         * any directory.
         */
        private void nothingSelectedAsDirectory() {
            JOptionPane.showMessageDialog(null, "Opps!!\n" + "You did not select new folder.", "No folder selected!", INFORMATION_MESSAGE, noDirectorySelectedIcon);
        }

        /**
         * This sets the string of path in text field and variable of the file
         * when selected.
         * @param barNumber It is a number which can be 0 to 3 for: 0 - c code
         * folder field 1 - input files folder field 2 - output files folder
         * field 3 - result file folder field
         */
        public void selectingPath(int barNumber) {
            browser = new FileBrowser(JFileChooser.DIRECTORIES_ONLY);
            try {
                folderPath[barNumber] = browser.selectedFileName();
                DirectoryField[barNumber].setText(folderPath[barNumber]);
            } catch (Exception e) {
                nothingSelectedAsDirectory();
            }
        }

        /**
         * This sets the string of path of the file when selected.
         *
         * @param barNumber It is a number which can be 0 to 3 for: 0 - c code
         * folder field 1 - input files folder field 2 - output files folder
         * field 3 - result file folder field
         */
        private void selectPath(int barNumber) {
            folderPath[barNumber] = DirectoryField[barNumber].getText();
        }

        @Override
        /**
         * {@inheritDoc} 
         * when a button is pressed then it takes action.
         */
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < 4; i++) {
                if (event.getSource() == browserButton[i]) {
                    selectingPath(i);
                    return;
                }
            }

            if (event.getSource() == submitButton) {
                
                submitButton.setText("Please wait...");
                submitButton.setEnabled(false);
                submitButton.setBounds(horizontalStartingPoint + textFieldWidth / 7, verticalStartingPoint + 6 * gapBetweenTwoButtons, textFieldWidth / 2, textFieldHeight);

                for (int i = 0; i < 4; i++) {
                    selectPath(i);
                }

                boolean allFilesFound = true;

                for (int i = 0; i < 4; i++) {
                    selectedPath[i] = new File(folderPath[i]);
                    if (selectedPath[i].exists() == false) {
                        allFilesFound = false;
                        break;
                    }
                }

                if (allFilesFound) {
                    JOptionPane.showMessageDialog(null, "System is ready to start processing.\nIt may take time in case of huge data.\nTo start, press OK and be patient.", "Confirmation", INFORMATION_MESSAGE, processStarted);

                    CodeProcessing.setNewFileNumber();
                    FinalResult finalResult = simulator.process(selectedPath[0], selectedPath[1], selectedPath[2]);
                    ResultProcessing resultProcess = new ResultProcessing(finalResult);
                    csvFile = resultProcess.process(selectedPath[3]);

                    int maxFileCreated = CodeProcessing.getNewFileNumber();

                    for (int i = 0; i < maxFileCreated; i++) {
                        deleteFile(new File(String.format("output%d.txt", i)));
                        deleteFile(new File(String.format("compiledFile%d.exe", i)));
                    }

                    JOptionPane.showMessageDialog(null, "Yes!\n" + "Result file successfully created!", "Success!", INFORMATION_MESSAGE, yesIcon);
                } else {
                    JOptionPane.showMessageDialog(null, String.format("Error:\nInvalid directory path(s) encountered!"), "Invalid Directory", INFORMATION_MESSAGE, noDirectorySelectedIcon);
                }

                submitButton.setText("Submit");
                submitButton.setEnabled(true);
                submitButton.setBounds(horizontalStartingPoint + textFieldWidth / 5 + 5, verticalStartingPoint + 6 * gapBetweenTwoButtons, textFieldWidth / 3, textFieldHeight);
            }
        }

    }

    /**
     * If the specified file is running then it kills the program and then
     * deletes the file.
     *
     * @param file the file to be deleted
     */
    public void deleteFile(File file) {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " + file);
        } catch (IOException ex) {
//            Logger.getLogger(CodeProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (file.exists()) {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException ex) {
//                Logger.getLogger(CodeProcessing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
