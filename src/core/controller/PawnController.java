package core.controller;

import core.db.Database;

import core.object.Pawn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PawnController {

    private final Database db;

    /*
     * database table details
     */
    public static final String TABLE_NAME = "tbl_pawning";

    // names of the database table fields
    public static final String ID = "ID";
    public static final String RECEIPT_NUMBER = "ReceiptNumber";
    public static final String DATE_OF_ENTRY = "DateOfEntry";
    public static final String FULLNAME = "FullName";
    public static final String ADDRESS = "Address";
    public static final String NIC_NUMBER = "NicNumber";
    public static final String DESCRIPTION = "Description";
    public static final String NET_WEIGHT = "NetWeight";
    public static final String GOLD_QUALITY = "GoldQuality";
    public static final String AMOUNT_PAID = "AmountPaid";
    public static final String DATE_OF_RECOLLECTION = "DateOfRecollection";
    public static final String PAWNING_STATUS = "PawningStatus";

    public static final String PAWNING_STATUS_ACTIVE = "active";
    public static final String PAWNING_STATUS_RECOLLECTED = "recollected";
    public static final String PAWNING_STATUS_EXPIRED = "expired";

    // constructor
    public PawnController(Database db) {
        this.db = db;
    }

    /*
     get the row count of tbl_pawning
     */
    public int getTableRowCount() {
        ResultSet rs = db.executeQuery("SELECT COUNT(*) from tbl_pawning");

        try {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }

    public int getRecollectedPawnCount() {
        ResultSet rs = db.executeQuery("SELECT COUNT(*) from tbl_pawning where PawningStatus='Recollected'");

        try {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }

    public int getActivePawnCount() {
        ResultSet rs = db.executeQuery("SELECT COUNT(*) from tbl_pawning where PawningStatus='Active'");

        try {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }

    public int getExpiredPawnCount() {
        ResultSet rs = db.executeQuery("SELECT COUNT(*) from tbl_pawning where PawningStatus='Expired'");

        try {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }

    /*
     Get pawn by ID
     */
    public Pawn getPawnByID(int id) {

        Pawn pawn;

        ResultSet pawnResultSet = db.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID=" + id);

        try {

            while (pawnResultSet.next()) {
                return generatePawnObject(pawnResultSet);

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    /*
     add pawn to database
     */
    public boolean insertPawn(Pawn pawn) {

        try {

            PreparedStatement insertPawnStatement = db.getPreparedStatement(
                    "INSERT INTO "
                    + TABLE_NAME
                    + "("
                    + DATE_OF_ENTRY + ", "
                    + RECEIPT_NUMBER + ", "
                    + FULLNAME + ", "
                    + ADDRESS + ", "
                    + NIC_NUMBER + ", "
                    + DESCRIPTION + ", "
                    + NET_WEIGHT + ", "
                    + GOLD_QUALITY + ", "
                    + AMOUNT_PAID + ", "
                    + DATE_OF_RECOLLECTION + ", "
                    + PAWNING_STATUS + ") "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            insertPawnStatement.setDate(1, pawn.getDateOfEntry());
            insertPawnStatement.setInt(2, pawn.getReceiptNumber());
            insertPawnStatement.setString(3, pawn.getFullName());
            insertPawnStatement.setString(4, pawn.getAddress());
            insertPawnStatement.setString(5, pawn.getNicNumber());
            insertPawnStatement.setString(6, pawn.getDescription());
            insertPawnStatement.setDouble(7, pawn.getNetWeight());
            insertPawnStatement.setString(8, pawn.getGoldQuality());
            insertPawnStatement.setDouble(9, pawn.getAmountPaid());
            insertPawnStatement.setDate(10, pawn.getDateOfRecollection());
            insertPawnStatement.setString(11, pawn.getPawningStatus().name());

            insertPawnStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return false;
    }

    // deletes pawn record
    public boolean deletePawn(Pawn pawn) {
        try {
            PreparedStatement preparedStatement = db.getPreparedStatement("DELETE FROM " + TABLE_NAME + " WHERE ID=?");
            preparedStatement.setInt(1, pawn.getID());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return false;

    }

    // update already exisiting pawn
    public boolean updatePawn(Pawn pawn) {
        try {

            PreparedStatement preparedStatement = db.getPreparedStatement(
                    "UPDATE "
                    + TABLE_NAME
                    + " SET "
                    + DATE_OF_ENTRY + "=?, "
                    + RECEIPT_NUMBER + "=?, "
                    + FULLNAME + "=?, "
                    + ADDRESS + "=?, "
                    + NIC_NUMBER + "=?, "
                    + DESCRIPTION + "=?, "
                    + NET_WEIGHT + "=?, "
                    + GOLD_QUALITY + "=?, "
                    + AMOUNT_PAID + "=?, "
                    + DATE_OF_RECOLLECTION + "=?, "
                    + PAWNING_STATUS + "=? "
                    + "WHERE "
                    + ID + "=?"
            );

            preparedStatement.setDate(1, pawn.getDateOfEntry());
            preparedStatement.setInt(2, pawn.getReceiptNumber());
            preparedStatement.setString(3, pawn.getFullName());
            preparedStatement.setString(4, pawn.getAddress());
            preparedStatement.setString(5, pawn.getNicNumber());
            preparedStatement.setString(6, pawn.getDescription());
            preparedStatement.setDouble(7, pawn.getNetWeight());
            preparedStatement.setString(8, pawn.getGoldQuality());
            preparedStatement.setDouble(9, pawn.getAmountPaid());
            preparedStatement.setDate(10, pawn.getDateOfRecollection());
            preparedStatement.setString(11, pawn.getPawningStatus().name());
            preparedStatement.setInt(12, pawn.getID());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return false;
    }

    // get all Pawn in the table with range
    public ArrayList<Pawn> getPawns(int limit, int offset) {

        ArrayList<Pawn> pawnList = new ArrayList<>();

        ResultSet rs = db.executeQuery(
                "SELECT * FROM "
                + TABLE_NAME + " "
                + "ORDER BY " + DATE_OF_ENTRY + " DESC "
                + "LIMIT " + limit + " OFFSET " + offset
        );

        try {

            while (rs.next()) {
                Pawn p = new Pawn();

                p.setID(rs.getInt(ID));
                p.setDateOfEntry(rs.getDate(DATE_OF_ENTRY));
                p.setFullName(rs.getString(FULLNAME));
                p.setAddress(rs.getString(ADDRESS));
                p.setNicNumber(rs.getString(NIC_NUMBER));
                p.setDescription(rs.getString(DESCRIPTION));
                p.setNetWeight(rs.getDouble(NET_WEIGHT));
                p.setGoldQuality(rs.getString(GOLD_QUALITY));
                p.setAmountPaid(rs.getDouble(AMOUNT_PAID));
                p.setDateOfRecollection(rs.getDate(DATE_OF_RECOLLECTION));
                p.setPawningStatus(Pawn.PawningStatus.valueOf(rs.getString(PAWNING_STATUS)));
                p.setReceiptNumber(rs.getInt(RECEIPT_NUMBER));

                pawnList.add(p);

            }

            // return the pawn list 
            return pawnList;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    /*
     * get last entered receipt number
     */
    public int getLastReceiptNumber() {
        try {

            PreparedStatement ps = db.getPreparedStatement("SELECT ReceiptNumber FROM tbl_pawning ORDER BY ID DESC LIMIT 1");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return rs.getInt("ReceiptNumber");
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return -1;
    }

    /*
     ===================================================================================================================
     SUMMERY CARD METHODS
     ===================================================================================================================
     */
    // get total pawning amout for active pawns
    public double getTotalPawningAmount() {

        double total = 0;

        try {

            ResultSet totalPawningAmoutRS = db.executeQuery("SELECT SUM(AmountPaid) as TotalAmount FROM tbl_pawning WHERE PawningStatus='active' OR PawningStatus='expired'");

            while (totalPawningAmoutRS.next()) {
                total = totalPawningAmoutRS.getDouble(1);
            }

            return total;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0.0;

    }

    // get total pawning gold
    public double getTotalPawningWeight() {

        double total = 0;

        try {

            ResultSet totalPawningRS = db.executeQuery("SELECT ROUND(SUM(NetWeight), 2) as TotalNetWeight FROM tbl_pawning WHERE (PawningStatus='expired' OR PawningStatus='active')");

            while (totalPawningRS.next()) {
                total = totalPawningRS.getDouble(1);
            }

            return total;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0.0;
    }

    // get today's pawnig amount
    public double getTodayPawningAmount(java.sql.Date today) {

        double total = 0;

        try {

            PreparedStatement todayPawningStatement = db.getPreparedStatement("SELECT SUM(tbl_pawning.AmountPaid) from tbl_pawning WHERE (DateOfEntry=?) AND (PawningStatus='active' OR PawningStatus='expired')");
            todayPawningStatement.setDate(1, today);

            ResultSet todayPawningAmountRS = todayPawningStatement.executeQuery();

            while (todayPawningAmountRS.next()) {
                total = todayPawningAmountRS.getDouble(1);
            }

            return total;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0.0;
    }

    // get today's pawning weight
    public double getTodayPawningWeignt(java.sql.Date today) {
        double total = 0;

        try {

            PreparedStatement statement = db.getPreparedStatement("SELECT ROUND(SUM(tbl_pawning.NetWeight), 2) from tbl_pawning WHERE (DateOfEntry=?) AND (PawningStatus='active' OR PawningStatus='expired')");
            statement.setDate(1, today);

            ResultSet totalPawningWeightRS = statement.executeQuery();

            while (totalPawningWeightRS.next()) {
                total = totalPawningWeightRS.getDouble(1);
            }

            return total;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0.0;
    }

    public double getPawningAmountBetweenDates(java.sql.Date startDate, java.sql.Date endDate) {
        double total = 0;

        try {

            PreparedStatement todayPawningStatement = db.getPreparedStatement("SELECT SUM(tbl_pawning.AmountPaid) FROM tbl_pawning WHERE (DateOfEntry BETWEEN ? AND ?) AND (PawningStatus='active' OR PawningStatus='expired')");
            todayPawningStatement.setDate(1, startDate);
            todayPawningStatement.setDate(2, endDate);

            ResultSet todayPawningAmountRS = todayPawningStatement.executeQuery();

            while (todayPawningAmountRS.next()) {
                total = todayPawningAmountRS.getDouble(1);
            }

            return total;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }

    public double getPawningWeightBetweenDates(java.sql.Date startDate, java.sql.Date endDate) {
        double total = 0;

        try {

            PreparedStatement todayPawningStatement = db.getPreparedStatement("SELECT ROUND(SUM(tbl_pawning.NetWeight), 2) FROM tbl_pawning WHERE (DateOfEntry BETWEEN ? AND ?) AND (tbl_pawning.PawningStatus='active' OR tbl_pawning.PawningStatus='expired')");
            todayPawningStatement.setDate(1, startDate);
            todayPawningStatement.setDate(2, endDate);

            ResultSet todayPawningAmountRS = todayPawningStatement.executeQuery();

            while (todayPawningAmountRS.next()) {
                total = todayPawningAmountRS.getDouble(1);
            }

            return total;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }

    /*
     ===================================================================================================================
     SEARCH METHODS
     ===================================================================================================================
     */
    public ArrayList<Pawn> searchPawnByDates(java.sql.Date startDate, java.sql.Date endDate, boolean pawningStatusActive, boolean pawningStatusExpired, boolean pawningStatusRecollected) {

        try {

            PreparedStatement statement;

            if (pawningStatusExpired) {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (DateOfEntry  BETWEEN ? AND ?) AND (PawningStatus='expired') ORDER BY DateOfEntry DESC");

            } else if (pawningStatusRecollected) {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (DateOfEntry  BETWEEN ? AND ?) AND (PawningStatus='recollected') ORDER BY DateOfEntry DESC");

            } else {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (DateOfEntry  BETWEEN ? AND ?) AND (PawningStatus='active') ORDER BY DateOfEntry DESC");
            }

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    public ArrayList<Pawn> searchPawnByRecollectedDate(java.sql.Date startDate, java.sql.Date endDate, boolean pawningStatusActive, boolean pawningStatusExpired, boolean pawningStatusRecollected) {
        try {

            PreparedStatement statement;

            if (pawningStatusExpired) {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (DateOfRecollection  BETWEEN ? AND ?) AND (PawningStatus='expired') ORDER BY DateOfEntry DESC");

            } else if (pawningStatusRecollected) {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (DateOfRecollection  BETWEEN ? AND ?) AND (PawningStatus='recollected') ORDER BY DateOfEntry DESC");

            } else {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (DateOfRecollection  BETWEEN ? AND ?) AND (PawningStatus='active') ORDER BY DateOfEntry DESC");
            }

            statement.setDate(1, startDate);
            statement.setDate(2, endDate);

            ResultSet rs = statement.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    public ArrayList<Pawn> searchPawnByReceiptNumber(int receiptNumber) {

        try {

            PreparedStatement statement;

            statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE ReceiptNumber=? ORDER BY DateOfEntry DESC");

            statement.setInt(1, receiptNumber);

            ResultSet rs = statement.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    public ArrayList<Pawn> searchPawnByFullName(String fullName, boolean pawningStatusActive, boolean pawningStatusExpired, boolean pawningStatusRecollected) {
        try {

            PreparedStatement statement;

            if (pawningStatusExpired) {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (FullName LIKE ?) AND (PawningStatus='expired') ORDER BY DateOfEntry DESC");

            } else if (pawningStatusRecollected) {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (FullName LIKE ?) AND (PawningStatus='recollected') ORDER BY DateOfEntry DESC");

            } else {
                statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE (FullName LIKE ?) AND (PawningStatus='active') ORDER BY DateOfEntry DESC");
            }

            statement.setString(1, "%" + fullName + "%");

            ResultSet rs = statement.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    public ArrayList<Pawn> searchPawnByNIC(String NicNumber) {
        try {

            PreparedStatement statement;

            statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE NicNumber LIKE ? ORDER BY DateOfEntry DESC");

            statement.setString(1, "%" + NicNumber + "%");

            ResultSet rs = statement.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    /*
     ===================================================================================================================
     REPORTS METHODS
     ===================================================================================================================
     */
    public ArrayList<Pawn> getTodayPawns() {

        try {

            String query = "SELECT * FROM tbl_pawning WHERE DateOfEntry = CURDATE()";

            PreparedStatement ps = db.getPreparedStatement(query);

            ResultSet rs = ps.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return null;
    }

    public ArrayList<Pawn> getTodayWithdrawals() {
        try {

            String query = "SELECT * FROM tbl_pawning WHERE ( DateOfRecollection = CURDATE() AND PawningStatus = \"Recollected\" )";

            PreparedStatement ps = db.getPreparedStatement(query);

            ResultSet rs = ps.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return null;
    }

    public ArrayList<Pawn> getTodayExpiringPawns() {
        try {

            String query = "SELECT * FROM tbl_pawning WHERE ( DateOfRecollection = CURDATE() AND PawningStatus <> \"Recollected\" )";

            PreparedStatement ps = db.getPreparedStatement(query);

            ResultSet rs = ps.executeQuery();

            return generatePawnListFromResultSet(rs);

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return null;
    }

    public Pawn getRelatedPawn(String nicNumber) {

        try {
            PreparedStatement statement = db.getPreparedStatement("SELECT * FROM tbl_pawning WHERE tbl_pawning.NicNumber=? LIMIT 1");
            statement.setString(1, nicNumber);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return generatePawnObject(rs);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    /*
     ===================================================================================================================
     PRIVATE MEOTHODS
     ===================================================================================================================
     */
    /*
     * generate Pawn object from ResultSet
     */
    private Pawn generatePawnObject(ResultSet resultSet) throws SQLException {

        Pawn p = new Pawn();

        p.setID(resultSet.getInt(ID));
        p.setDateOfEntry(resultSet.getDate(DATE_OF_ENTRY));
        p.setFullName(resultSet.getString(FULLNAME));
        p.setAddress(resultSet.getString(ADDRESS));
        p.setNicNumber(resultSet.getString(NIC_NUMBER));
        p.setDescription(resultSet.getString(DESCRIPTION));
        p.setNetWeight(resultSet.getDouble(NET_WEIGHT));
        p.setGoldQuality(resultSet.getString(GOLD_QUALITY));
        p.setAmountPaid(resultSet.getDouble(AMOUNT_PAID));
        p.setDateOfRecollection(resultSet.getDate(DATE_OF_RECOLLECTION));
        p.setPawningStatus(Pawn.PawningStatus.valueOf(resultSet.getString(PAWNING_STATUS)));
        p.setReceiptNumber(resultSet.getInt(RECEIPT_NUMBER));

        return p;

    }

    /*
     * generate arraylist of pawn objects from resultset
     */
    private ArrayList<Pawn> generatePawnListFromResultSet(ResultSet resultSet) throws SQLException {

        ArrayList<Pawn> pawnList = new ArrayList<>();

        while (resultSet.next()) {
            Pawn p = new Pawn();

            p.setID(resultSet.getInt(ID));
            p.setDateOfEntry(resultSet.getDate(DATE_OF_ENTRY));
            p.setFullName(resultSet.getString(FULLNAME));
            p.setAddress(resultSet.getString(ADDRESS));
            p.setNicNumber(resultSet.getString(NIC_NUMBER));
            p.setDescription(resultSet.getString(DESCRIPTION));
            p.setNetWeight(resultSet.getDouble(NET_WEIGHT));
            p.setGoldQuality(resultSet.getString(GOLD_QUALITY));
            p.setAmountPaid(resultSet.getDouble(AMOUNT_PAID));
            p.setDateOfRecollection(resultSet.getDate(DATE_OF_RECOLLECTION));
            p.setPawningStatus(Pawn.PawningStatus.valueOf(resultSet.getString(PAWNING_STATUS)));
            p.setReceiptNumber(resultSet.getInt(RECEIPT_NUMBER));

            pawnList.add(p);

        }

        // return the pawn list 
        return pawnList;
    }

}
