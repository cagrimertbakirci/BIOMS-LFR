/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package superserial;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 *
 * @author Carlton Johnson
 */
public class DisplayGraph extends javax.swing.JFrame {

    /**
     * Creates new form DisplayGraph
     * @param F The location of the graph you want displayed.
     */
    public DisplayGraph(File F, int graphType) {
        File img=null;
        initComponents();
        this.setVisible(true);
        methods m=new methods();
        String fileType=F.getName().substring(F.getName().lastIndexOf(".")+1);
        
        switch(graphType){
            case 0:
                img=m.averageAbsoluteFitness(F);
                break;
            case 1:
                img=m.bestAbsoluteFitness(F);
                break;
            case 2:
                img=m.averageRelativeFitness(F);
                break;
            case 3:
                img=m.bestRelativeFitness(F);
                break;
            case 4:
                
                break;
            case 5:
                
                break;
            case 6:
                
                break;
            default:
                img=null;
                this.setVisible(false);
                this.dispose();
        }
        BufferedImage draw = null;
        
        try {
            draw = ImageIO.read(img);
        } catch (IOException e) {
        }
        ImageIcon imageIcon=new ImageIcon(draw);
        jLabel1.setIcon(imageIcon);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Graph = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Graph)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(831, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Graph)
                .addContainerGap(535, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Graph;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
