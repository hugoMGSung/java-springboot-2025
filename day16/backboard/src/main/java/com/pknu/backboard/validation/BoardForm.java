package com.pknu.backboard.validation;

import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {

    @Size(max = 250)
    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    // 파일업로드 관련 필드
    private String fileOriginalName; // 업로드한 실제 파일명, 같은이름의 파일이 중복될 수 있음
    private String fileStoredName;  // 서버에 저장된 실제이름
    private String filePath;        // 파일 저장위치
}
