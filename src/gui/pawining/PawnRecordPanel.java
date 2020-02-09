/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pawining;

import core.controller.MainController;
import core.object.Pawn;
import core.object.User;
import core.utilities.DateManager;
import core.utilities.NumberManager;
import core.utilities.printer.JewelleryReceiptPrinter;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Sri Saravana
 */
public class PawnRecordPanel extends javax.swing.JFrame {

    private Pawn selectedPawn;
    private MainController controller;
    private JewelleryReceiptPrinter pdfPrinter;
    DefaultComboBoxModel<Pawn.PawningStatus> pawningStatusComboModel;
    User activeUser;

    /**
     * Creates new form PawnRecord
     */
    public PawnRecordPanel() {
        initComponents();
    }

    public PawnRecordPanel(MainController controller, Pawn pawn, User user) {
        this();

        //set the controller
        this.controller = controller;

        // set the selectedPawn
        selectedPawn = pawn;

        // set the active user
        activeUser = user;

        // set the titlebar
        this.setTitle(selectedPawn.getFullName() + " Pawning Details");
        
        checkAdminFunctions();
        

        // prepare pawning status combo
        preparePawningStatusCombobox();

        // populate the fields with selected pawn
        populateFields();
    }

    public void display() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void preparePawningStatusCombobox() {
        pawningStatusComboModel = new DefaultComboBoxModel<>();

        pawningStatusComboModel.addElement(Pawn.PawningStatus.Active);
        pawningStatusComboModel.addElement(Pawn.PawningStatus.Expired);
        pawningStatusComboModel.addElement(Pawn.PawningStatus.Recollected);

        cmbPawningStatus.setModel(pawningStatusComboModel);
    }

    //
    //======================================================================================================================================
    //
    // disable the fields at startup
    private void disableFields() {
        dateDateOfEntry.setEnabled(false);
        dateDateOfRecollection.setEnabled(false);
        txtFullName.setEditable(false);
        txtAddress.setEditable(false);
        txtNicNumber.setEditable(false);
        txtDescription.setEditable(false);
        txtReceiptNumber.setEditable(false);
        txtNetWeight.setEditable(false);
        txtGoldQuality.setEditable(false);
        txtAmountPaid.setEditable(false);
        cmbPawningStatus.setEnabled(false);
    }

    private void enableFields() {
        dateDateOfEntry.setEnabled(true);
        dateDateOfRecollection.setEnabled(true);
        txtFullName.setEditable(true);
        txtAddress.setEditable(true);
        txtNicNumber.setEditable(true);
        txtDescription.setEditable(true);
        txtReceiptNumber.setEditable(true);
        txtNetWeight.setEditable(true);
        txtGoldQuality.setEditable(true);
        txtAmountPaid.setEditable(true);
        cmbPawningStatus.setEnabled(true);
    }

    // get and set pawning status from combobox
    private void setPawningStatus() {
        cmbPawningStatus.setSelectedItem(selectedPawn.getPawningStatus());
    }

    // set the pawning status combo box
    private Pawn.PawningStatus getPawningStatus() {
        return (Pawn.PawningStatus) cmbPawningStatus.getSelectedItem();
    }

    // populate the fields from sleceted pawn in the listbox
    private void populateFields() {

        // set the fields
        dateDateOfEntry.setDate(DateManager.toUtilDate(selectedPawn.getDateOfEntry()));
        dateDateOfRecollection.setDate(DateManager.toUtilDate(selectedPawn.getDateOfRecollection()));

        txtReceiptNumber.setText(NumberManager.toString(selectedPawn.getReceiptNumber()));
        txtFullName.setText(selectedPawn.getFullName());
        txtAddress.setText(selectedPawn.getAddress());
        txtNicNumber.setText(selectedPawn.getNicNumber());
        txtDescription.setText(selectedPawn.getDescription());
        txtNetWeight.setText(NumberManager.toString(selectedPawn.getNetWeight()));
        txtGoldQuality.setText(selectedPawn.getGoldQuality());
        txtAmountPaid.setText(NumberManager.toString(selectedPawn.getAmountPaid()));

        setPawningStatus();

    }

    /*
     * generate PDF receipt
     */
    private void printReceipt() {
        // initiate Jewellery Rrecept Printer
        pdfPrinter = new JewelleryReceiptPrinter(("abhira-jewell-receipt.pdf"));

        pdfPrinter.printPawningReceipt(selectedPawn);

        //open generated pdf file
        pdfPrinter.execPdfViewer();
    }

    /*
     * activate admin fuinctions
     */
    private void checkAdminFunctions() {

        if (activeUser.getRole() == User.Role.Administrator) {
            enableFields();
            btnDeleteRecord.setEnabled(true);
            btnUpdateRecord.setEnabled(true);
        } else {
            disableFields();
            btnDeleteRecord.setEnabled(false);
            btnUpdateRecord.setEnabled(false);
        }
    }

