/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pawining;

import core.controller.MainController;
import core.info.Information;
import core.object.Pawn;
import core.object.User;
import core.utilities.DateManager;
import core.utilities.NumberManager;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Sri Saravana
 */
public class SearchPawns extends javax.swing.JFrame {

    private MainController controller;
    private ArrayList<Pawn> resultPawnList;
    private boolean isExpired, isActive, isRecollected;
    private int resultCount;
    private Pawn selectedPawn;
    private User activeUser;

    /**
     * Creates new form SearchPawns
     */
    public SearchPawns() {
        initComponents();
    }

    public SearchPawns(MainController controller, User user) {
        this();
        this.setTitle("Search : " + Information.APP_TITLE);
        this.controller = controller;
        this.activeUser = user;
        // set dates
        setDates();
    }

    public void display() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //
    //======================================================================================================================================
    //
    // this will set the initial dates for startDate and endDate fields
    // it is for convenient
    // startDate: will be first day of the current month
    // endDate:   will be today's date
    private void setDates() {
        dateStartDate.setDate(DateManager.getFirstDayOfCurrentMonth());
        dateEndDate.setDate(DateManager.getUtilToday());
    }

    // check which pawning status filter is selected
    // this is required so the correct query can be executed on server
    // to get the results
    private void checkPawningStatusFilter() {
        isActive = radioActivePawns.isSelected();
        isExpired = radioExpiredPawns.isSelected();
        isRecollected = radioRecollectedPawns.isSelected();
    }

    // this will populate the listbox with the results
    // 
    private void populateResultListBox() {

        if (resultPawnList != null) {
            DefaultListModel<Pawn> listModel = new DefaultListModel<>();

            for (Pawn p : resultPawnList) {
                listModel.addElement(p);
            }
            lstPawn.setModel(listModel);
        }

    }

    // generate results
    private void generateResults() {
        // get the total number of records
        resultCount = resultPawnList.size();

        // set the pagination label
        double totalAmount = 0.0;
        double totalWeight = 0.0;
        
        for(Pawn p : resultPawnList){
            totalAmount += p.getAmountPaid();
            totalWeight += p.getNetWeight();
        }
        lblTotalAmount.setText("Total: Rs. " + String.format("%,.2f", totalAmount));
        
        lblTotalWeight.setText("Weight: " + String.format("%,.2f", totalWeight) + " g");
        
        lblPagination.setText(resultCount + " Result(s) found.");

        // populate the list box - show the partial results according to 
        // the limit and offset value
        populateResultListBox();
    }

    private void searchByDates() {

        java.sql.Date startDate = DateManager.toSqlDate(dateStartDate.getDate());
        java.sql.Date endDate = DateManager.toSqlDate(dateEndDate.getDate());

        checkPawningStatusFilter();

        // populate currenPawnList with result
        resultPawnList = controller.pawnController.searchPawnByDates(startDate, endDate, isActive, isExpired, isRecollected);

        generateResults();

    }
    
    private void searchByRecollectionDate(){
        java.sql.Date startDate = DateManager.toSqlDate(dateStartRecollectionDate.getDate());
        java.sql.Date endDate = DateManager.toSqlDate(dateEndRecollectionDate.getDate());

        checkPawningStatusFilter();

        // populate currenPawnList with result
        resultPawnList = controller.pawnController.searchPawnByRecollectedDate(startDate, endDate, isActive, isExpired, isRecollected);

        generateResults();
    }

    private void searchByReceiptNumber() {

        if (!txtReceiptNumber.getText().isEmpty()) {
            int receiptNumber = NumberManager.toInt(txtReceiptNumber.getText());

            checkPawningStatusFilter();

            // populate currenPawnList with result
            resultPawnList = controller.pawnController.searchPawnByReceiptNumber(receiptNumber);

            generateResults();
        }
    }

