package shop.cazait.domain.cafevisit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafe.entity.CafeImage;
import shop.cazait.domain.cafevisit.entity.CafeVisit;

@ApiModel(value = "최근 본 카페 리스트 정보", description = "방문 ID, 카페 ID, 이름, 이미지를 포함")
@Builder(access = AccessLevel.PRIVATE)
public class GetCafeVisitRes {

    @ApiModelProperty(value = "방문 ID", example = "1")
    private Long cafeVisitId;

    @ApiModelProperty(value = "카페 ID", example = "1")
    private Long cafeId;

    @ApiModelProperty(value = "카페 이름", example = "롬곡")
    private String name;

    @ApiModelProperty(value = "카페 이미지 URL", example = "image.png")
    private List<String> imageUrl;

    public static List<GetCafeVisitRes> of(List<CafeVisit> visitLogs) {
        return visitLogs.stream()
                .map(visitLog -> GetCafeVisitRes.builder()
                        .cafeVisitId(visitLog.getId())
                        .cafeId(visitLog.getCafe().getId())
                        .name(visitLog.getCafe().getName())
                        .imageUrl(visitLog.getCafe().getCafeImage().stream()
                                .map(CafeImage::getImageUrl)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

}
