package com.example.board_upload.dto.upload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {

    private String uuid;
    private String filesName;
    private boolean img;

    // 나중에 JSON으로 처리될 때 link라는 속성으로 자동처리됨
    public String getLink(){
        if(img){
            return "s_"+uuid+"_"+filesName; // 이미지인 경우에 썸네일
        }else {
            return uuid+"_"+filesName;
        }
    }
}
