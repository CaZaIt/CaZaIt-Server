package shop.cazait.domain.congestion.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCongestionReq {

    private CongestionStatus congestionStatus;

}
