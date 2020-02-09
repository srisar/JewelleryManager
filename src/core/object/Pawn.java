/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.object;

/**
 *
 * @author Sri Saravana
 */
public class Pawn {

    public static enum PawningStatus {

        Active, Expired, Recollected
    }

    private int ID, receiptNumber;
    private double netWeight, amountPaid;
    private String fullName, address, description, goldQuality, nicNumber;
    private java.sql.Date dateOfEntry, dateOfRecollection;

    private PawningStatus pawningStatus;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoldQuality() {
        return goldQuality;
    }

    public void setGoldQuality(String goldQuality) {
        this.goldQuality = goldQuality;
    }

    public java.sql.Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(java.sql.Date date) {
        this.dateOfEntry = date;
    }

    public java.sql.Date getDateOfRecollection() {
        return dateOfRecollection;
    }

    public void setDateOfRecollection(java.sql.Date dateOfRecollection) {
        this.dateOfRecollection = dateOfRecollection;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public PawningStatus getPawningStatus() {
        return pawningStatus;
    }

    public void setPawningStatus(PawningStatus pawningStatus) {
        this.pawningStatus = pawningStatus;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String toString() {
        return this.dateOfEntry.toString() + " : " + this.fullName;
    }

}
