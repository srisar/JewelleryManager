/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.utilities;

/**
 *
 * @author Sri Saravana
 */
public class Paginator {

    private int limit, offset, totalPageCount, totalRecordsCount, currentPage;

    public Paginator(int itemLimit) {

        this.limit = itemLimit;

    }

    public void initPaging(int totalRecords) {
        // set offset to 0
        offset = 0;

        totalRecordsCount = totalRecords;

        calculateTotalPageCount();
    }

    private int getTotalPageCount() {
        return totalPageCount;
    }

    public void calculateTotalPageCount() {

        if (totalRecordsCount % limit == 0) {
            totalPageCount = totalRecordsCount / limit;
        } else {
            totalPageCount = (totalRecordsCount / limit) + 1;
        }
    }

    public int firstPage() {
        currentPage = 1;

        offset = limit * (currentPage - 1);
        return offset;
    }

    public int previousPage() {
        if (currentPage > 1) {
            currentPage -= 1;
        }

        if (currentPage >= 1) {
            offset = limit * (currentPage - 1);
            return offset;
        }

        return offset;
    }

    public int nextPage() {

        if (currentPage < totalPageCount) {
            currentPage += 1;
        }

        if (currentPage <= totalPageCount) {
            offset = limit * (currentPage - 1);
            return offset;
        }

        return offset;
    }

    public int lastPage() {
        currentPage = totalPageCount;

        offset = limit * (currentPage - 1);

        return offset;
    }

    public int refreshCurrentPage() {
        return offset;
    }

}
