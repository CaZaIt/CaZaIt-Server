package shop.cazait.domain.cafefavorites.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCafeFavoritesRes {

    private String name;
    private String imageUrl;

    @Builder
    public GetCafeFavoritesRes(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }


}
