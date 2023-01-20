package shop.cazait.domain.cafevisit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafevisit.entity.CafeVisit;

@ApiModel(value = "등록한 방문 기록", description = "등록한 방문 기록 정보")
@Builder(access = AccessLevel.PRIVATE)
public class PostCafeVisitRes {

    @ApiModelProperty(value = "방문 기록 ID", example = "1")
    private Long cafeVisitId;

    @ApiModelProperty(value = "사용자 닉네임", example = "root")

    private String nickName;

    @ApiModelProperty(value = "방문한 카페 이름", example = "롬곡")
    private String cafeName;

    public static PostCafeVisitRes of(CafeVisit visitLog) {
        return PostCafeVisitRes.builder()
                .cafeVisitId(visitLog.getId())
                .nickName(visitLog.getUser().getNickname())
                .cafeName(visitLog.getCafe().getName())
                .build();
    }

}
