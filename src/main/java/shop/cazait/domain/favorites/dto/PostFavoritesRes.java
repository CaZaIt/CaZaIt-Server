package shop.cazait.domain.favorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;

@ApiModel(value = "즐겨찾기 정보", description = "등록한 즐겨찾기 ID를 가진 DTO")
@Builder(access = AccessLevel.PRIVATE)
public class PostCafeFavoritesRes {

    @ApiModelProperty(value = "즐겨찾기 ID")
    private Long id;

    public static PostCafeFavoritesRes of(Long cafeFavoritesId) {
        return PostCafeFavoritesRes.builder()
                .id(cafeFavoritesId)
                .build();
    }

}
