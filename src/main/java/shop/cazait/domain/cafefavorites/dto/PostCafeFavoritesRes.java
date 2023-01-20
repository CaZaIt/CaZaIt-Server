package shop.cazait.domain.cafefavorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@ApiModel(value = "즐겨찾기 정보", description = "등록한 즐겨찾기 ID를 가진 DTO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCafeFavoritesRes {

    @ApiModelProperty(value = "즐겨찾기 ID")
    private Long id;

    @Builder
    public PostCafeFavoritesRes(Long id) {
        this.id = id;
    }

}
