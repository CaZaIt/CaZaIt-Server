package shop.cazait.domain.congestion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@ApiModel(value = "등록 또는 수정한 혼잡도", description = "등록 또는 수정 완료한 혼잡도 정보")
@Builder(access = AccessLevel.PRIVATE)
public class PostCongestionRes {

    @ApiModelProperty(value = "혼잡도 ID", example = "1")
    private Long congestionId;

    @ApiModelProperty(value = "카페 ID", example = "1")
    private Long cafeId;

    @ApiModelProperty(value = "혼잡도 상태", example = "free")
    private String congestionStatus;

    public static PostCongestionRes of(Congestion newCongestion) {
        return PostCongestionRes.builder()
                .congestionId(newCongestion.getId())
                .cafeId(newCongestion.getCafe().getId())
                .congestionStatus(newCongestion.getCongestionStatus().getValue())
                .build();
    }

}
