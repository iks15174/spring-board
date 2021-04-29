package com.example.board.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {
    
    private @Setter @Getter int currentPageNo;

    private @Setter @Getter int recordsPerPage; //페이지당 출력할 데이터 개수

    private @Setter @Getter int pageSize; //화면 하단에 출력할 페이지 사이즈

    /** 검색 키워드 */
	private @Setter @Getter String searchKeyword;

    private @Setter @Getter String searchType;

    public Criteria(){
        this.currentPageNo = 1;
        this.recordsPerPage = 10;
        this.pageSize = 5;
    }

    public int getStartPage(){
        return (currentPageNo - 1) * recordsPerPage;
    }

    public String makeQueryString(int pageNo) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                                        .queryParam("currentPageNo", pageNo)
                                        .queryParam("recordPerPage", recordsPerPage)
                                        .queryParam("pageSize", pageSize)
                                        .queryParam("searchType", searchType)
                                        .queryParam("searchKeyword", searchKeyword)
                                        .build()
                                        .encode();

        return uriComponents.toString();
    }
}
