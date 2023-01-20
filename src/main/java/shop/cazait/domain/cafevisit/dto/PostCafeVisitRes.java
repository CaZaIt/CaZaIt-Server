package shop.cazait.domain.cafevisit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafevisit.entity.CafeVisit;

@ApiModel(value = "최근 본 카페 정보", description = "최근 본 카페로 등록한 카페 정보")
@Builder(access = AccessLevel.PRIVATE)
public class PostCafeVisitRes {

    @ApiModelProperty(value = "최근 본 카페 ID")
    private Long cafeVisitId;

    @ApiModelProperty(value = "사용자 닉네임")
    private String userName;

    @ApiModelProperty(value = "방문한 카페 이름")
    private String cafeName;

    public static PostCafeVisitRes of(CafeVisit visitLog) {
        return PostCafeVisitRes.builder()
                .cafeVisitId(visitLog.getId())
                .userName(visitLog.getUser().getNickname())
                .cafeName(visitLog.getCafe().getName())
                .build();
    }

}
