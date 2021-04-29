package com.example.board.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO extends commonDTO{

    /** 번호 (PK) */
	private @Setter @Getter Long idx;

	/** 제목 */
	private @Setter @Getter String title;

	/** 내용 */
	private @Setter @Getter String content;

	/** 작성자 */
	private @Setter @Getter String writer;

	/** 조회 수 */
	private @Setter @Getter int viewCnt;

	/** 공지 여부 */
	private @Setter @Getter String noticeYn;

	/** 비밀 여부 */
	private @Setter @Getter String secretYn;
    
}
