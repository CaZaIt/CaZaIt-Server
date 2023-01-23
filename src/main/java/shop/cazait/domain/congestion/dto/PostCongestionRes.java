package shop.cazait.domain.congestion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@ApiModel(value = "등록 또는 수정한 혼잡도", description = "등록 또는 수정 완료한 혼잡도 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCongestionRes {

    @ApiModelProperty(value = "혼잡도 ID")
    private Long congestionId;

    @ApiModelProperty(value = "카페 ID")
    private Long cafeId;

    @ApiModelProperty(value = "혼잡도 상태")
    private CongestionStatus congestionStatus;

    @Builder
    public PostCongestionRes(Long congestionId, Long cafeId, CongestionStatus congestionStatus) {
        this.congestionId = congestionId;
        this.cafeId = cafeId;
        this.congestionStatus = congestionStatus;
    }

}
