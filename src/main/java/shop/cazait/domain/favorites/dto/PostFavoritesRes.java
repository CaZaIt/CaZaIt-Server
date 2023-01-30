package shop.cazait.domain.favorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "즐겨찾기 등록 Response", description = "등록한 즐겨찾기 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostFavoritesRes {

    @ApiModelProperty(value = "즐겨찾기 ID", example = "1")
    private Long id;

    public static PostFavoritesRes of(Long favoritesId) {
        return PostFavoritesRes.builder()
                .id(favoritesId)
                .build();
    }

}
