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

    @ApiModelProperty(value = "카페 주소", example = "서울특별시 광진구 군자동 광나루로17길 18")
    private String address;

    @ApiModelProperty(value = "카페 위도", example = "36.987561")
    private String latitude;

    @ApiModelProperty(value = "카페 경도", example = "127.543215")
    private String longitude;

    @ApiModelProperty(value = "혼잡도", example = "free")
    private String congestion;

    @ApiModelProperty(value = "카페 이미지 URL", example = "image.png")
    private List<String> imageUrl;

    @ApiModelProperty(value = "관심 카페 여부", example = "true")
    private boolean isFavorites;

    public static GetCheckLogRes of(CheckLog log, boolean isFavorites) {

        return GetCheckLogRes.builder()
                .cafeVisitId(log.getId())
                .cafeId(log.getCafe().getId())
                .name(log.getCafe().getName())
                .address(log.getCafe().getAddress())
                .latitude(log.getCafe().getCoordinate().getLatitude())
                .longitude(log.getCafe().getCoordinate().getLongitude())
                .imageUrl(log.getCafe().getCafeImage().stream()
                        .map(CafeImage::getImageUrl)
                        .collect(Collectors.toList()))
                .isFavorites(isFavorites)
                .build();
    }

}
