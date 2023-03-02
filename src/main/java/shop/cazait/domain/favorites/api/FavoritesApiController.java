package shop.cazait.domain.favorites.api;

import static shop.cazait.global.error.status.SuccessStatus.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.favorites.dto.GetFavoritesRes;
import shop.cazait.domain.favorites.dto.PostFavoritesRes;
import shop.cazait.domain.favorites.service.FavoritesService;
import shop.cazait.domain.user.exception.UserException;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.error.status.SuccessStatus;

@Tag(name = "즐겨찾기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoritesApiController {

    private final FavoritesService favoritesService;

    @PostMapping("/user/{userId}/cafe/{cafeId}")
    @Operation(summary = "즐겨찾기 등록", description = "유저 ID와 카페 ID를 받아 즐겨찾기를 등록한다.")
    @Parameters({
            @Parameter(name = "userId", description = "즐겨찾기를 등록할 유저 ID"),
            @Parameter(name = "cafeId", description = "즐겨찾기로 등록할 카페 ID")
    })
    public SuccessResponse<PostFavoritesRes> addFavorites(@PathVariable Long userId,
                                                          @PathVariable Long cafeId)
            throws CafeException, UserException {
        return new SuccessResponse<>(CREATE_FAVORITES, favoritesService.addFavorites(userId, cafeId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "즐겨찾기 조회", description = "유저 ID를 받아 모든 즐겨찾기를 조회한다.")
    @Parameter(name = "userId", description = "즐겨찾기를 조회할 유저 ID")
    public SuccessResponse<List<GetFavoritesRes>> getFavorites(@PathVariable Long userId) {

        List<GetFavoritesRes> result = favoritesService.getFavorites(userId);
        SuccessStatus resultStatus = SUCCESS;

        if (result == null) {
            resultStatus = NO_CONTENT_SUCCESS;
        }

       return new SuccessResponse<>(resultStatus, result);
    }

    @Operation(summary = "즐겨찾기 삭제", description = "즐겨찾기 ID를 받아 즐겨찾기를 삭제한다.")
    @Parameters({
            @Parameter(name= "userId", description = "즐겨찾기 ID"),
            @Parameter(name= "cafeId", description = "즐겨찾기 ID")
    })
    @DeleteMapping("/delete/{userId}/{cafeId}")
    public SuccessResponse<String> deleteFavorites(@PathVariable Long userId, @PathVariable Long cafeId) {
        return new SuccessResponse<>(SUCCESS, favoritesService.deleteFavorites(userId, cafeId));
    }

}
