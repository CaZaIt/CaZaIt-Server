package shop.cazait.domain.master.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.cazait.domain.master.entity.Master;

@Getter
@Setter
@Builder
public class PostRegisterRes {

    private  String email;
    private  String nickname;

    static public PostRegisterRes toDto(Master master) {
        return PostRegisterRes.builder()
                .email(master.getEmail())
                .nickname(master.getNickname())
                .build();
    }

}
