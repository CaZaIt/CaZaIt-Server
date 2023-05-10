package shop.cazait.domain.favorites.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "즐겨찾기 등록 Response", description = "등록한 즐겨찾기 정보")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class FavoritesCreateOutDTO {

    @Schema(description = "즐겨찾기 ID", example = "1")
    private Long id;

    public static FavoritesCreateOutDTO of(Long favoritesId) {
        return FavoritesCreateOutDTO.builder()
                .id(favoritesId)
                .build();
    }

}
