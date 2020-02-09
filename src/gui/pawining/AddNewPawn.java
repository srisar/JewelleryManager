/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pawining;

import core.controller.MainController;
import core.info.Information;
import core.utilities.DateManager;
import core.object.Pawn;
import core.utilities.printer.JewelleryReceiptPrinter;
import core.utilities.NumberManager;
import java.awt.Color;
import javax.swing.JOptionPane;
import org.joda.time.LocalDate;

/**
 *
 * @author Sri Saravana
 */
public class AddNewPawn extends javax.swing.JFrame {

    MainController mc;
    Pawn currentPawn;
    JewelleryReceiptPrinter pdfPrinter;
    int currentReceiptNumber;

    /**
     * Creates new form AddNewPawn
     */
    public AddNewPawn() {
        initComponents();
    }

    public AddNewPawn(MainController mainController) {
        this();

        this.setTitle("Add New Pawn : " + Information.APP_TITLE);

        // initiate the main controller
        mc = mainController;

        // create today's date and fill it on dateChooserDate
        LocalDate currentDate = LocalDate.now();
        dateDateOfEntry.setDate(currentDate.toDate());

        //disable the print PDF receipt button
        btnPrintPdfReceipt.setEnabled(false);

        // set current receipt number
        setCurrentReceiptNumber();

    }

    // make the window display
    public void display() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /*
     ===================================================================================================================
     Operational functions below
     ===================================================================================================================
     *
     */
    private void setCurrentReceiptNumber() {
        currentReceiptNumber = mc.pawnController.getLastReceiptNumber() + 1;

        txtReceiptNumber.setText(NumberManager.toString(currentReceiptNumber));
    }

    /*
     * validate fields
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
            JOptionPane.showMessageDialog(this, "Please enter valid date");
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
     * clear all fields
     */
    private void clearAll() {
        txtFullName.setText("");
        txtAddress.setText("");
        txtDescription.setText("");
        txtGoldQuality.setText("");
        txtNetWeight.setText("");
        txtNicNumber.setText("");
        txtAmountPaid.setText("");
        dateDateOfRecollection.setDate(null);
    }

    /*
     * insert the pawn to database
     */
    private void insertPawnToDatabase() {

        if (validateFields()) {

            double netWeight = NumberManager.toDouble(txtNetWeight.getText());
            double amountPaid = NumberManager.toDouble(txtAmountPaid.getText());

            currentPawn = new Pawn();

            currentPawn.setDateOfEntry(DateManager.toSqlDate(dateDateOfEntry.getDate()));
            currentPawn.setReceiptNumber(NumberManager.toInt(txtReceiptNumber.getText()));
            currentPawn.setFullName(txtFullName.getText());
            currentPawn.setAddress(txtAddress.getText());
            currentPawn.setNicNumber(txtNicNumber.getText());
            currentPawn.setDescription(txtDescription.getText());
            currentPawn.setNetWeight(netWeight);
            currentPawn.setGoldQuality(txtGoldQuality.getText());
            currentPawn.setAmountPaid(amountPaid);
            currentPawn.setDateOfRecollection(DateManager.toSqlDate(dateDateOfRecollection.getDate()));
            currentPawn.setPawningStatus(Pawn.PawningStatus.Active);

            if (mc.pawnController.insertPawn(currentPawn)) {
                lblStatus.setText("Saved.You can now print the receipt");

                // enable print receipt button
                btnPrintPdfReceipt.setEnabled(true);

                // disable accept button in order to avoid accedential duplicate data entry
                btnAccept.setEnabled(false);
            }
        }
    }

    /*
     * print pdf for the current record.
     * add another record, clear all fields, enable accept button and disable print receipt button
     */
    private void printPdfReceipt() {

        // initiate Jewellery Rrecept Printer
        pdfPrinter = new JewelleryReceiptPrinter(("abhira-jwell-receipt.pdf"));

        pdfPrinter.printPawningReceipt(currentPawn);

        //open generated pdf file
        pdfPrinter.execPdfViewer();

    }

    /*
     * add another pawn
     */
    private void addAnotherPawn() {

        // set the currentPawn to null - deletes all the information
        currentPawn = null;

        // clear all fields
        clearAll();

        // disable printpdf button
        btnPrintPdfReceipt.setEnabled(false);

        setCurrentReceiptNumber();

    }