    private void searchByFullName() {

        if (!txtFullName.getText().isEmpty()) {
            String fullName = txtFullName.getText();

            checkPawningStatusFilter();

            resultPawnList = controller.pawnController.searchPawnByFullName(fullName, isActive, isExpired, isRecollected);
            
            generateResults();
        }
    }
    
    
    private void searchByNICNumber(){
         if (!txtNicNumber.getText().isEmpty()) {
            String nicNumber = txtNicNumber.getText();

            checkPawningStatusFilter();

            resultPawnList = controller.pawnController.searchPawnByNIC(nicNumber);
            
            generateResults();
        }
    }

    // this will load the selected pawn from the result list box
    private void loadSelectedPawnDetails() {

        if (lstPawn.getSelectedIndex() >= 0) {
            selectedPawn = (Pawn) lstPawn.getSelectedValue();
        } else {
            selectedPawn = null;
        }

        if (selectedPawn != null) {
            PawnRecordPanel pawnRecord = new PawnRecordPanel(controller, selectedPawn, activeUser);
            pawnRecord.display();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a pawn from the result list box.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //
    //======================================================================================================================================
    //
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pawningStatusRadioGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstPawn = new javax.swing.JList();
        lblPagination = new javax.swing.JLabel();
        btnLoadSelectedPawn = new javax.swing.JButton();
        lblTotalAmount = new javax.swing.JLabel();
        lblTotalWeight = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        dateStartDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        dateEndDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        btnSearchByDate = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtReceiptNumber = new javax.swing.JTextField();
        btnSearchByReceiptNumber = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        btnSearchByFullName = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtNicNumber = new javax.swing.JTextField();
        btnSearchByNIC = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        radioActivePawns = new javax.swing.JRadioButton();
        radioExpiredPawns = new javax.swing.JRadioButton();
        radioRecollectedPawns = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        dateStartRecollectionDate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        dateEndRecollectionDate = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        btnSearchByRecollectionDate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));
        jPanel1.setMinimumSize(new java.awt.Dimension(260, 0));

        lstPawn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(lstPawn);

        lblPagination.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPagination.setText("STATUS");

