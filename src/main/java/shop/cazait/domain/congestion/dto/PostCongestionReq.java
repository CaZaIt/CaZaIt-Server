package shop.cazait.domain.congestion.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@ApiModel(value = "등록 또는 수정할 혼잡도", description = "등록 또는 수정할 혼잡도 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostCongestionReq {

    @ApiModelProperty(value = "혼잡도 상태")
    private CongestionStatus congestionStatus;

}
