package shop.cazait.domain.favorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;

@ApiModel(value = "즐겨찾기 정보", description = "등록한 즐겨찾기 ID를 포함")
@Builder(access = AccessLevel.PRIVATE)
public class PostFavoritesRes {

    @ApiModelProperty(value = "즐겨찾기 ID")
    private Long id;

    public static PostFavoritesRes of(Long cafeFavoritesId) {
        return PostFavoritesRes.builder()
                .id(cafeFavoritesId)
                .build();
    }

}
