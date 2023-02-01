package shop.cazait.domain.favorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.favorites.entity.Favorites;

@ApiModel(value = "즐겨찾기 Response", description = "즐겨찾기로 등록한 모든 카페에 대한 정보")
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class GetFavoritesRes {

    @ApiModelProperty(value = "즐겨찾기 ID", example = "1")
    private Long favoritesId;
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
    @ApiModelProperty(value = "카페 이미지", example = "image.png")
    private List<String> imageUrl;

    public static List<GetFavoritesRes> of(List<Favorites> findFavorites) {
        return findFavorites.stream()
                .map(favorites -> {
                    return GetFavoritesRes.builder()
                            .favoritesId(favorites.getId())
                            .cafeId(favorites.getCafe().getId())
                            .name(favorites.getCafe().getName())
                            .address(favorites.getCafe().getAddress())
                            .latitude(favorites.getCafe().getCoordinate().getLatitude())
                            .longitude(favorites.getCafe().getCoordinate().getLongitude())
                            .congestion(favorites.getCafe().getCongestion().getCongestionStatus().getValue())
                            .imageUrl(favorites.getCafe().getCafeImage().stream()
                                    .map(CafeImage::getImageUrl)
                                    .collect(Collectors.toList()))
                            .build();
                }).collect(Collectors.toList());
    }

}
