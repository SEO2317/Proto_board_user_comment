package com.example.board.vel01.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter @Setter
@Builder
@Table(name = "user_info")
public class User{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;

    @Column(length = 100, nullable = false)

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pwd;

    private String token;

//    private Role role;

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Request {
    	private String id;
        private String nickName;
        private String pwd;

        public static User toEntity() {
            User user = User.builder()
                    .id(toEntity().id)
                    .nickName(toEntity().nickName)
                    .pwd(toEntity().id)
                    .build();
            return user;
        }
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Response {
        private String id;
        private String nickName;
        private String pwd;
        private String token;


        public static User.Response toResponse(final User user) {
            return User.Response.builder()
                    .id(user.getId())
                    .nickName(user.getNickName())
                    .pwd(user.getPwd())
                    .token(user.getToken())
                    .build();
        }
        public static List<Response> toResponseList(final List<User> users) {
            List<Response> list = new ArrayList<>();
            for (User user : users) {
                list.add(toResponse(user));
            }
            return list;

        }
    }
}