package shop.cazait.domain.cafevisit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Access;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@ApiModel(value = "최근 본 카페 정보", description = "최근 본 카페로 등록한 카페 정보")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCafeVisitRes {

    @ApiModelProperty(value = "최근 본 카페 ID")
    private Long cafeVisitId;

    @ApiModelProperty(value = "사용자 닉네임")
    private String userName;

    @ApiModelProperty(value = "방문한 카페 이름")
    private String cafeName;

    @Builder
    public PostCafeVisitRes(Long cafeVisitId, String userName, String cafeName) {
        this.cafeVisitId = cafeVisitId;
        this.userName = userName;
        this.cafeName = cafeName;
    }

}
