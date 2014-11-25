/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

import static ccodecompile.UserOutlook.setFonts;
import static ccodecompile.UserOutlook.timeLimit;
import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JTextField;

/**
 * Time limit is moved here in the 1.2 version and background setting is added
 * as an extra feature here.
 * @author      Enamul Hassan    <enamsustcse@gmail.com>
 * @version     1.2
 * @since       2014-03-25
 */
public class SettingOption extends JFrame {

    public final int horizontalStartingPoint = 200;
    public final int verticalStartingPoint = 50;
    public final int heightOfFrame = 338;
    public final int widthOfFrame = 631;
    public final int textFieldHeight = 50;
    public JLabel timeLimitLabel;
    public final Choice delayMinuteChooser = new Choice();
    public final Choice delaySecondChooser = new Choice();

    private int timeLimitMinute = 0;
    private int timeLimitSecond = 5;
    public final JLabel timeLimitMinuteLabel = new JLabel("Minute(s)");
    public final JLabel timeLimitSecondLabel = new JLabel("Second(s)");
    UserOutlook mainFrame;

    JButton browserButton;
    JButton selectButton;
    JTextField backgroundFile;

    public final ImageIcon background = new ImageIcon(getClass().getResource("setting.jpg"));
    /**
     * There are a lot of useful things remained in UserOutlook class. So, the
     * object of this class kept as a parameter.
     * @param mainFrame     an object of UserOutlook class
     */
    public SettingOption(UserOutlook mainFrame) {
        super("Settings");
        this.mainFrame = mainFrame;
        setVisible(false);
    }
    /**
     * It initializes the frame.
     */
    public void initializer() {
        setContentPane(new JLabel(background));

        setResizable(false);
        setSize(widthOfFrame, heightOfFrame);
        setBounds(320, 220, widthOfFrame, heightOfFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTimeLimitSettings("          Time Limit :");
        setBackground();
        setVisible(false);
    }
    
    /**
     * Setting of background.
     */
    public void setBackground() {

        browserButton = new JButton("...");
        selectButton = new JButton("ok");
        
        JLabel backgroundLabel = new JLabel("        Background :");
        backgroundFile = new JTextField("background.jpg", 40);

        backgroundLabel.setBounds(horizontalStartingPoint - 160, verticalStartingPoint + 70, 160, 30);
        backgroundFile.setBounds(horizontalStartingPoint, verticalStartingPoint + 75, 250, 25);
        browserButton.setBounds(horizontalStartingPoint + 255, verticalStartingPoint + 75, 25, 25);
        selectButton.setBounds(widthOfFrame/2 - 25, verticalStartingPoint + 155, 70, 25);

        setFonts(backgroundLabel, Color.orange, 20);
        mainFrame.addMouseResponse(backgroundFile);

        showInProgram(backgroundLabel);
        showInProgram(backgroundFile);
        showInProgram(browserButton);
        showInProgram(selectButton);
        
        selectButton.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                mainFrame.setVisible(true);
            }
        });
        
        browserButton.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                FileBrowser browser = new FileBrowser(JFileChooser.FILES_ONLY);
                try {
                    String fileName = browser.selectedFileName();

                    try {
                        File backgroundFilePath = new File("background.style");
                        backgroundFilePath.createNewFile();
                        try (FileWriter backgroundFileWriter = new FileWriter(backgroundFilePath)) {
                            backgroundFileWriter.write(fileName);

                        }
                    } catch (IOException ex) {

                    }
                    backgroundFile.setText(fileName);
                    mainFrame.background = new ImageIcon(fileName);
                    mainFrame.initializer();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Opps!!\n" + "You did not select new file.", "No file selected!", INFORMATION_MESSAGE, mainFrame.noDirectorySelectedIcon);
                }

            }
        });

    }

    /**
     * It handles the time limit chooser.
     */
    public class ItemResponse implements ItemListener {

        @Override
        /**
         * {@inheritDoc} It sets the time limit minutes and seconds.
         */
        public void itemStateChanged(ItemEvent event) {
            if (event.getSource() == delayMinuteChooser) {
                timeLimitMinute = delayMinuteChooser.getSelectedIndex();
            } else if (event.getSource() == delaySecondChooser) {
                timeLimitSecond = delaySecondChooser.getSelectedIndex();
            }

            timeLimit = (long) ((timeLimitMinute * 60) + timeLimitSecond) * (long) 1000;
        }

    }

    /**
     * set time limit settings
     *
     * @param barNumber It is a number which can be 0 to 4 for: 0 - c code
     * folder field 1 - input files folder field 2 - output files folder field 3
     * - result file folder field 4 - Time field
     * @param label the label of the bar
     */
    private void setTimeLimitSettings(String label) {
        timeLimitLabel = new JLabel(label);

        timeLimitLabel.setBounds(horizontalStartingPoint - 160, verticalStartingPoint, 160, 30);
        timeLimitMinuteLabel.setBounds(horizontalStartingPoint + 60, verticalStartingPoint, 85, 30);
        timeLimitSecondLabel.setBounds(horizontalStartingPoint + 210, verticalStartingPoint, 90, 30);
        delayMinuteChooser.setBounds(horizontalStartingPoint, verticalStartingPoint + 6, textFieldHeight, textFieldHeight);
        delaySecondChooser.setBounds(horizontalStartingPoint + 155, verticalStartingPoint + 6, textFieldHeight, textFieldHeight);

        setOptionsOfChooser();
        timeLimitSecondLabel.setBackground(Color.red);
        setFonts(timeLimitMinuteLabel, Color.BLUE, 20);
        setFonts(timeLimitSecondLabel, Color.WHITE, 20);
        setFonts(timeLimitLabel, Color.ORANGE, 20);

        showInProgram(timeLimitMinuteLabel);
        showInProgram(timeLimitSecondLabel);
        showInProgram(timeLimitLabel);
        showInProgram(delayMinuteChooser);
        showInProgram(delaySecondChooser);

        delayMinuteChooser.addItemListener(new ItemResponse());
        delaySecondChooser.addItemListener(new ItemResponse());
    }

    /**
     * Sets the options of time chooser.
     */
    public void setOptionsOfChooser() {
        for (int i = 0; i < 60; i++) {
            delayMinuteChooser.add(Integer.toString(i));
            delaySecondChooser.add(Integer.toString(i));
        }
        delaySecondChooser.select(5);
    }

    /**
     * add the object to the frame
     *
     * @param object object when JTextField
     */
    protected void showInProgram(JTextField object) {
        add(object);
    }

    /**
     * add the object to the frame
     *
     * @param object object when label
     */
    protected void showInProgram(JLabel object) {
        add(object);
    }

    /**
     * add the object to the frame
     *
     * @param object object when button
     */
    protected void showInProgram(JButton object) {
        add(object);
    }

    /**
     * add the object to the frame
     *
     * @param object object when choice
     */
    protected void showInProgram(Choice object) {
        add(object);
    }

}
