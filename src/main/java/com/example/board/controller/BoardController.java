package com.example.board.controller;

import java.util.List;

import com.example.board.constant.Method;
import com.example.board.domain.BoardDTO;
import com.example.board.paging.Criteria;
import com.example.board.service.BoardService;
import com.example.board.util.UiUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import groovy.transform.Undefined.EXCEPTION;

@Controller
public class BoardController extends UiUtils{
    
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/board/write.do")
    public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model){
        if(idx == null){
            model.addAttribute("board", new BoardDTO());
        }else{
            BoardDTO board = boardService.getBoardDetail(idx);
            if(board == null){
                return "redirect:/board/list.do";
            }
            model.addAttribute("board", board);
        }
        return "board/write";
    }

    @PostMapping(value = "/board/register.do")
    public String registerBoard(final BoardDTO params, Model model){
        try{
            boolean isRegistered = boardService.registerBoard(params);
            if(isRegistered = false){
                return showMessageWithRedirect("게시글 등록에 실패햐였습니다.", "/board/list.do", Method.GET, null, model);
            }
        }catch(DataAccessException E){
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생했습니다.", "/board/list.do", Method.GET, null, model);
        }catch(Exception e){
            return showMessageWithRedirect("시스템에 실패햐였습니다.", "/board/list.do", Method.GET, null, model);
        }
        return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/board/list.do", Method.GET, null, model);
    }

    @GetMapping(value = "/board/list.do")
    public String openBoardList(@ModelAttribute("params") BoardDTO params, Model model){
        List<BoardDTO> boardList = boardService.getBoardList(params);
        model.addAttribute("boardList", boardList);

        return "board/list";
    }

    @GetMapping(value = "/board/view.do")
    public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model){
        if(idx == null){
            //TODO =>예외 처리
            return "redirect:/board/list.do";
        }
        BoardDTO board = boardService.getBoardDetail(idx);
        if(board == null || "Y".equals(board.getDeleteYn())){

            return "redirect:/board/list.do";
        }
        model.addAttribute("board", board);
        return "board/view";
    }

    @PostMapping(value = "/board/delete.do")
    public String deleteBoard(@RequestParam(value = "idx", required = false) Long idx, Model model){
        if(idx == null){
            return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
        }

        try{
            boolean isDeleted = boardService.deleteBoard(idx);
            if(isDeleted == false){
                return showMessageWithRedirect("게시글 삭제에 실패했습니다.", "/board/list.do", Method.GET, null, model);
            }
        }catch(DataAccessException e){
            return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생했습니다.", "/board/list.do", Method.GET, null, model);
        }catch(Exception e){
            return showMessageWithRedirect("시스템에 문제가 발생했습니다.", "/board/list.do", Method.GET, null, model);
        }
        return showMessageWithRedirect("게시글 삭제가 완료되었습니다.", "/board/list.do", Method.GET, null, model);
    }
}
