package shop.cazait.domain.cafefavorites.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCafeFavoritesRes {

    private Long cafeId;
    private String name;
    private String imageUrl;

    @Builder
    public GetCafeFavoritesRes(Long cafeId, String name, String imageUrl) {
        this.cafeId = cafeId;
        this.name = name;
        this.imageUrl = imageUrl;
    }


}
