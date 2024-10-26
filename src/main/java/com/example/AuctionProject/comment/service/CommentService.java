package com.example.AuctionProject.comment.service;

import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.board.repository.BoardRepository;
import com.example.AuctionProject.board.service.BoardService;
import com.example.AuctionProject.comment.dto.CommentDto;
import com.example.AuctionProject.comment.entitiy.Comment;
import com.example.AuctionProject.comment.repository.CommentRepository;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CommentService {

    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Comment createComment(CommentDto commentDto, UserDetailsImpl userDetails, Long id) {
        System.out.println(userDetails.getUser().getUserId());
        Comment comment = new Comment();
        comment.setContents(commentDto.getContents());
        comment.setUser(userDetails.getUser());
        comment.setBoard(boardRepository.findById(id).get());

        commentRepository.save(comment);

        return comment;
    }

    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContents(comment.getContents());
        commentDto.setUserDto(userService.toDto(comment.getUser()));
        commentDto.setBoardDto(boardService.toDto(comment.getBoard()));

        return commentDto;
    }

    public List<CommentDto> toDtoList(List<Comment> commentList) {
        List<CommentDto> commentDtos = new ArrayList<>();

        for(Comment comment : commentList) {
            CommentDto commentDto = toDto(comment);

            commentDtos.add(commentDto);
        } return commentDtos;
    }
}
