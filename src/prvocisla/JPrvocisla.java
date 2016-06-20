package prvocisla;

//@author Ľudovít "Luigi" Kováč
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPrvocisla extends javax.swing.JFrame {

    int dolnaHranicaIntervalu = 0;
    int hornaHranicaIntervalu = 0;
    int pocetPrvocisel = 0;
    int pocetPrvociselA = 0;
    int pocetPrvociselB = 0;
    int dolnaA, hornaA, dolnaB, hornaB;
    boolean exc = false;
    ArrayList<Integer> zoznamPrvocisel = new ArrayList<>();
    ArrayList<Integer> zoznamPrvociselA = new ArrayList<>();
    ArrayList<Integer> zoznamPrvociselB = new ArrayList<>();

    public void noveHladanie() {

        jTextArea.setText("");
        jTextAreaP.setText("");
        jProgressBar1.setValue(0);
        jProgressBar2.setValue(0);
        jTextFieldDolnaHranicaIntervalu.setText("0");
        jTextFieldHornaHranicaIntervalu.setText("");
        dolnaHranicaIntervalu = 0;
        hornaHranicaIntervalu = 0;
        pocetPrvocisel = 0;
        pocetPrvociselA = 0;
        pocetPrvociselB = 0;
        exc = false;
        zoznamPrvocisel.removeAll(zoznamPrvocisel);
        zoznamPrvociselA.removeAll(zoznamPrvociselA);
        zoznamPrvociselB.removeAll(zoznamPrvociselB);
    }

    public void nacitajVstup() {

        try {

            dolnaHranicaIntervalu = Integer.parseInt(jTextFieldDolnaHranicaIntervalu.getText());             // vstup (dolná hranica intervalu)
            hornaHranicaIntervalu = Integer.parseInt(jTextFieldHornaHranicaIntervalu.getText());             // vstup (horná hranica intervalu)

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(rootPane, "Zle zadaný vstup");

            noveHladanie();
            exc = true;

        }

        if (hornaHranicaIntervalu < dolnaHranicaIntervalu) {

            JOptionPane.showMessageDialog(null, "Nepovolený interval !!!");

            noveHladanie();
            exc = true;
        }

    }

    public void rozdelInterval() {

        if (hornaHranicaIntervalu == dolnaHranicaIntervalu || hornaHranicaIntervalu == dolnaHranicaIntervalu + 1
                || hornaHranicaIntervalu == dolnaHranicaIntervalu + 2) {

            dolnaA = dolnaHranicaIntervalu;
            hornaA = hornaHranicaIntervalu;
            dolnaB = 0;
            hornaB = 0;

            System.out.println("dolnaA:" + dolnaA + " až hornaA:" + hornaA);
            System.out.println("dolnaB:" + dolnaB + " až  hornaB:" + hornaB);

        } else {

            dolnaA = dolnaHranicaIntervalu;

            double vypocet = (Math.ceil(((double) hornaHranicaIntervalu - (double) dolnaHranicaIntervalu))) * 0.7; // cca 70% z intervalu vlákno 1, cca 30% vlákno 2

            hornaA = (int) vypocet + dolnaHranicaIntervalu;

            dolnaB = hornaA + 1;

            hornaB = hornaHranicaIntervalu;

            System.out.println("dolnaA:" + dolnaA + " až hornaA:" + hornaA);

            System.out.println("dolnaB:" + dolnaB + " až  hornaB:" + hornaB);

        }

    }

    public void jednovlaknovyVypocet() {

        for (int x = dolnaHranicaIntervalu; x <= hornaHranicaIntervalu; x++) {

            if (x == 2) {
                pocetPrvocisel++;
                zoznamPrvocisel.add(x);

                continue;
            } else if (x % 2 == 0) {

                continue;
            }
            if (x == 0 || x == 1) {

                continue;
            }
            int pocitadlo = 0;
            for (int y = x - 1; y != 1; y--) {
                if ((x % y) == 0) {
                    break;
                } else {

                    pocitadlo++;
                }
                if (pocitadlo == x - 2) {
                    zoznamPrvocisel.add(x);
                    pocetPrvocisel++;
                }
            }

        }
        jTextArea.append("Vlákno našlo v zadanom intervale " + pocetPrvocisel + " prvočísel \n");
        for (int i = 0; i < zoznamPrvocisel.size(); i++) {
            int prvocislo = zoznamPrvocisel.get(i);
            if (i == zoznamPrvocisel.size() - 1) {
                jTextAreaP.append(prvocislo + "");
                break;
            }
            jTextAreaP.append(prvocislo + ", ");
        }

    }

    public void dvojvlaknovyVypocet() throws InterruptedException {

        class Vlakno1 extends Thread {

            Vlakno1() {

            }

            @Override
            public void run() {

                for (int x = dolnaA; x <= hornaA; x++) {

                    if (x == 2) {
                        zoznamPrvociselA.add(x);
                        pocetPrvociselA++;

                        continue;

                    } else if (x % 2 == 0) {

                        continue;
                    }

                    if (x == 0 || x == 1) {

                        continue;

                    }

                    int pocitadlo = 0;
                    for (int y = x - 1; y != 1; y--) {

                        if ((x % y) == 0) {

                            break;

                        } else {

                            pocitadlo++;

                        }

                        if (pocitadlo == x - 2) {
                            zoznamPrvociselA.add(x);
                            pocetPrvociselA++;

                        }
                    }

                }

            }
        }

        class Vlakno2 extends Thread {

            Vlakno2() {
            }

            @Override
            public void run() {

                for (int x = dolnaB; x <= hornaB; x++) {

                    if (x == 2) {
                        zoznamPrvociselB.add(x);
                        pocetPrvociselB++;

                        continue;

                    } else if (x % 2 == 0) {

                        continue;
                    }

                    if (x == 0 || x == 1) {

                        continue;

                    }

                    int pocitadlo = 0;
                    for (int y = x - 1; y != 1; y--) {

                        if ((x % y) == 0) {

                            break;

                        } else {

                            pocitadlo++;

                        }

                        if (pocitadlo == x - 2) {
                            zoznamPrvociselB.add(x);
                            pocetPrvociselB++;

                        }
                    }

                }

            }
        }

        Vlakno1 v1 = new Vlakno1();
        Vlakno2 v2 = new Vlakno2();

        v1.start();
        v2.start();

        v1.join();
        v2.join();

        jTextArea.append("Vlákno 1 našlo v žiadanom intervale " + pocetPrvociselA + " prvočísel \n");
        jTextArea.append("Vlákno 2 našlo v žiadanom intervale " + pocetPrvociselB + " prvočísel \n");
        jTextArea.append("Spolu bolo nájdených " + (pocetPrvociselA + pocetPrvociselB) + " prvočísel");

        zoznamPrvociselA.addAll(zoznamPrvociselB);

        for (int i = 0; i < zoznamPrvociselA.size(); i++) {
            int prvocislo = zoznamPrvociselA.get(i);
            if (i == zoznamPrvociselA.size() - 1) {
                jTextAreaP.append(prvocislo + "");
                break;
            }
            jTextAreaP.append(prvocislo + ", ");
        }
    }

    public JPrvocisla() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelDolnaHranicaIntervalu = new javax.swing.JLabel();
        jLabelHornaHranicaIntervalu1 = new javax.swing.JLabel();
        jTextFieldDolnaHranicaIntervalu = new javax.swing.JTextField();
        jTextFieldHornaHranicaIntervalu = new javax.swing.JTextField();
        jCheckBoxDveVlakna = new javax.swing.JCheckBox();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jProgressBar2 = new javax.swing.JProgressBar();
        jButtonNoveHladanie = new javax.swing.JButton();
        jButtonPocitajPrvocisla = new javax.swing.JButton();
        jLabelNazovProgramu = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaP = new javax.swing.JTextArea();
        jLabelNajdenePrvocisla = new javax.swing.JLabel();
        jLabelVlakno1P = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelDolnaHranicaIntervalu.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabelDolnaHranicaIntervalu.setText("DOLNÁ HRANICA INTERVALU");

        jLabelHornaHranicaIntervalu1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabelHornaHranicaIntervalu1.setText("HORNÁ HRANICA INTERVALU");

        jTextFieldDolnaHranicaIntervalu.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jTextFieldDolnaHranicaIntervalu.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldDolnaHranicaIntervalu.setText("0");
        jTextFieldDolnaHranicaIntervalu.setAutoscrolls(false);

        jTextFieldHornaHranicaIntervalu.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jTextFieldHornaHranicaIntervalu.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldHornaHranicaIntervalu.setAutoscrolls(false);

        jCheckBoxDveVlakna.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jCheckBoxDveVlakna.setSelected(true);
        jCheckBoxDveVlakna.setText("POUŽIŤ DVE VLÁKNA");
        jCheckBoxDveVlakna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxDveVlaknaActionPerformed(evt);
            }
        });

        jTextArea.setEditable(false);
        jTextArea.setColumns(20);
        jTextArea.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jTextArea.setLineWrap(true);
        jTextArea.setRows(5);
        jTextArea.setAutoscrolls(false);
        jScrollPane1.setViewportView(jTextArea);

        jButtonNoveHladanie.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButtonNoveHladanie.setText("NOVÉ HĽADANIE");
        jButtonNoveHladanie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNoveHladanieActionPerformed(evt);
            }
        });

        jButtonPocitajPrvocisla.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jButtonPocitajPrvocisla.setText("HĽADAJ PRVOČÍSLA");
        jButtonPocitajPrvocisla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPocitajPrvocislaActionPerformed(evt);
            }
        });

        jLabelNazovProgramu.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabelNazovProgramu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNazovProgramu.setText("HĽADANIE PRVOČÍSEL V ZADANOM INTERVALE");

        jLabel1.setText("Created by Ľudovít \"Luigi\" Kováč");

        jTextAreaP.setEditable(false);
        jTextAreaP.setColumns(20);
        jTextAreaP.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jTextAreaP.setLineWrap(true);
        jTextAreaP.setRows(5);
        jScrollPane2.setViewportView(jTextAreaP);

        jLabelNajdenePrvocisla.setText("Nájdené prvočísla :");

        jLabelVlakno1P.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPocitajPrvocisla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelVlakno1P, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextFieldDolnaHranicaIntervalu)
                                            .addComponent(jLabelDolnaHranicaIntervalu))
                                        .addGap(36, 36, 36)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabelHornaHranicaIntervalu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextFieldHornaHranicaIntervalu, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(218, 218, 218)
                                        .addComponent(jCheckBoxDveVlakna)))
                                .addGap(316, 316, 316))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addComponent(jButtonNoveHladanie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNazovProgramu, javax.swing.GroupLayout.DEFAULT_SIZE, 1342, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNajdenePrvocisla)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelNazovProgramu)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDolnaHranicaIntervalu)
                    .addComponent(jLabelHornaHranicaIntervalu1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDolnaHranicaIntervalu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldHornaHranicaIntervalu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBoxDveVlakna)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPocitajPrvocisla, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelNajdenePrvocisla)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNoveHladanie, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelVlakno1P, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxDveVlaknaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxDveVlaknaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxDveVlaknaActionPerformed

    private void jButtonNoveHladanieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNoveHladanieActionPerformed

        noveHladanie();

    }//GEN-LAST:event_jButtonNoveHladanieActionPerformed

    private void jButtonPocitajPrvocislaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPocitajPrvocislaActionPerformed

        if (!"".equals(jTextArea.getText())) {

            noveHladanie();

        } else {

            nacitajVstup();

            if (exc == false) {

                if (jCheckBoxDveVlakna.isSelected()) {

                    rozdelInterval();
                    try {
                        dvojvlaknovyVypocet();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(JPrvocisla.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    jednovlaknovyVypocet();

                }

            } else {

                noveHladanie();

            }
        }

    }//GEN-LAST:event_jButtonPocitajPrvocislaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {


        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JPrvocisla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JPrvocisla().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNoveHladanie;
    private javax.swing.JButton jButtonPocitajPrvocisla;
    private javax.swing.JCheckBox jCheckBoxDveVlakna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDolnaHranicaIntervalu;
    private javax.swing.JLabel jLabelHornaHranicaIntervalu1;
    private javax.swing.JLabel jLabelNajdenePrvocisla;
    private javax.swing.JLabel jLabelNazovProgramu;
    private javax.swing.JLabel jLabelVlakno1P;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea;
    private javax.swing.JTextArea jTextAreaP;
    private javax.swing.JTextField jTextFieldDolnaHranicaIntervalu;
    private javax.swing.JTextField jTextFieldHornaHranicaIntervalu;
    // End of variables declaration//GEN-END:variables
}
