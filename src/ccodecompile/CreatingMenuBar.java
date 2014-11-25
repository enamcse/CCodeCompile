/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ccodecompile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * In the new version, This class creates menu bar and it handles UserOutlook 
 * Class. This class basically creates a menu bar and handles their activities.
 * @author      Enamul Hassan    <enamsustcse@gmail.com>
 * @version     1.2
 * @since       2014-03-25
 */
public class CreatingMenuBar {

    UserOutlook mainFrame = new UserOutlook();
    SettingOption settingOption = new SettingOption(mainFrame);
    HowToUse howToWork = new HowToUse();
    AboutAuthor aboutAuthor = new AboutAuthor();
    AboutVersion aboutVersion = new AboutVersion();
    public final ImageIcon about1 = new ImageIcon(getClass().getResource("about1.png"));
    public final ImageIcon about2 = new ImageIcon(getClass().getResource("about2.png"));
    public final ImageIcon about3 = new ImageIcon(getClass().getResource("about3.png"));
    public final ImageIcon about4 = new ImageIcon(getClass().getResource("about4.png"));
    public final ImageIcon howToUse = new ImageIcon(getClass().getResource("howTo.png"));
    public final ImageIcon version = new ImageIcon(getClass().getResource("version.png"));

    /**
     * Initializes all frames.
     */
    public CreatingMenuBar() {
        mainFrame.setContentPane(createContentPane());
        mainFrame.setJMenuBar(createMenuBar());
        mainFrame.initializer();
        mainFrame.setVisible(true);
        settingOption.setContentPane(createContentPane());
        settingOption.setJMenuBar(createMenuBar());
        settingOption.initializer();
        howToWork.setContentPane(createContentPane());
        howToWork.setJMenuBar(createMenuBar());
        howToWork.initializer();
        aboutAuthor.setContentPane(createContentPane());
        aboutAuthor.setJMenuBar(createMenuBar());
        aboutAuthor.initializer();
        aboutVersion.setContentPane(createContentPane());
        aboutVersion.setJMenuBar(createMenuBar());
        aboutVersion.initializer();
    }
    
