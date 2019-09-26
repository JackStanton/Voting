package trojan.GUI;

import trojan.xml.GenerateXML;
import trojan.xml.ReadXMLFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ResultWindow extends JFrame {

    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_POS_X = 600;
    private static final int WINDOW_POS_Y = 200;

    public ResultWindow(){
        setSize( WINDOW_WIDTH,WINDOW_HEIGHT);
        setLocation(WINDOW_POS_X,WINDOW_POS_Y);
        setTitle("Result");
        setResizable(false);


        JPanel mainPanel = new JPanel(new GridLayout(ChooseWindow.resultList.size(),1));
        JButton btnSave = new JButton("Save");

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ReadXMLFile.list.size() == ChooseWindow.resultList.size()){
                    JFileChooser chooser = new JFileChooser();
                    chooser.showSaveDialog(new JFileChooser());
                    File saveFile = chooser.getSelectedFile();
                    GenerateXML.execute(saveFile);
                }else{
                    new InformWindow(1);
                }
            }
        });


        if (ChooseWindow.resultList.size() > 0){
            for (int i = 0; i < ChooseWindow.resultList.size(); i++) {
                JLabel label = new JLabel();
                String username = ChooseWindow.resultList.get(i).getUsername();
                int level = ChooseWindow.resultList.get(i).getLevel();
                String choice = ChooseWindow.resultList.get(i).getChoose()?"yes":"no";
                label.setText("Level: " + level + " Username: " + username + " Choice: " + choice);
                mainPanel.add(label,BorderLayout.NORTH);
            }
        } else {
            JLabel label = new JLabel();
            label.setText("No user has made a choice");
            mainPanel.add(label,BorderLayout.NORTH);
        }


        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.NORTH);
        add(btnSave, BorderLayout.SOUTH);
        setVisible(true);
    }
}
