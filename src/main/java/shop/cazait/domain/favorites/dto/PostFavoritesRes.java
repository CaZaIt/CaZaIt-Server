package shop.cazait.domain.favorites.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "즐겨찾기 등록 Response : 등록한 즐겨찾기 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class PostFavoritesRes {

    @Schema(description = "즐겨찾기 ID", example = "1")
    private Long id;

    public static PostFavoritesRes of(Long favoritesId) {
        return PostFavoritesRes.builder()
                .id(favoritesId)
                .build();
    }

}
