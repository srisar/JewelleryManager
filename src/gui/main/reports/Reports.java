/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.main.reports;

import core.controller.MainController;
import core.object.Pawn;
import core.object.User;
import core.utilities.NumberManager;
import gui.pawining.PawnRecordPanel;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Saravana
 */
public class Reports extends javax.swing.JFrame {

    private MainController mc;
    ArrayList<Pawn> resultPawnList;
    DefaultListModel<Pawn> pawnListModel;
    private User activeUser;

    /**
     * Creates new form Reports
     */
    public Reports() {
        initComponents();
    }

    public Reports(MainController mainController, User user) {
        this();

        mc = mainController;
        activeUser = user;
        
        //generate details
        generateDetails();
    }

    public void display() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    /*
     ====================================================================================================================
     ====================================================================================================================
     */

    private void loadSelectedPawn() {
        if (lstResults.getSelectedValue() != null) {
            PawnRecordPanel pawnRecordPanel = new PawnRecordPanel(mc, (Pawn) lstResults.getSelectedValue(), activeUser);
            pawnRecordPanel.display();
        }
    }

    private void populateResultList() {

        pawnListModel = new DefaultListModel<>();

        if (resultPawnList != null) {
            for (Pawn p : resultPawnList) {
                pawnListModel.addElement(p);
            }
            lstResults.setModel(pawnListModel);
        }

        // calculate total amount
        double totalAmount = 0;
        for (Pawn p : resultPawnList) {
            totalAmount += p.getAmountPaid();
        }
        txtTotalAmount.setText(NumberManager.toString(totalAmount));

        // calculate total gold
        double totalGold = 0;
        for (Pawn p : resultPawnList) {
            totalGold += p.getNetWeight();
        }
        txtTotalGold.setText(NumberManager.toString(totalGold));

    }

    private void generateDetails() {
        ArrayList<Pawn> todayPawningList = mc.pawnController.getTodayPawns();
        txtCountTodayPawns.setText(todayPawningList.size() + "");
        
        ArrayList<Pawn> todayWithdrawalsList = mc.pawnController.getTodayWithdrawals();
        txtCountWithdrawals.setText(todayWithdrawalsList.size() + "");
        
        ArrayList<Pawn> todayExpiringlist = mc.pawnController.getTodayExpiringPawns();
        txtCountExpiring.setText(todayExpiringlist.size() + "");
        
        int totalPawnCount = mc.pawnController.getTableRowCount();
        txtTotalPawns.setText(NumberManager.toString(totalPawnCount));
        
        txtTotalActivePawns.setText(NumberManager.toString(mc.pawnController.getActivePawnCount()));
        txtTotalExpiredPawns.setText(NumberManager.toString(mc.pawnController.getExpiredPawnCount()));
        txtTotalReturnedPawns.setText(NumberManager.toString(mc.pawnController.getRecollectedPawnCount()));
        
    }

    /*
     ====================================================================================================================
     Hooker Methods
     ====================================================================================================================
     */
    private void todaysTotalPawning() {
        resultPawnList = mc.pawnController.getTodayPawns();
        populateResultList();
    }

    private void todaysTotalWithdrawal() {
        resultPawnList = mc.pawnController.getTodayWithdrawals();
        populateResultList();
    }

