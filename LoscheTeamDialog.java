import javax.swing.*;
import java.awt.event.*;
import java.nio.file.*;
import java.io.*;
import java.nio.file.Paths;



public class LoscheTeamDialog extends JDialog {
    String gewahltesItem;
    int gewahltesItemIndex;
    public LoscheTeamDialog (JFrame parentFrame,String title,Verein[]copyArray){
        super(parentFrame,title);

        Verein[]loscheArray = copyArray;
        String[]loscheStringArray = new String [loscheArray.length];

        for (int i = 0; i<loscheStringArray.length;i++){
            loscheStringArray[i] = loscheArray[i].getNameTeam();
        }

        JComboBox cboLosche = new JComboBox(loscheStringArray);


        JPanel panelButton = new JPanel();

        JButton losche = new JButton("Lösche");
        JButton cancelButton = new JButton("Cancel");

        panelButton.add(losche);
        panelButton.add(cancelButton);
        


        losche.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                gewahltesItem = (String)cboLosche.getSelectedItem();
                gewahltesItemIndex = cboLosche.getSelectedIndex();
                System.out.println(gewahltesItemIndex);
                
            }

        });






        cancelButton.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                
                dispose();
            }

        });


        this.add(panelButton,java.awt.BorderLayout.SOUTH);
        this.add(cboLosche,java.awt.BorderLayout.CENTER);

        setLocationRelativeTo(parentFrame);
        setModal(true);
        setSize(350,150);
        setVisible(true);
    }
    public String getGewahltesItem (){
        return gewahltesItem;
    }
    public int getGewahltesItemIndex (){
        return gewahltesItemIndex;
    }
}