    // close add new pawn windo
    private void closeWindow() {
        this.dispose();

    }

    // check NIC number and if a related pawn is found in the database
    // fill the name and address from that pawn
    private void checkNICRelation() {

        Pawn p = mc.pawnController.getRelatedPawn(txtNicNumber.getText());

        if (p != null) {
            txtFullName.setText(p.getFullName());
            txtAddress.setText(p.getAddress());
        }else{
            JOptionPane.showMessageDialog(rootPane, "No related record found for this NIC");
        }

    }

    /*
     END of Operational functions below
     ====

     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateDateOfEntry = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        txtNicNumber = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        txtNetWeight = new javax.swing.JTextField();
        txtGoldQuality = new javax.swing.JTextField();
        dateDateOfRecollection = new com.toedter.calendar.JDateChooser();
        lblReceiptNumber = new javax.swing.JLabel();
        txtReceiptNumber = new javax.swing.JTextField();
        btnCheckNIC = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtAmountPaid = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnAccept = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnPrintPdfReceipt = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        btnAddAnother = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Date");

        dateDateOfEntry.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Full Name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Address");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("NIC Number");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Description");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Net Weignt");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Gold Quality");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Date of Recollection");

        txtFullName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtAddress.setRows(3);
        txtAddress.setTabSize(4);
        jScrollPane1.setViewportView(txtAddress);

        txtNicNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescription.setRows(3);
        txtDescription.setTabSize(4);
        jScrollPane2.setViewportView(txtDescription);

        txtNetWeight.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtGoldQuality.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        dateDateOfRecollection.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblReceiptNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblReceiptNumber.setText("Receipt Number");

        txtReceiptNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnCheckNIC.setText("Check & Fill");
        btnCheckNIC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckNICActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblReceiptNumber)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateDateOfEntry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFullName)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addComponent(txtNetWeight)
                    .addComponent(txtGoldQuality)
                    .addComponent(dateDateOfRecollection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtReceiptNumber)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNicNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCheckNIC, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateDateOfEntry, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReceiptNumber)
                    .addComponent(txtReceiptNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(txtNicNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCheckNIC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtNetWeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel7)
                    .addComponent(txtGoldQuality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateDateOfRecollection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 51));
        jLabel8.setText("Amount Paid");

        txtAmountPaid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAmountPaid)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtAmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnClear.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/clear.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnAccept.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/save.png"))); // NOI18N
        btnAccept.setText("Save This Pawning");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/close.png"))); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnPrintPdfReceipt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnPrintPdfReceipt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/printer.png"))); // NOI18N
        btnPrintPdfReceipt.setText("Print PDF Receipt");
        btnPrintPdfReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintPdfReceiptActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStatus.setText("Not saved.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAddAnother.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddAnother.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/icons/add.png"))); // NOI18N
        btnAddAnother.setText("Add Another Pawning");
        btnAddAnother.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAnotherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 132, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnClear)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAccept)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnClose))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnAddAnother)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPrintPdfReceipt)))))
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnAccept)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPrintPdfReceipt)
                    .addComponent(btnAddAnother))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        // TODO add your handling code here:
        insertPawnToDatabase();
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        closeWindow();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clearAll();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnAddAnotherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAnotherActionPerformed
        // TODO add your handling code here:
        addAnotherPawn();
    }//GEN-LAST:event_btnAddAnotherActionPerformed

    private void btnPrintPdfReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintPdfReceiptActionPerformed
        // TODO add your handling code here:
        printPdfReceipt();
    }//GEN-LAST:event_btnPrintPdfReceiptActionPerformed

    private void btnCheckNICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckNICActionPerformed
        // TODO add your handling code here:
        checkNICRelation();
    }//GEN-LAST:event_btnCheckNICActionPerformed

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
            java.util.logging.Logger.getLogger(AddNewPawn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewPawn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewPawn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewPawn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddNewPawn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAddAnother;
    private javax.swing.JButton btnCheckNIC;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPrintPdfReceipt;
    private com.toedter.calendar.JDateChooser dateDateOfEntry;
    private com.toedter.calendar.JDateChooser dateDateOfRecollection;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
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
