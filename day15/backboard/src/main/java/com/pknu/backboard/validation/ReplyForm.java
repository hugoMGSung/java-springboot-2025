package com.pknu.backboard.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyForm {

    @NotBlank(message = "댓글내용은 필수입니다")
    private String content;
}
