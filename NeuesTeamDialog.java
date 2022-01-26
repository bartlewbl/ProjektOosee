import javax.swing.*;
import java.awt.event.*;
import java.nio.file.*;
import java.io.*;
import java.nio.file.Paths;




public class NeuesTeamDialog extends JDialog {
    Verein zVerein;
    public NeuesTeamDialog (JFrame frameParent,String title){
        super(frameParent,title);
        

        JLabel jLabelKommu = new JLabel("Geben Sie bitte die Name ihres Vereins:");

        JTextArea jTextAreaTeamName = new JTextArea("0",1,1);

        JPanel buttonPanel = new JPanel ();

        JButton create = new JButton("Create");
        JButton cancel = new JButton("Cancel");

        buttonPanel.add(create);
        buttonPanel.add(cancel);

        create.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                String z = (jTextAreaTeamName.getText());
                zVerein = new Verein (z);
                dispose();
                
            }

        });
        cancel.addActionListener(new ActionListener (){
            public void actionPerformed(ActionEvent ae){
                
                dispose();
            }

        });
        

        this.add(jTextAreaTeamName,java.awt.BorderLayout.CENTER);
        this.add(buttonPanel,java.awt.BorderLayout.SOUTH);
        this.add(jLabelKommu,java.awt.BorderLayout.NORTH);

        setLocationRelativeTo(frameParent);
        setModal(true);
        setSize(300,150);
        setVisible(true);
    }
    public Verein getLetztesVerein (){
            
        return zVerein;
    }

}
