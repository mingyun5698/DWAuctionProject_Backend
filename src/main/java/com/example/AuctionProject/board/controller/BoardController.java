package com.example.AuctionProject.board.controller;

import com.example.AuctionProject.auction.dto.AuctionDto;
import com.example.AuctionProject.auction.entity.Auction;
import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.auth.security.UserRoleEnum;
import com.example.AuctionProject.board.dto.BoardDto;
import com.example.AuctionProject.board.entity.Board;
import com.example.AuctionProject.board.repository.BoardRepository;
import com.example.AuctionProject.board.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;


    @PostMapping("/board")
    public ResponseEntity<?> createBoard(@ModelAttribute BoardDto boardDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Board board = boardService.createBoard(boardDto, userDetails, file);
        BoardDto boardDto1 = boardService.toDto(board);
        return ResponseEntity.ok(boardDto1);
    }

    @GetMapping("/board")
    public ResponseEntity<?> allBoard() {
        List<Board> boardList = (List<Board>) boardRepository.findAll();
        List<BoardDto> boardDtoList = boardService.toDtoList(boardList);

        return ResponseEntity.ok(boardDtoList);
    }

    // 동적 라우팅으로 인한 단일 게시판 리턴
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDto> idBoard(@PathVariable("id") Long id) {

        Board board = boardRepository.findById(id).orElse(null);
        BoardDto boardDto = boardService.toDto(board);

        return ResponseEntity.ok(boardDto);

    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id")Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if((!userDetails.getUser().getId().equals(boardRepository.findById(id).get().getUser().getId())) && !userDetails.getUser().getUserType().equals(UserRoleEnum.ADMIN)) {
            return ResponseEntity.badRequest().body("삭제 권한이 없습니다.");
        }

        boardRepository.deleteById(id);
        return ResponseEntity.ok("삭제 완료");
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<?> updateBoard(
            @PathVariable("id") Long id,
            @ModelAttribute BoardDto boardDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if((!userDetails.getUser().getId().equals(boardRepository.findById(id).get().getUser().getId())) && !userDetails.getUser().getUserType().equals(UserRoleEnum.ADMIN)) {
            return ResponseEntity.badRequest().body("수정 권한이 없습니다.");
        }

        Board updatedBoard = boardService.updateBoard(id, boardDto, file);
        BoardDto updatedBoardDto = boardService.toDto(updatedBoard);
        return ResponseEntity.ok(updatedBoardDto);
    }

    // 보드 수정시 사용자 확인
    @PostMapping("/checkuser")
    public ResponseEntity<?> checkMember(@RequestBody BoardDto boardDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("검증 : " + boardRepository.findById(boardDto.getId()).get().getUser().getId() + " " + userDetails.getUser().getId());
        if (boardRepository.findById(boardDto.getId()).get().getUser().getUserId().equals(userDetails.getUser().getUserId()) || userDetails.getUser().getUserType().equals(UserRoleEnum.ADMIN)) {
            return ResponseEntity.ok("확인 완료");
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
    }
}
