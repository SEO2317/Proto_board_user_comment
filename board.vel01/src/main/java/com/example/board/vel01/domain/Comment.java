package com.example.board.vel01.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment {


    @Id
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "created_date")
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String comment) {
        this.comment = comment;
    }

    /* 댓글 수정이 추가를 한다면*/

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private String id;

        private String comment;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

        private User user;

        private Post posts;


        public Request(final Comment commentEntity) {
            this.id = commentEntity.getId();
            this.comment = commentEntity.getComment();
            this.createdDate = commentEntity.getCreatedDate();
            this.modifiedDate = commentEntity.getModifiedDate();
            this.user = commentEntity.getUser();
            this.posts = commentEntity.getPosts();
        }

        public static Comment toEntity(Request request) {
            return Comment.builder()
                    .id(request.getId())
                    .comment(request.getComment())
                    .createdDate(request.getCreatedDate())
                    .modifiedDate(request.getModifiedDate())
                    .user(request.getUser())
                    .posts(request.getPosts())
                    .build();

        }
    }


    @Getter
    @RequiredArgsConstructor
    public static class Response {

        private String id;
        private String comment;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String nickName;
        private String userId;
        private Long postsId;


        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.nickName = comment.getUser().getNickName();
            this.userId = comment.getUser().getId();
            this.postsId = Long.valueOf(comment.getPosts().getId());
        }



        public static List<Comment.Response> toResponseList(final List<Comment> comments){
            List<Comment.Response> list = new ArrayList<>();
            for(Comment comment : comments){
                list.add(new Response(comment));
            }
            return list;
        }
    }
}