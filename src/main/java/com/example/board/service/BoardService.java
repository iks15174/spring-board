package com.example.board.service;

import java.util.List;
import com.example.board.domain.BoardDTO;
import com.example.board.paging.Criteria;

public interface BoardService{

    public boolean registerBoard(BoardDTO params);

    public BoardDTO getBoardDetail(Long idx);

    public boolean deleteBoard(Long idx);

    public List<BoardDTO> getBoardList(BoardDTO params);
}
