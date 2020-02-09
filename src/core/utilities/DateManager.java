/*
* Utility class for managind dates between java.util.Date and java.sql.Date
* to make the working easy, joda time lib is used by this class.
*/

package core.utilities;

import org.joda.time.LocalDate;


/**
 *
 * @author Sri Saravana
 */
public final class DateManager {

    // to convert java.util Date into java.sql Date
    public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }

    // to convert java.sql Date into java.util Date
    public static java.util.Date toUtilDate(java.sql.Date sqlDate) {
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());

        return utilDate;
    }

    // this will return today's date in java.util.Date format
    public static java.util.Date getUtilToday() {
        return new java.util.Date();
    }

    // this will return sql date equvelent of getUtilToday
    public static java.sql.Date getSqlToday() {
        return new java.sql.Date(getUtilToday().getTime());
    }
    
    
    // this will return the frist day of current month
    // REQ: joda time lib
    // http://joda-time.sourceforge.net/
    public static java.sql.Date getFirstDayOfCurrentMonth(){
        LocalDate today = new LocalDate();
        
        int year = today.getYear();
        int month = today.getMonthOfYear();
        
        LocalDate firstDayOfMonth = new LocalDate(year, month, 1);
        
        java.sql.Date sqlFirstDayOfMonth = new java.sql.Date(firstDayOfMonth.toDate().getTime());
        
        
        return sqlFirstDayOfMonth;
        
    }

}
