package shop.cazait.domain.checklog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.checklog.entity.CheckLog;

@ApiModel(value = "조회 기록 Response", description = "조회 기록에 대한 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class GetCheckLogRes {

    @ApiModelProperty(value = "조회 기록 ID", example = "1")
    private Long cafeVisitId;

    @ApiModelProperty(value = "카페 ID", example = "1")
    private Long cafeId;

    @ApiModelProperty(value = "카페 이름", example = "롬곡")
    private String name;

    @ApiModelProperty(value = "카페 주소", example = "서울시 광진구")
    private String address;

    @ApiModelProperty(value = "혼잡도", example = "free")
    private String congestion;

    @ApiModelProperty(value = "카페 이미지 URL", example = "image.png")
    private List<String> imageUrl;

    public static List<GetCheckLogRes> of(List<CheckLog> visitLogs) {
        return visitLogs.stream()
                .map(visitLog -> GetCheckLogRes.builder()
                        .cafeVisitId(visitLog.getId())
                        .cafeId(visitLog.getCafe().getId())
                        .name(visitLog.getCafe().getName())
                        .address(visitLog.getCafe().getLocation())
                        .congestion(visitLog.getCafe().getCongestion().getCongestionStatus().getValue())
                        .imageUrl(visitLog.getCafe().getCafeImage().stream()
                                .map(CafeImage::getImageUrl)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

}
