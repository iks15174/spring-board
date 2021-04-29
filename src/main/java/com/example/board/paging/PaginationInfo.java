package com.example.board.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {

    private @Setter @Getter Criteria criteria;

    //전체 데이터 개수
    private @Setter @Getter int totalRecordCount;

    //전체 페이지 개수
    private @Setter @Getter int totalPageCount;

    //페이지 리스트의 첫 페이지 번호
    private @Setter @Getter int firstPage;

    //페이지 리스트의 마지막 페이지 번호
    private @Setter @Getter int lastPage;

    //SQL의 조건절에 사용되는 첫 RNUM
    private @Setter @Getter int firstRecordIndex;

    //SQL의 조건절에 사용되는 마지막 RNUM
    private @Setter @Getter int lastRecordIndex;

    //이전 페이지 존재 여부
    private @Setter @Getter boolean hasPreviousPage;

    //다음 페이지 존재 여부
    private @Setter @Getter boolean hasNextPage;
    
    public PaginationInfo(Criteria criteria){
        if(criteria.getCurrentPageNo() < 1){
            criteria.setCurrentPageNo(1);
        }
        if(criteria.getRecordsPerPage() < 1 || criteria.getRecordsPerPage() > 100){
            criteria.setRecordsPerPage(10);
        }
        if(criteria.getPageSize() < 5 || criteria.getPageSize() > 20){
            criteria.setPageSize(5);
        }

        this.criteria = criteria;
    }

    public void setTotalRecordCount(int totalRecordCount){
        this.totalRecordCount = totalRecordCount;

        if(totalRecordCount > 0){
            calculation();
        }
    }

    private void calculation(){
        totalPageCount = ((totalRecordCount - 1) / criteria.getRecordsPerPage()) + 1;
        if(criteria.getCurrentPageNo() > totalPageCount){
            criteria.setCurrentPageNo(totalPageCount);
        }

        firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;
        lastPage = firstPage + criteria.getPageSize() - 1;
        if(lastPage > totalPageCount){
            lastPage = totalPageCount;
        }

        firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordsPerPage();
        lastRecordIndex = firstRecordIndex + criteria.getRecordsPerPage();

        hasPreviousPage = firstPage != 1;
        hasNextPage = (lastPage * criteria.getRecordsPerPage()) < totalRecordCount;
    }
}
