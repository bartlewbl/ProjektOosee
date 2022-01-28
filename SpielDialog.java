import javax.swing.*;
import java.awt.event.*;
import java.nio.file.*;
import java.io.*;
import java.nio.file.Paths;


public class SpielDialog extends JDialog  {
    private GuiThreadDecoupler _guiThreadDecoupl1;
    
    
    Tabelle tabelle;
    public SpielDialog (JFrame parentFrame,String title,Tabelle tab,Verein[]copyArray){
        super(parentFrame,title);

        Verein[]spielArray = copyArray;
        String[]spielStringArray = new String [spielArray.length];

        for (int i = 0; i<spielStringArray.length;i++){
            spielStringArray[i] = spielArray[i].getNameTeam();
        }

        JComboBox cboWahlTeam1 = new JComboBox(spielStringArray);
        JComboBox cboWahlTeam2 = new JComboBox(spielStringArray);




        tabelle = tab;
        JPanel jPanelButton = new JPanel ();
        JLabel jLabelKommuWahl = new JLabel ("               Wähle 2 Teams für das Spiel:");
        JPanel wahlTeams = new JPanel();

        JButton buttonStarteSpiel = new JButton ("Starte Spiel");
        JButton buttonCancel = new JButton ("Cancel");

        wahlTeams.add(cboWahlTeam1);
        wahlTeams.add(cboWahlTeam2);
        

        jPanelButton.add(buttonStarteSpiel);
        jPanelButton.add(buttonCancel);


        buttonStarteSpiel.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                Runnable runAction = new Runnable() {
                    public void run(){
                        int wahlTeam1Index = cboWahlTeam1.getSelectedIndex();
                        int wahlTeam2Index = cboWahlTeam2.getSelectedIndex();
                        if (wahlTeam1Index != wahlTeam2Index){
                            tabelle.starteSpiel(tabelle,tabelle.getTeam(wahlTeam1Index),tabelle.getTeam(wahlTeam2Index));
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Du hast gleiche Vereins gewählt!","Fehlermeldung!",JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }
                };
                _guiThreadDecoupl1 = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl1.startActionExecution();
            }

        });
        buttonCancel.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                dispose();
            }

        });




        this.add(jLabelKommuWahl,java.awt.BorderLayout.NORTH);
        this.add(wahlTeams,java.awt.BorderLayout.CENTER);
        this.add(jPanelButton,java.awt.BorderLayout.SOUTH);
        

        setLocationRelativeTo(parentFrame);
        setModal(true);
        setSize(300,150);
        setVisible(true);
    }
    
}