    private void todayExpiringPawns() {
        resultPawnList = mc.pawnController.getTodayExpiringPawns();
        populateResultList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnTodayTotalPawning = new javax.swing.JButton();
        btnTodayTotalWithdrawal = new javax.swing.JButton();
        btnTodayExpiredPawns = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstResults = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        txtTotalAmount = new javax.swing.JTextField();
        btnLoadSelectedPawn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTotalGold = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCountTodayPawns = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCountWithdrawals = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCountExpiring = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtTotalPawns = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTotalActivePawns = new javax.swing.JTextField();
        txtTotalReturnedPawns = new javax.swing.JTextField();
        txtTotalExpiredPawns = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reports");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Today's Reports"));

        btnTodayTotalPawning.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/chart-pie.png"))); // NOI18N
        btnTodayTotalPawning.setText("Show today's total pawning");
        btnTodayTotalPawning.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTodayTotalPawning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodayTotalPawningActionPerformed(evt);
            }
        });

        btnTodayTotalWithdrawal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/clear.png"))); // NOI18N
        btnTodayTotalWithdrawal.setText("Show today's total withdrawals");
        btnTodayTotalWithdrawal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTodayTotalWithdrawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodayTotalWithdrawalActionPerformed(evt);
            }
        });

        btnTodayExpiredPawns.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/clock.png"))); // NOI18N
        btnTodayExpiredPawns.setText("Show pawns expired today");
        btnTodayExpiredPawns.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTodayExpiredPawns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodayExpiredPawnsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTodayTotalPawning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTodayTotalWithdrawal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTodayExpiredPawns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTodayTotalPawning)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTodayTotalWithdrawal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTodayExpiredPawns)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));

        jScrollPane1.setViewportView(lstResults);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Total Amount in LKR");

        txtTotalAmount.setEditable(false);
        txtTotalAmount.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalAmount.setForeground(new java.awt.Color(255, 51, 51));

        btnLoadSelectedPawn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/receipt.png"))); // NOI18N
        btnLoadSelectedPawn.setText("Load selected record");
        btnLoadSelectedPawn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadSelectedPawnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Total Gold in Grams");

        txtTotalGold.setEditable(false);
        txtTotalGold.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalGold.setForeground(new java.awt.Color(204, 0, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLoadSelectedPawn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotalGold)
                            .addComponent(txtTotalAmount))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTotalGold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLoadSelectedPawn)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Today's Details"));

        jLabel3.setText("Total pawning");

        txtCountTodayPawns.setEditable(false);

        jLabel4.setText("Withdrawals");

        txtCountWithdrawals.setEditable(false);

        jLabel5.setText("Expiring");

        txtCountExpiring.setEditable(false);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/refresh.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCountTodayPawns)
                            .addComponent(txtCountWithdrawals)
                            .addComponent(txtCountExpiring))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCountTodayPawns)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCountWithdrawals)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCountExpiring)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRefresh)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Total Pawns in Database");

        txtTotalPawns.setEditable(false);

        jLabel7.setText("Total Active Pawns");

        jLabel8.setText("Total Recollected Pawns");

        jLabel9.setText("Total Expired Pawns");

        txtTotalActivePawns.setEditable(false);

        txtTotalReturnedPawns.setEditable(false);

        txtTotalExpiredPawns.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotalPawns)
                    .addComponent(txtTotalActivePawns)
                    .addComponent(txtTotalReturnedPawns)
                    .addComponent(txtTotalExpiredPawns))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTotalPawns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTotalActivePawns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTotalReturnedPawns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTotalExpiredPawns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTodayTotalPawningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodayTotalPawningActionPerformed
        // TODO add your handling code here:
        todaysTotalPawning();
    }//GEN-LAST:event_btnTodayTotalPawningActionPerformed

    private void btnTodayTotalWithdrawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodayTotalWithdrawalActionPerformed
        // TODO add your handling code here:
        todaysTotalWithdrawal();
    }//GEN-LAST:event_btnTodayTotalWithdrawalActionPerformed

    private void btnTodayExpiredPawnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodayExpiredPawnsActionPerformed
        // TODO add your handling code here:
        todayExpiringPawns();
    }//GEN-LAST:event_btnTodayExpiredPawnsActionPerformed

    private void btnLoadSelectedPawnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadSelectedPawnActionPerformed
        // TODO add your handling code here:
        loadSelectedPawn();
    }//GEN-LAST:event_btnLoadSelectedPawnActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        generateDetails();
    }//GEN-LAST:event_btnRefreshActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reports.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reports().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadSelectedPawn;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTodayExpiredPawns;
    private javax.swing.JButton btnTodayTotalPawning;
    private javax.swing.JButton btnTodayTotalWithdrawal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstResults;
    private javax.swing.JTextField txtCountExpiring;
    private javax.swing.JTextField txtCountTodayPawns;
    private javax.swing.JTextField txtCountWithdrawals;
    private javax.swing.JTextField txtTotalActivePawns;
    private javax.swing.JTextField txtTotalAmount;
    private javax.swing.JTextField txtTotalExpiredPawns;
    private javax.swing.JTextField txtTotalGold;
    private javax.swing.JTextField txtTotalPawns;
    private javax.swing.JTextField txtTotalReturnedPawns;
    // End of variables declaration//GEN-END:variables
}
