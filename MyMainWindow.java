import javax.swing.*;
import java.awt.event.*;
import java.nio.file.*;
import java.io.*;
import java.nio.file.Paths;

public class MyMainWindow extends JFrame {
    public MyMainWindow (String _name){
        super(_name);
        Tabelle tabelle = new Tabelle();


        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menuDatei,menuTabelle;
        menuBar.add(menuDatei = new JMenu("Datei"));
        menuBar.add(menuTabelle = new JMenu("Tabelle"));

        JMenuItem menuSichern, menuLaden;
        menuDatei.add(menuSichern = new JMenuItem ("Sichern"));
        menuDatei.add(menuLaden = new JMenuItem ("Laden"));

        JMenuItem menuReset,menuNeuesTeam;
        menuTabelle.add(menuNeuesTeam = new JMenuItem("Neues Team"));
        menuTabelle.add(menuReset = new JMenuItem ("Reset"));
        


        menuSichern.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                System.out.println("Sichern gedrückt");
                File f = tabelle.getFilePath();
                JFileChooser fileDialog = new JFileChooser();
                fileDialog.setCurrentDirectory(tabelle.getFilePath());
                int result = fileDialog.showSaveDialog(MyMainWindow.this);
                if (result == JFileChooser.APPROVE_OPTION){
                    f = fileDialog.getSelectedFile();
                    tabelle.speichernDatei(f);
                }
            }
        });

        menuLaden.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                System.out.println("Laden gedrückt");
                JFileChooser fileDialog = new JFileChooser();
                fileDialog.setCurrentDirectory(tabelle.getFilePath());
                int result = fileDialog.showSaveDialog(MyMainWindow.this);
                if (result == JFileChooser.APPROVE_OPTION){
                    File f = fileDialog.getSelectedFile();
                    tabelle.ladenDatei(f);
                }
            }
        });
        menuReset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                tabelle.resetAlleTeams();
            }
        });
        menuNeuesTeam.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                NeuesTeamDialog addDialog = new NeuesTeamDialog("Hello");
                addDialog.setSize(500,300);
                addDialog.setVisible(true);
            }

        });




        setSize(500,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    

    public static void main (String []args){
        new MyMainWindow("Füßballmanager");
    }
 
}
