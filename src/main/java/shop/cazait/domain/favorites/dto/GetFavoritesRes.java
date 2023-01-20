package shop.cazait.domain.favorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import shop.cazait.domain.cafe.entity.CafeImage;
import shop.cazait.domain.favorites.entity.Favorites;

@ApiModel(value = "즐겨찾기 한 카페 정보", description = "즐겨찾기로 등록한 모든 카페에 대한 정보")
@Builder(access = AccessLevel.PROTECTED)
public class GetFavoritesRes {

    @ApiModelProperty(value = "즐겨찾기 ID")
    private Long favoritesId;
    @ApiModelProperty(value = "카페 ID")
    private Long cafeId;
    @ApiModelProperty(value = "카페 이름")
    private String name;
    @ApiModelProperty(value = "카페 이미지")
    private List<String> imageUrl = new ArrayList<>();

    public static List<GetFavoritesRes> of(List<Favorites> findFavorites) {
        return findFavorites.stream()
                .map(favorites -> {
                    return GetFavoritesRes.builder()
                            .favoritesId(favorites.getId())
                            .cafeId(favorites.getCafe().getId())
                            .name(favorites.getCafe().getName())
                            .imageUrl(favorites.getCafe().getCafeImage().stream()
                                    .map(CafeImage::getImageUrl)
                                    .collect(Collectors.toList()))
                            .build();
                }).collect(Collectors.toList());
    }

}