    /*
     * fields validation before updating
     */
    private boolean validateFields() {

        //reset field colors to normal
        txtAddress.setBackground(Color.WHITE);
        txtAmountPaid.setBackground(Color.WHITE);
        txtDescription.setBackground(Color.WHITE);
        txtGoldQuality.setBackground(Color.WHITE);
        txtFullName.setBackground(Color.WHITE);
        txtNetWeight.setBackground(Color.WHITE);
        txtNicNumber.setBackground(Color.WHITE);

        // set the boolean value for status, and after each if conditions
        // final return value can be determined.
        boolean status = true;

        if (dateDateOfEntry.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please enter valid date.");
            status = false;
        }

        if (dateDateOfRecollection.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please enter valid date of recollection.");
            status = false;
        }

        if (txtFullName.getText().isEmpty()) {
            txtFullName.setBackground(Color.YELLOW);
            lblStatus.setText("Please fill in the required fields marked in YELLOW.");
            status = false;
        }

        if (txtNicNumber.getText().isEmpty()) {
            txtNicNumber.setBackground(Color.YELLOW);
            lblStatus.setText("Please fill in the required fields marked in YELLOW.");
            status = false;
        }

        if (txtNetWeight.getText().isEmpty()) {
            //txtNetWeight.setBorder(BorderFactory.createLineBorder(Color.yellow));
            txtNetWeight.setBackground(Color.YELLOW);
            lblStatus.setText("Please fill in the required fields marked in YELLOW.");
            status = false;
        }

        if (txtReceiptNumber.getText().isEmpty()) {
            txtReceiptNumber.setBackground(Color.YELLOW);
            lblStatus.setText("Please fill in the required fields marked in YELLOW.");
            status = false;
        }

        // check the entry is valid number
        if (!NumberManager.canConveterToNumber(txtNetWeight.getText())) {
            txtNetWeight.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Net Weight should be a number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

        if (!NumberManager.canConveterToNumber(txtAmountPaid.getText())) {
            txtAmountPaid.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Amount should be a number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

        if (!NumberManager.canConveterToNumber(txtReceiptNumber.getText())) {
            txtReceiptNumber.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Receipt number should be a number", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

        return status != false;

    }

    /*
     * update selected pawn
     */
    private void updatePawn() {

        if (validateFields()) {
            Pawn updatePawn = new Pawn();
            updatePawn.setID(selectedPawn.getID());

            updatePawn.setDateOfEntry(DateManager.toSqlDate(dateDateOfEntry.getDate()));
            updatePawn.setDateOfRecollection(DateManager.toSqlDate(dateDateOfRecollection.getDate()));
            updatePawn.setFullName(txtFullName.getText());
            updatePawn.setAddress(txtAddress.getText());
            updatePawn.setNicNumber(txtNicNumber.getText());
            updatePawn.setDescription(txtDescription.getText());
            updatePawn.setReceiptNumber(NumberManager.toInt(txtReceiptNumber.getText()));
            updatePawn.setNetWeight(NumberManager.toDouble(txtNetWeight.getText()));
            updatePawn.setGoldQuality(txtGoldQuality.getText());
            updatePawn.setAmountPaid(NumberManager.toDouble(txtAmountPaid.getText()));

            updatePawn.setPawningStatus(getPawningStatus());

            if (controller.pawnController.updatePawn(updatePawn)) {
                lblStatus.setText("Record updated.");
            }
        }
    }

    /*
     * delete current pawn
     */
    private void deletePawn() {

        int result = JOptionPane.showConfirmDialog(this, "Do you want to delete this record?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            if (selectedPawn != null) {
                if (controller.pawnController.deletePawn(selectedPawn)) {
                    JOptionPane.showMessageDialog(this, "Entry deleted.", "Delete", JOptionPane.INFORMATION_MESSAGE);

                    //close this window
                    this.dispose();
                }
            }
        }

    }

    //
    //======================================================================================================================================
    //
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblFullName = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        lblAddress = new javax.swing.JLabel();
        lblNicNumber = new javax.swing.JLabel();
        txtNicNumber = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        lblGoldQuality = new javax.swing.JLabel();
        txtGoldQuality = new javax.swing.JTextField();
        lblNetWeight = new javax.swing.JLabel();
        txtNetWeight = new javax.swing.JTextField();
        lblAmountPaid = new javax.swing.JLabel();
        txtAmountPaid = new javax.swing.JTextField();
        lblPawningStatus = new javax.swing.JLabel();
        cmbPawningStatus = new javax.swing.JComboBox();
        lblReceiptNumber = new javax.swing.JLabel();
        txtReceiptNumber = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        dateDateOfEntry = new com.toedter.calendar.JDateChooser();
        lblDateOfEntry = new javax.swing.JLabel();
        dateDateOfRecollection = new com.toedter.calendar.JDateChooser();
        lblDateOfRecollection = new javax.swing.JLabel();
        btnUpdateRecord = new javax.swing.JButton();
        btnDeleteRecord = new javax.swing.JButton();
        btnPrintReceipt = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Personal Information"));

        lblFullName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFullName.setText("Full Name");

        txtFullName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAddress.setRows(3);
        jScrollPane2.setViewportView(txtAddress);

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAddress.setText("Address");

        lblNicNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNicNumber.setText("NIC Number");

        txtNicNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblDescription.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDescription.setText("Description");

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescription.setRows(3);
        txtDescription.setTabSize(4);
        jScrollPane3.setViewportView(txtDescription);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNicNumber, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDescription, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFullName, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFullName)
                    .addComponent(txtNicNumber)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFullName)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNicNumber)
                    .addComponent(txtNicNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDescription)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Pawn Information"));

        lblGoldQuality.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblGoldQuality.setText("Gold Quality");

        txtGoldQuality.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblNetWeight.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblNetWeight.setText("Net Weight");

        txtNetWeight.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAmountPaid.setText("Amount Paid");

        txtAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblPawningStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPawningStatus.setText("Pawning Status");

        cmbPawningStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblReceiptNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblReceiptNumber.setText("Receipt Number");

        txtReceiptNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPawningStatus)
                    .addComponent(lblNetWeight)
                    .addComponent(lblAmountPaid)
                    .addComponent(lblReceiptNumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAmountPaid)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtNetWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblGoldQuality)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGoldQuality, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                    .addComponent(cmbPawningStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtReceiptNumber))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceiptNumber)
                    .addComponent(txtReceiptNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNetWeight)
                    .addComponent(txtNetWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGoldQuality)
                    .addComponent(txtGoldQuality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAmountPaid)
                    .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPawningStatus)
                    .addComponent(cmbPawningStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Dates Information"));

        dateDateOfEntry.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblDateOfEntry.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDateOfEntry.setText("Date of Entry");

        dateDateOfRecollection.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblDateOfRecollection.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDateOfRecollection.setText("Date of Recollection");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDateOfRecollection, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDateOfEntry, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateDateOfEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateDateOfRecollection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateDateOfEntry, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(lblDateOfEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateDateOfRecollection, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(lblDateOfRecollection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnUpdateRecord.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUpdateRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/edit-database.png"))); // NOI18N
        btnUpdateRecord.setText("Update Record");
        btnUpdateRecord.setEnabled(false);
        btnUpdateRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateRecordActionPerformed(evt);
            }
        });

        btnDeleteRecord.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDeleteRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/clear.png"))); // NOI18N
        btnDeleteRecord.setText("Delete This Record");
        btnDeleteRecord.setEnabled(false);
        btnDeleteRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRecordActionPerformed(evt);
            }
        });

        btnPrintReceipt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPrintReceipt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/printer.png"))); // NOI18N
        btnPrintReceipt.setText("Print This Receipt");
        btnPrintReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintReceiptActionPerformed(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStatus.setText("Status OK");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(btnDeleteRecord)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnUpdateRecord))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(btnPrintReceipt, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPrintReceipt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateRecord)
                    .addComponent(btnDeleteRecord))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateRecordActionPerformed
        // TODO add your handling code here:
        updatePawn();
    }//GEN-LAST:event_btnUpdateRecordActionPerformed

    private void btnDeleteRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRecordActionPerformed
        // TODO add your handling code here:
        deletePawn();
    }//GEN-LAST:event_btnDeleteRecordActionPerformed

    private void btnPrintReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReceiptActionPerformed
        // TODO add your handling code here:
        printReceipt();
    }//GEN-LAST:event_btnPrintReceiptActionPerformed

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
            java.util.logging.Logger.getLogger(PawnRecordPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PawnRecordPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PawnRecordPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PawnRecordPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PawnRecordPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteRecord;
    private javax.swing.JButton btnPrintReceipt;
    private javax.swing.JButton btnUpdateRecord;
    private javax.swing.JComboBox cmbPawningStatus;
    private com.toedter.calendar.JDateChooser dateDateOfEntry;
    private com.toedter.calendar.JDateChooser dateDateOfRecollection;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblAmountPaid;
    private javax.swing.JLabel lblDateOfEntry;
    private javax.swing.JLabel lblDateOfRecollection;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblGoldQuality;
    private javax.swing.JLabel lblNetWeight;
    private javax.swing.JLabel lblNicNumber;
    private javax.swing.JLabel lblPawningStatus;
    private javax.swing.JLabel lblReceiptNumber;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtAmountPaid;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtGoldQuality;
    private javax.swing.JTextField txtNetWeight;
    private javax.swing.JTextField txtNicNumber;
    private javax.swing.JTextField txtReceiptNumber;
    // End of variables declaration//GEN-END:variables
}
