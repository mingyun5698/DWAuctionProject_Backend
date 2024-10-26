package com.example.AuctionProject.comment.controller;

import com.example.AuctionProject.auction.repository.AuctionRepository;
import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.auth.security.UserRoleEnum;
import com.example.AuctionProject.board.repository.BoardRepository;
import com.example.AuctionProject.comment.dto.CommentDto;
import com.example.AuctionProject.comment.entitiy.Comment;
import com.example.AuctionProject.comment.repository.CommentRepository;
import com.example.AuctionProject.comment.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("id") Long id) {
        if(userDetails.getUser().getUserType() != UserRoleEnum.ADMIN) return ResponseEntity.badRequest().body("권한이 없습니다.");

        Comment comment = commentService.createComment(commentDto, userDetails, id);
        CommentDto commentDto1 = commentService.toDto(comment);

        return ResponseEntity.ok(commentDto1);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> viewComment(@RequestParam("id") Long id) {
        List<Comment> commentList= commentRepository.findAllByBoard(boardRepository.findById(id).get());
        List<CommentDto> commentDtoList = commentService.toDtoList(commentList);

        return ResponseEntity.ok(commentDtoList);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id")Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails.getUser().getUserType() != UserRoleEnum.ADMIN) return ResponseEntity.badRequest().body("권한이 없습니다.");
        System.out.println(id);
        commentRepository.deleteById(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }


}
