package com.example.board.vel01.service;

import com.example.board.vel01.domain.Comment;
import com.example.board.vel01.domain.Post;
import com.example.board.vel01.domain.User;
import com.example.board.vel01.repository.CommentRepository;
import com.example.board.vel01.repository.PostRepository;
import com.example.board.vel01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpI implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postsRepository;


    @Override
    public String save(String id, String Nickname, Comment.Request request) {
        User user = userRepository.findNickNameBy();
        Post posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + Nickname));

        request.setUser(user);
        request.setPosts(posts);

        Comment comment = Comment.Request.toEntity(request);
        commentRepository.save(comment);

        return comment.getId();
    }

    @Override
    public List<Comment> findAll(String id) {
        Post posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        List<Comment> comment = posts.getComments();
        commentRepository.findAll((Pageable) comment);
        return comment;
    }

    @Override
    public void update(String id, Comment.Request request) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        comment.update(request.getComment());
    }

    @Override
    public void delete(String id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        commentRepository.delete(comment);
    }
}
