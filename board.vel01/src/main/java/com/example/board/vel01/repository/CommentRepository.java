package com.example.board.vel01.repository;


import com.example.board.vel01.domain.Comment;
import com.example.board.vel01.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {

    /* 게시글 댓글 목록 가져오기 */
    List<Comment> getCommentByPostsOrderById(Post posts);
}
