package shop.cazait.domain.cafefavorites.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCafeFavoritesRes {

    private Long id;

    @Builder
    public PostCafeFavoritesRes(Long id) {
        this.id = id;
    }

}
