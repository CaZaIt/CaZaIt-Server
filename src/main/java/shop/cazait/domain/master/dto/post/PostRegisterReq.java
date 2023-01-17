package shop.cazait.domain.master.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.master.entity.Master;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRegisterReq {

    private  String email;
    private String password;
    private  String nickname;

    public Master toEntity() {
        return Master.builder()
                .email(getEmail())
                .password(getPassword())
                .nickname(getNickname())
                .build();
    }

}