    /**
     * It creates the content pane and returns the JPanel
     * @return      fully prepared content pane
     */
    public JPanel createContentPane() {
        JPanel contentSetter = new JPanel();
        contentSetter.setBackground(Color.LIGHT_GRAY);
        contentSetter.setMinimumSize(new Dimension(300, 200));
        contentSetter.setPreferredSize(new Dimension(300, 200));
        contentSetter.setMaximumSize(new Dimension(300, 200));
        contentSetter.setOpaque(true);
        return contentSetter;
    }
    /**
     * It sets the options for the menu bar and then return.
     * @return      fully prepared menu bar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();


        JMenu mainMenu = new JMenu("Menu");
        JMenu viewOption = new JMenu("View");
        JMenu helpSection = new JMenu("Help");

        menuBar.add(mainMenu);
        menuBar.add(viewOption);
        menuBar.add(helpSection);

        JMenuItem setting = new JMenuItem("Setting");
        JMenuItem mainProgram = new JMenuItem("Main Program");
        JMenuItem exitButton = new JMenuItem("Exit");
        mainProgram.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                hideAll();
                mainFrame.setVisible(true);
            }
        });
        setting.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                hideAll();
                settingOption.setVisible(true);
            }
        });

        mainMenu.add(mainProgram);
        mainMenu.add(setting);
        mainMenu.addSeparator();
        mainMenu.add(exitButton);
        exitButton.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        final JMenuItem openResultFile = new JMenuItem("Open Result.csv file");
        viewOption.addMouseListener(new MouseAdapter() {
            @Override
            /**
             * {@inheritDoc}
             */
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    if (mainFrame.csvFile.exists()) {
                        openResultFile.setEnabled(true);
                    }
                } catch (Exception e) {
                }
            }
        });

        openResultFile.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                Runtime rt = Runtime.getRuntime();
                try {
                    Process pr = rt.exec("cmd /c " + mainFrame.csvFile.toString());
                } catch (IOException ex) {
                }
            }
        });

        viewOption.add(openResultFile);
        openResultFile.setEnabled(false);
        JMenuItem howTo = new JMenuItem("How to use");
        JMenu aboutProgram = new JMenu("About");
        JMenuItem author = new JMenuItem("Author");
        JMenuItem version = new JMenuItem("Version");
        aboutProgram.add(author);
        aboutProgram.add(version);

        helpSection.add(howTo);
        helpSection.add(aboutProgram);

        howTo.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                hideAll();
                howToWork.setVisible(true);
            }
        });
        author.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                hideAll();
                aboutAuthor.setVisible(true);
            }
        });
        version.addActionListener(new ActionListener() {

            @Override
            /**
             * {@inheritDoc}
             */
            public void actionPerformed(ActionEvent e) {
                hideAll();
                aboutVersion.setVisible(true);
            }
        });

        return menuBar;
    }
    /**
     * This method hides all frames.
     */
    public void hideAll(){
        mainFrame.setVisible(false);
        howToWork.setVisible(false);
        aboutAuthor.setVisible(false);
        aboutVersion.setVisible(false);
        settingOption.setVisible(false);
    }
    /**
     * This class handles how to use the software page.
     */
    public class HowToUse extends JFrame {
        /**
         * Initializes title.
         */
        public HowToUse() {
            super("How To Use");
            setVisible(false);
        }
        JButton okButton = new JButton("ok");
        
        /**
         * Initializes frame settings.
         */
        public void initializer() {
            setContentPane(new JLabel(howToUse));
            setResizable(false);
            setSize(960, 540);
            setBounds(180, 120, 960, 540);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            setVisible(false);
            okButton.addActionListener(new ActionListener() {

                @Override
                /**
                 * {@inheritDoc}
                 */
                public void actionPerformed(ActionEvent e) {
                    hideAll();
                    mainFrame.setVisible(true);
                }
            });
            okButton.setBounds(840, 440, 80, 30);
            add(okButton);
        }
    }
    
    /**
     * This class handles About version page.
     */
    public class AboutVersion extends JFrame {
        /**
         * Initializes title.
         */
        public AboutVersion() {
            super("About Version");
            setVisible(false);
        }
        JButton okButton = new JButton("ok");
        
        /**
         * Initializes frame settings.
         */
        public void initializer() {
            setContentPane(new JLabel(version));
            setResizable(false);
            setSize(720, 540);
            setBounds(180, 120, 720, 540);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            setVisible(false);
            okButton.addActionListener(new ActionListener() {

                @Override
                /**
                 * {@inheritDoc}
                 */
                public void actionPerformed(ActionEvent e) {
                    hideAll();
                    mainFrame.setVisible(true);
                }
            });
            okButton.setBounds(580, 440, 80, 30);
            add(okButton);
        }
    }
    
    /**
     * This class handles About Author page.
     */
    public class AboutAuthor extends JFrame {
        /**
         * Initializes title.
         */
        public AboutAuthor() {
            super("About Author");
            setVisible(false);
        }
        JButton nextButton = new JButton("Next");
        JButton prevButton = new JButton("Previous");
        JButton exitButton = new JButton("Back");
        ImageIcon nowShowing = about1;
        
        /**
         * Reinitializes frame settings.
         */
        public void reInitializer() {
            setContentPane(new JLabel(nowShowing));
            setResizable(false);
            setSize(960, 560);
            setBounds(180, 120, 960, 560);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            add(nextButton);
            add(prevButton);
            add(exitButton);
        }
        
        /**
         * Initializes frame settings.
         */
        public void initializer() {
            nowShowing = about1;
            setContentPane(new JLabel(nowShowing));

            setResizable(false);
            setSize(960, 560);
            setBounds(180, 120, 960, 560);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            setVisible(false);
            prevButton.setEnabled(false);
            nextButton.addActionListener(new ActionListener() {

                @Override
                /**
                 * {@inheritDoc}
                 */
                public void actionPerformed(ActionEvent e) {
                    if (nowShowing == about1) {
                        nowShowing = about2;
                        prevButton.setEnabled(true);
                        reInitializer();
                        setVisible(true);
                    } else if (nowShowing == about2) {
                        nowShowing = about3;
                        reInitializer();
                        setVisible(true);
                    } else if (nowShowing == about3) {
                        nowShowing = about4;
                        nextButton.setEnabled(false);
                        reInitializer();
                        setVisible(true);
                    }
                }
            });
            nextButton.setBounds(540, 10, 100, 30);
            add(nextButton);
            prevButton.addActionListener(new ActionListener() {

                @Override
                /**
                 * {@inheritDoc}
                 */
                public void actionPerformed(ActionEvent e) {
                    if (nowShowing == about4) {
                        nowShowing = about3;
                        nextButton.setEnabled(true);
                        reInitializer();
                        setVisible(true);
                    } else if (nowShowing == about3) {
                        nowShowing = about2;
                        reInitializer();
                        setVisible(true);
                    } else if (nowShowing == about2) {
                        nowShowing = about1;
                        prevButton.setEnabled(false);
                        reInitializer();
                        setVisible(true);
                    }
                }
            });
            prevButton.setBounds(320, 10, 100, 30);
            add(prevButton);

            exitButton.addActionListener(new ActionListener() {

                @Override
                /**
                 * {@inheritDoc}
                 */
                public void actionPerformed(ActionEvent e) {
                    hideAll();
                    mainFrame.setVisible(true);
                    nowShowing = about1;
                }
            });

            exitButton.setBounds(430, 10, 100, 30);
            add(exitButton);
        }
    }

}
