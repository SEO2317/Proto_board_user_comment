package com.example.board.vel01.service;

import com.example.board.vel01.domain.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    String save(String id, String Nickname, Comment.Request request);

    List<Comment> findAll(String id);

    void update(String id, Comment.Request request);

    void delete(String id);

}
