package trojan.GUI;

import trojan.xml.ReadXMLFile;
import trojan.xml.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChooseWindow extends JFrame {

    public static ArrayList<Result> resultList = new ArrayList<>();

    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_HEIGHT = 150;
    private static final int WINDOW_POS_X = 600;
    private static final int WINDOW_POS_Y = 320;
    private static boolean result = false;


    public ChooseWindow(int level, String username, String mode){

        setTitle("Choose");
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setLocation(WINDOW_POS_X,WINDOW_POS_Y);
        setResizable(false);


        JButton btnYes = new JButton();
        JButton btnNo = new JButton();
        JButton btnOk = new JButton();

        btnOk.setText("Ok");
        btnNo.setText("Dont sign");
        btnYes.setText("Sign");
        btnYes.setPreferredSize(btnNo.getPreferredSize());


        btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = true;
                choose(username,level,true,mode);
                if (checkFinish()){
                    MainWindow.btnOpen.setVisible(true);
                    new InformWindow(3);
                }
                setVisible(false);

            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);

            }
        });

        btnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = true;
                choose(username,level,false,mode);
                if (checkFinish()){
                    MainWindow.btnOpen.setVisible(true);
                    new InformWindow(3);
                }
                setVisible(false);

            }
        });

        JPanel mainGrid = new JPanel(new GridLayout(2,1));
        JPanel labelGrid = new JPanel();
        JPanel buttonGrid = new JPanel();

        JLabel label = new JLabel();

        label.setText("Level: "+ level + " Username: "+ username +". Choose your answer:");

        if(searchList(username,level)){
            label.setText("You have already made your choice");
            labelGrid.add(label);
            buttonGrid.add(btnOk);
            mainGrid.add(labelGrid);
            mainGrid.add(buttonGrid);

        }else{
            labelGrid.add(label);
            buttonGrid.add(btnYes);
            buttonGrid.add(btnNo);
            mainGrid.add(labelGrid);
            mainGrid.add(buttonGrid);
        }
        add(mainGrid);
        setVisible(true);
    }

    private void choose(String username, int level, boolean choose, String mode){
        Result result = new Result(level,username,choose,mode);
        resultList.add(result);
    }

    private boolean searchList(String username,int level){
        for (int i = 0; i < resultList.size(); i++) {
            if ((resultList.get(i).getUsername() == username)&&(resultList.get(i).getLevel() == level)){
                return true;
            }
        }
        return false;
    }

    public static boolean generateObj(int level, String username, String mode){
        new ChooseWindow(level, username, mode);

        return result;
    }

    public static boolean checkFinish(){
        return (resultList.size() == ReadXMLFile.list.size());
    }
}
