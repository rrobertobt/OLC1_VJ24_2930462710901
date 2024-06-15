package edu.robertob.olc1.vj24;

import edu.robertob.olc1.vj24.Analysis.AnalysisGenerator;
import edu.robertob.olc1.vj24.Frames.MainFrame;

public class OLC1VJ242930462710901 {

    public static void main(String[] args) {
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                System.out.println(info.getName());
//                if ("Metal".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        
        /*
            Crear un jfilechooser para abrir archivos
            
        */
        try {
//            AnalysisGenerator.generateCompiler();
            new MainFrame().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
