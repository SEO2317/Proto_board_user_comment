package com.example.board.vel01.api;
import com.example.board.vel01.domain.Comment;
import com.example.board.vel01.domain.User;
import com.example.board.vel01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST API Controller
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity saveComment(@PathVariable String id, @RequestBody Comment.Request request,
                                      User user) {
        return ResponseEntity.ok(commentService.save(id, User.Request.toEntity().getNickName(), request));
    }

    /* READ */
    @GetMapping("/posts/{id}/comments")
    public List<Comment> readComments(@PathVariable String id) {
        return commentService.findAll(id);
    }

    /* UPDATE */
    @PutMapping({"/posts/{id}/comments"})
    public ResponseEntity update(@PathVariable String id, @RequestBody Comment.Request request) {
        commentService.update(id, request);
        return ResponseEntity.ok(id);
    }

    /* DELETE */
    @DeleteMapping("/posts/{id}/comments/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
