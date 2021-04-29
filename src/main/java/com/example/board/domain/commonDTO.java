package com.example.board.domain;


import java.time.LocalDateTime;
import com.example.board.paging.Criteria;
import com.example.board.paging.PaginationInfo;

import lombok.Getter;
import lombok.Setter;

public class commonDTO extends Criteria {
    
    //페이징 정보
    private @Setter @Getter PaginationInfo paginationInfo;

    //삭제 여부
    private @Setter @Getter String deleteYn;

    //등록일
    private @Setter @Getter LocalDateTime insertTime;

    //수정일
    private @Setter @Getter LocalDateTime updateTime;

    //삭제일
    private @Setter @Getter LocalDateTime deleteTime;
}
