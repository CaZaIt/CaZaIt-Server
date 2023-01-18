package shop.cazait.domain.congestion.dto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;

@NoArgsConstructor
@Data
public class PostCongestionReq {

    @ApiParam(value = "혼잡도 상태", required = true, example = "free")
    private String congestionStatus;

    public static Congestion toEntity(Cafe cafe, CongestionStatus congestionStatus) {
        return Congestion.builder()
                .cafe(cafe)
                .congestionStatus(congestionStatus)
                .build();
    }

}
