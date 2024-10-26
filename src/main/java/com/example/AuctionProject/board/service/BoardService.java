package com.example.AuctionProject.board.service;

import com.example.AuctionProject.auction.service.ImageService;
import com.example.AuctionProject.auth.security.UserDetailsImpl;
import com.example.AuctionProject.board.dto.BoardDto;
import com.example.AuctionProject.board.entity.Board;
import com.example.AuctionProject.board.repository.BoardRepository;
import com.example.AuctionProject.user.entity.User;
import com.example.AuctionProject.user.repository.UserRepository;
import com.example.AuctionProject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    public Board createBoard(BoardDto boardDto, UserDetailsImpl userDetails, MultipartFile file) throws IOException {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new IllegalArgumentException("Invalid user Id"));

        String imagePath = imageService.saveImage(file);

        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContents(boardDto.getContents());
        board.setProductName(boardDto.getProductName());
        board.setProductCategory(boardDto.getProductCategory());
        board.setImagePath(imagePath);
        board.setUser(user);
        board.setCreateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        boardRepository.save(board);

        return board;
    }

    public Board updateBoard(Long id, BoardDto boardDto, MultipartFile file) throws IOException {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()) {
            throw new IllegalArgumentException("Board not found");
        }
        String imagePath = imageService.saveImage(file);
        Board board = optionalBoard.get();

        board.setTitle(boardDto.getTitle());
        board.setContents(boardDto.getContents());
        board.setProductName(boardDto.getProductName());
        board.setProductCategory(boardDto.getProductCategory());
        board.setImagePath(boardDto.getImagePath());
        board.setImagePath(imagePath);

        return boardRepository.save(board);
    }

    public BoardDto toDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setTitle(board.getTitle());
        boardDto.setContents(board.getContents());
        boardDto.setId(board.getId());
        boardDto.setProductName(board.getProductName());
        boardDto.setProductCategory(board.getProductCategory());
        boardDto.setImagePath(board.getImagePath());
        boardDto.setUserDto(userService.toDto(board.getUser()));
        boardDto.setCreateTime(board.getCreateTime());
        return boardDto;
    }

    public List<BoardDto> toDtoList(List<Board> boardList) {

        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board : boardList) {
            BoardDto boardDto = toDto(board);
            boardDtoList.add(boardDto);

        }

        return boardDtoList;
    }
}