        btnLoadSelectedPawn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLoadSelectedPawn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/receipt.png"))); // NOI18N
        btnLoadSelectedPawn.setText("Load Selected Pawn");
        btnLoadSelectedPawn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadSelectedPawnActionPerformed(evt);
            }
        });

        lblTotalAmount.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalAmount.setForeground(new java.awt.Color(255, 0, 51));
        lblTotalAmount.setText("Total: ");

        lblTotalWeight.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalWeight.setForeground(new java.awt.Color(0, 153, 0));
        lblTotalWeight.setText("Weight: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPagination, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(btnLoadSelectedPawn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalWeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoadSelectedPawn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPagination)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalWeight)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Search By Date"));

        dateStartDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Start Date:");

        dateEndDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("End Date:");

        btnSearchByDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSearchByDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/search.png"))); // NOI18N
        btnSearchByDate.setText("Search");
        btnSearchByDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchByDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addComponent(btnSearchByDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearchByDate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Searh By Fields"));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Receipt Number");

        txtReceiptNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnSearchByReceiptNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSearchByReceiptNumber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/search.png"))); // NOI18N
        btnSearchByReceiptNumber.setText("Search");
        btnSearchByReceiptNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchByReceiptNumberActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Full Name");

        txtFullName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnSearchByFullName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSearchByFullName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/search.png"))); // NOI18N
        btnSearchByFullName.setText("Search");
        btnSearchByFullName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchByFullNameActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("NIC Number");

        txtNicNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnSearchByNIC.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSearchByNIC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/search.png"))); // NOI18N
        btnSearchByNIC.setText("Search");
        btnSearchByNIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchByNICActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtNicNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchByNIC))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtReceiptNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchByReceiptNumber))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtFullName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchByFullName)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtReceiptNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchByReceiptNumber))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchByFullName))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNicNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchByNIC))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Global Search Filters"));

        pawningStatusRadioGroup.add(radioActivePawns);
        radioActivePawns.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioActivePawns.setSelected(true);
        radioActivePawns.setText("Active Pawns");

        pawningStatusRadioGroup.add(radioExpiredPawns);
        radioExpiredPawns.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioExpiredPawns.setText("Expired Pawns");

        pawningStatusRadioGroup.add(radioRecollectedPawns);
        radioRecollectedPawns.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        radioRecollectedPawns.setText("Recollected Pawns");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("<html>Select one of these options to fiter out the restults.<br>\nReceipt Number and NIC number searchers are global.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(radioActivePawns)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioExpiredPawns)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioRecollectedPawns))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioActivePawns)
                    .addComponent(radioExpiredPawns)
                    .addComponent(radioRecollectedPawns))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Search Tips"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("<html>Name search is case sensitive. eg. 'Apple' is not equal to 'apple'<br><br>\nYou can search full names with wildcard. eg. To search 'Apple', just type 'Ap' and it will show results that contains the letters 'Ap' in the Full Name filed.<br><br>\nYou can also use wildcard search on NIC Number");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Search By Recollection Date"));

        dateStartRecollectionDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Start Date:");

        dateEndRecollectionDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("End Date:");

        btnSearchByRecollectionDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSearchByRecollectionDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/search.png"))); // NOI18N
        btnSearchByRecollectionDate.setText("Search");
        btnSearchByRecollectionDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchByRecollectionDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateStartRecollectionDate, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateEndRecollectionDate, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                    .addComponent(btnSearchByRecollectionDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateEndRecollectionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dateStartRecollectionDate, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearchByRecollectionDate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchByDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchByDateActionPerformed
        // TODO add your handling code here:
        searchByDates();
    }//GEN-LAST:event_btnSearchByDateActionPerformed

    private void btnSearchByReceiptNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchByReceiptNumberActionPerformed
        // TODO add your handling code here:
        searchByReceiptNumber();

    }//GEN-LAST:event_btnSearchByReceiptNumberActionPerformed

    private void btnSearchByFullNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchByFullNameActionPerformed
        // TODO add your handling code here:
        searchByFullName();
    }//GEN-LAST:event_btnSearchByFullNameActionPerformed

    private void btnSearchByNICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchByNICActionPerformed
        // TODO add your handling code here:
        searchByNICNumber();
    }//GEN-LAST:event_btnSearchByNICActionPerformed

    private void btnLoadSelectedPawnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadSelectedPawnActionPerformed
        // TODO add your handling code here:
        loadSelectedPawnDetails();
    }//GEN-LAST:event_btnLoadSelectedPawnActionPerformed

    private void btnSearchByRecollectionDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchByRecollectionDateActionPerformed
        // TODO add your handling code here:
        searchByRecollectionDate();
    }//GEN-LAST:event_btnSearchByRecollectionDateActionPerformed

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
            java.util.logging.Logger.getLogger(SearchPawns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchPawns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchPawns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchPawns.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchPawns().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadSelectedPawn;
    private javax.swing.JButton btnSearchByDate;
    private javax.swing.JButton btnSearchByFullName;
    private javax.swing.JButton btnSearchByNIC;
    private javax.swing.JButton btnSearchByReceiptNumber;
    private javax.swing.JButton btnSearchByRecollectionDate;
    private com.toedter.calendar.JDateChooser dateEndDate;
    private com.toedter.calendar.JDateChooser dateEndRecollectionDate;
    private com.toedter.calendar.JDateChooser dateStartDate;
    private com.toedter.calendar.JDateChooser dateStartRecollectionDate;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPagination;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JLabel lblTotalWeight;
    private javax.swing.JList lstPawn;
    private javax.swing.ButtonGroup pawningStatusRadioGroup;
    private javax.swing.JRadioButton radioActivePawns;
    private javax.swing.JRadioButton radioExpiredPawns;
    private javax.swing.JRadioButton radioRecollectedPawns;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtNicNumber;
    private javax.swing.JTextField txtReceiptNumber;
    // End of variables declaration//GEN-END:variables
}
