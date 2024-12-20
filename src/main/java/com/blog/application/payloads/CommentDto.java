package com.blog.application.payloads;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String userId;
    private String content;
    private LocalDateTime dateTime;
}
