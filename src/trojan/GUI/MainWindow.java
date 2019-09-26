package trojan.GUI;

import trojan.xml.GenerateXML;
import trojan.xml.ReadXMLFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_POS_X = 600;
    private static final int WINDOW_POS_Y = 200;
    private static int indexOfList = 0;
    private static int listSize = 0;
    private static File fileName;
    public static JButton btnOpen = new JButton();


    public int generateBtn(JPanel gridUser){
        int countOfButton = 0;
        for (int i = 0; i < listSize; i++) {
            if(ReadXMLFile.list.get(i).getLevel() == ReadXMLFile.list.get(indexOfList).getLevel()){
                int level = ReadXMLFile.list.get(i).getLevel();
                String username = ReadXMLFile.list.get(i).getUserName();
                String mode = ReadXMLFile.list.get(i).getMode();
                JButton button = new JButton();
                button.setText(ReadXMLFile.list.get(i).getUserName());
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       ChooseWindow.generateObj(level,username,mode);
                    }
                });
                gridUser.add(button);
                countOfButton++;
            }


        }
        return countOfButton;
    }

    public MainWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize( WINDOW_WIDTH,WINDOW_HEIGHT);
        setLocation(WINDOW_POS_X,WINDOW_POS_Y);
        setTitle("Program");
        setResizable(false);


        JFileChooser chooser = new JFileChooser();
        JButton btnStart = new JButton();

        JButton btnNext = new JButton();
        JButton btnShowResult = new JButton();
        JLabel label = new JLabel();
        JPanel gridMain = new JPanel( new GridLayout(4,1));
        JPanel grid = new JPanel( new GridLayout(1,2) );
        JPanel gridUser = new JPanel(new GridLayout(5,1));

        btnShowResult.setText("Show progress");
        btnShowResult.setEnabled(false);
        btnOpen.setText("Open");
        btnStart.setText("Start");
        btnStart.setEnabled(false);
        btnNext.setText("Next");
        btnNext.setEnabled(false);

        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if((ChooseWindow.checkFinish())&&(ReadXMLFile.list.size() != 0)){
////                    gridUser.removeAll();
////                    label.setText("");
////                    repaint();
//                    new InformWindow(4);
//                }else{
                    fileName = new File("");

                    String message;
                    chooser.showOpenDialog(new JFileChooser());
                    fileName = chooser.getSelectedFile();
                    if (fileName != null){
                        String string = chooser.getSelectedFile().toString();
                        string = string.substring(string.lastIndexOf('.')+1,string.length());
                        if (string.equals("xml")){
                            message = "Selected file ( " + chooser.getSelectedFile() + " )";
                            btnOpen.setEnabled(false);
                            btnStart.setEnabled(true);
                        } else message = "Selected file is not xml";
                    } else{
                        message = "File not selected";
                    }
                    JOptionPane.showMessageDialog(MainWindow.this, message);
                    indexOfList = 0;
                }

//            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridUser.removeAll();
                ReadXMLFile.list.clear();
                ChooseWindow.resultList.clear();
                ReadXMLFile.execute(String.valueOf(fileName));
                listSize = ReadXMLFile.list.size();
                if(listSize > 0){
                    label.setText("Level: "+ReadXMLFile.list.get(indexOfList).getLevel());
                    indexOfList+=generateBtn(gridUser);
                    btnStart.setEnabled(false);
                    btnNext.setEnabled(true);
                    btnShowResult.setEnabled(true);
                } else {
                    String message = "Selected file ( " + chooser.getSelectedFile() + " ) is not valid xml";
                    JOptionPane.showMessageDialog(MainWindow.this, message);
                    btnOpen.setEnabled(true);
                    btnStart.setEnabled(false);
                }
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ChooseWindow.resultList.size() == indexOfList){
                    gridUser.removeAll();
                    gridUser.repaint();
                    label.setText("Level: "+ReadXMLFile.list.get(indexOfList).getLevel());
                    indexOfList+=generateBtn(gridUser);
                    if (!(indexOfList < listSize)) {
                        btnNext.setEnabled(false);
                        btnOpen.setEnabled(true);
                        btnShowResult.setEnabled(true);
                    }
                } else {
                    new InformWindow(2);
                }
            }
        });

        btnShowResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                chooser.showSaveDialog(new JFileChooser());
//                File saveFile = chooser.getSelectedFile();
//                GenerateXML.execute(saveFile);
                new ResultWindow();
//                btnShowResult.setEnabled(false);
            }
        });


        grid.add(btnOpen);
        grid.add(btnStart);
        gridMain.add(grid);
        add(gridUser, BorderLayout.SOUTH);
        gridMain.add(btnNext);
        gridMain.add(btnShowResult);
        gridMain.add(label);
        add(gridMain, BorderLayout.NORTH);

        setVisible(true);

    }


}
