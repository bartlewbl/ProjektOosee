import javax.swing.*;
import java.awt.event.*;
import java.nio.file.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.zip.ZipException;

public class MyMainWindow extends JFrame implements IReporter {
    int i = 0;
    SpielDialog simSpielDialog;
    Tabelle tabelle = new Tabelle(MyMainWindow.this);
    private GuiThreadDecoupler _guiThreadDecoupl;
    JTextArea jTextAreaTabelle;
   

    public MyMainWindow (String _name){
        super(_name);
        
        jTextAreaTabelle = new JTextArea ("Verein                        |  Punkte  |  Tore  |  Gegentore   |   Anzahl Spiele  |",1,1);
        jTextAreaTabelle.setEditable(false);
        JPanel leerPanel = new JPanel();
        JPanel tabelleButtonPanel = new JPanel();

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menuDatei,menuTabelle;
        menuBar.add(menuDatei = new JMenu("Datei"));
        menuBar.add(menuTabelle = new JMenu("Tabelle"));

        JMenuItem menuSichern, menuLaden, menuRestart;
        menuDatei.add(menuSichern = new JMenuItem ("Sichern"));
        menuDatei.add(menuLaden = new JMenuItem ("Laden"));
        menuDatei.add(menuRestart = new JMenuItem ("Restart"));

        JMenuItem menuReset,menuNeuesTeam,menuDelete,menuShow;
        menuTabelle.add(menuShow = new JMenuItem ("Aktualisiere Tabelle"));
        menuTabelle.add(menuNeuesTeam = new JMenuItem("Neues Team"));
        menuTabelle.add(menuDelete = new JMenuItem("Lösche Team"));
        menuTabelle.add(menuReset = new JMenuItem ("Reset Teams"));
        
        JButton starteButton = new JButton ("Neues Spiel");
        JButton deleteButton = new JButton ("Lösche Team");
        JButton addButton = new JButton ("Neues Team");
        tabelleButtonPanel.add(starteButton);
        tabelleButtonPanel.add(addButton);
        tabelleButtonPanel.add(deleteButton);
        
        JScrollPane sp = new JScrollPane(jTextAreaTabelle);
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
        
        menuRestart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Runnable runAction = new Runnable() {
                    Tabelle tab = new Tabelle(MyMainWindow.this);
                
                    public void run(){
                        tabelle = tab;
                        tabelle.getTeamsToTabelle();
                    }
                };
                _guiThreadDecoupl = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl.startActionExecution();
                
            }
        });
        
        
        menuReset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Runnable runAction = new Runnable() {
                    public void run(){
                        tabelle.resetAlleTeams();
                        tabelle.sortiereVereine();
                        tabelle.getTeamsToTabelle();
                    }
                };
                _guiThreadDecoupl = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl.startActionExecution();
                
            }
        });
        menuNeuesTeam.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                
                NeuesTeamDialog addDialog = new NeuesTeamDialog(MyMainWindow.this,"Neues Team");
                Verein c = addDialog.getLetztesVerein();
                String cc =c.getNameTeam();
                int ccc = cc.length();
                Runnable runAction = new Runnable() {
                    public void run(){
                        if (ccc <= 20){
                            tabelle.addTeam(addDialog.getLetztesVerein());
                            tabelle.sortiereVereine();
                            tabelle.getTeamsToTabelle();
                        }
                    }
                };
                _guiThreadDecoupl = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl.startActionExecution();
                addDialog.dispose();
            }

        });
        menuShow.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                Runnable runAction = new Runnable() {
                    public void run(){
                        tabelle.sortiereVereine();
                        tabelle.getTeamsToTabelle();
                    }
                };
                _guiThreadDecoupl = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl.startActionExecution();
            }

        });

        addButton.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                
                NeuesTeamDialog addDialog = new NeuesTeamDialog(MyMainWindow.this,"Neues Team");
                Verein c = addDialog.getLetztesVerein();
                String cc =c.getNameTeam();
                int ccc = cc.length();
                Runnable runAction = new Runnable() {
                    public void run(){
                        if (ccc <= 20){
                            tabelle.addTeam(addDialog.getLetztesVerein());
                            tabelle.sortiereVereine();
                            tabelle.getTeamsToTabelle();
                        }
                    }
                };
                _guiThreadDecoupl = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl.startActionExecution();
                addDialog.dispose();
            }

        });

        starteButton.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                jTextAreaTabelle.setText("");
                simSpielDialog = new SpielDialog(MyMainWindow.this, "Spiel Simulation",tabelle,tabelle.getTeamArray());
                
            }

        });

        deleteButton.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                tabelle.sortiereVereine();
                LoscheTeamDialog deleteDialog = new LoscheTeamDialog (MyMainWindow.this,"Lösche Team",tabelle.getTeamArray());
                Runnable runAction = new Runnable() {
                    public void run(){
                        int z = deleteDialog.getGewahltesItemIndex();
                        Verein ver = tabelle.getTeam(z);
                        tabelle.removeTeam(ver);
                        tabelle.sortiereVereine();
                        tabelle.getTeamsToTabelle();
                        deleteDialog.dispose();
                    }
                };
                _guiThreadDecoupl = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl.startActionExecution();
                
                //tabelle.sortiereVereine();
                deleteDialog.dispose();
            }

        });
        menuDelete.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                tabelle.sortiereVereine();
                LoscheTeamDialog deleteDialog = new LoscheTeamDialog (MyMainWindow.this,"Lösche Team",tabelle.getTeamArray());
                Runnable runAction = new Runnable() {
                    public void run(){
                        int z = deleteDialog.getGewahltesItemIndex();
                        Verein ver = tabelle.getTeam(z);
                        tabelle.removeTeam(ver);
                        tabelle.sortiereVereine();
                        tabelle.getTeamsToTabelle();
                        deleteDialog.dispose();
                    }
                };
                _guiThreadDecoupl = new GuiThreadDecoupler(runAction);
                _guiThreadDecoupl.startActionExecution();
                
                //tabelle.sortiereVereine();
                deleteDialog.dispose();
            }

        });

        

        this.add(sp,java.awt.BorderLayout.CENTER);
        this.add(leerPanel,java.awt.BorderLayout.WEST);
        this.add(tabelleButtonPanel,java.awt.BorderLayout.SOUTH);

        setSize(500,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void giveNewMessage (String str){
        _guiThreadDecoupl.startGuiUpdate(new Runnable (){
            public void run(){
                jTextAreaTabelle.setText(jTextAreaTabelle.getText()+"\n"+ str);
            }
        },100);
    }
    public void resetTabelle (){
        jTextAreaTabelle.setText("Verein                        |  Punkte  |  Tore  |  Gegentore   |   Anzahl Spiele  |");
    }
    



    public static void main (String []args){
        MyMainWindow myWindow = new MyMainWindow("Füßballmanager");

    }
 
}
