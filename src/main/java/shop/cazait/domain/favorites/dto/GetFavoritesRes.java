package shop.cazait.domain.favorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shop.cazait.domain.favorites.entity.Favorites;

@ApiModel(value = "즐겨찾기 한 카페 정보", description = "카페 ID, 이름, 이미지를 포함")
@Builder(access = AccessLevel.PROTECTED)
public class GetFavoritesRes {

    @ApiModelProperty(value = "카페 ID")
    private Long cafeId;
    @ApiModelProperty(value = "카페 이름")
    private String name;
    @ApiModelProperty(value = "카페 이미지")
    private String imageUrl;

    public static List<GetFavoritesRes> of(List<Favorites> findFavorites) {
        return findFavorites.stream()
                .map(favorites -> {
                    return GetFavoritesRes.builder()
                            .cafeId(favorites.getCafe().getId())
                            .name(favorites.getCafe().getName())
                            .imageUrl(favorites.getCafe().getImageUrl())
                            .build();
                }).collect(Collectors.toList());
    }

}
