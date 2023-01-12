package shop.cazait.domain.cafefavorites.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@ApiModel(value = "즐겨찾기 한 카페 정보", description = "카페 ID, 이름, 이미지를 가진 DTO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCafeFavoritesRes {

    @ApiModelProperty(value = "카페 ID")
    private Long cafeId;
    @ApiModelProperty(value = "카페 이름")
    private String name;
    @ApiModelProperty(value = "카페 이미지")
    private String imageUrl;

    @Builder
    public GetCafeFavoritesRes(Long cafeId, String name, String imageUrl) {
        this.cafeId = cafeId;
        this.name = name;
        this.imageUrl = imageUrl;
    }


}
