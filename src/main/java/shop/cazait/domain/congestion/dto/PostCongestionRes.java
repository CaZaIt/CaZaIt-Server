package shop.cazait.domain.congestion.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCongestionRes {

    private Long congestionId;
    private Long cafeId;
    private CongestionStatus congestionStatus;

    @Builder
    public PostCongestionRes(Long congestionId, Long cafeId, CongestionStatus congestionStatus) {
        this.congestionId = congestionId;
        this.cafeId = cafeId;
        this.congestionStatus = congestionStatus;
    }

}
