package trojan.GUI;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import trojan.xml.GenerateXML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class InformWindow extends JFrame {

    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_HEIGHT = 150;
    private static final int WINDOW_POS_X = 600;
    private static final int WINDOW_POS_Y = 320;


    public InformWindow(int flag){
        setTitle("Information");
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setLocation(WINDOW_POS_X,WINDOW_POS_Y);
        setResizable(false);


        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        JPanel labelPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        JButton btnYes = new JButton("Yes");
        JButton btnNo = new JButton("No");
        btnNo.setPreferredSize(btnYes.getPreferredSize());



        if ((flag == 1)||(flag == 4)){
            JLabel label = new JLabel();
            if (flag == 1){
                label.setText("The singing of the document is not yet complete. Are you sure want to save the result?");
            }
            if (flag == 4){
                label.setText("Are you want to save the result?");
            }
            btnYes.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    setVisible(false);
                    JFileChooser chooser = new JFileChooser();
                    chooser.showSaveDialog(new JFileChooser());
                    File saveFile = chooser.getSelectedFile();
                    GenerateXML.execute(saveFile);
                }
            });

            btnNo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    if (flag == 4){
                        ChooseWindow.resultList.clear();
                    }
                }
            });


            labelPanel.add(label, BorderLayout.NORTH);
            buttonPanel.add(btnYes,BorderLayout.SOUTH);
            buttonPanel.add(btnNo,BorderLayout.SOUTH);
            mainPanel.add(labelPanel, BorderLayout.NORTH);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        }

        if (flag == 2){
            JLabel label = new JLabel();
            JButton  btnOk = new JButton("Ok");
            label.setText("Level not finished");
            labelPanel.add(label, BorderLayout.NORTH);
            buttonPanel.add(btnOk,BorderLayout.SOUTH);
            mainPanel.add(labelPanel, BorderLayout.NORTH);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            btnOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });
        }


        if (flag == 3){
            JLabel label = new JLabel();
            JButton  btnOk = new JButton("Ok");
            String status = getDocumentStatus();

            label.setText("Document is " + status);
            labelPanel.add(label, BorderLayout.NORTH);
            buttonPanel.add(btnOk,BorderLayout.SOUTH);
            mainPanel.add(labelPanel, BorderLayout.NORTH);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            btnOk.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });
        }


        add(mainPanel);
        setVisible(true);
    }

    private static String getDocumentStatus(){
        int lvl = 0;
        boolean signed = true;
        for (int i = 0; i < ChooseWindow.resultList.size(); i++) {
            boolean choice = ChooseWindow.resultList.get(0).getChoose();
            boolean result = choice;
            if (lvl < ChooseWindow.resultList.get(i).getLevel()){
                lvl = ChooseWindow.resultList.get(i).getLevel();

                for (int j = 0; j < ChooseWindow.resultList.size(); j++) {
                    if (lvl == ChooseWindow.resultList.get(j).getLevel()){
                        choice = ChooseWindow.resultList.get(j).getChoose();
                        if (!(ChooseWindow.resultList.get(i).getMode().equals(""))){
                            if (ChooseWindow.resultList.get(i).getMode().equals("or")){
                                result = result || choice;
                            }
                            if (ChooseWindow.resultList.get(i).getMode().equals("and")){
                                result = result && choice;
                            }
                        } else result = ChooseWindow.resultList.get(i).getChoose();
                    }
                }
                signed = signed && result;
            }
        }
        return  (signed) ? "signed":"not signed";
    }

}
