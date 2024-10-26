package com.example.AuctionProject.comment.dto;

import com.example.AuctionProject.board.dto.BoardDto;
import com.example.AuctionProject.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;

    private String contents;
    private UserDto userDto;
    private BoardDto boardDto;
}
