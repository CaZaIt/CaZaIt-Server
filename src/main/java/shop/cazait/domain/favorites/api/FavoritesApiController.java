package shop.cazait.domain.favorites.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = "즐겨찾기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoritesApiController {

    private final FavoritesService favoritesService;

    @ApiOperation(value = "즐겨찾기 등록", notes = "사용자 ID와 카페 ID를 받아 즐겨찾기를 등록한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID"),
            @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    })
    @PostMapping("/user/{userId}/cafe/{cafeId}")
    public SuccessResponse<PostFavoritesRes> addFavorites(@PathVariable(name = "userId") Long userId,
                                                          @PathVariable(name = "cafeId") Long cafeId)
            throws CafeException, UserException {
        return new SuccessResponse<>(favoritesService.addFavorites(userId, cafeId));
    }

    @ApiOperation(value = "즐겨찾기 조회", notes = "사용자 ID를 받아 모든 즐겨찾기를 조회한다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID")
    @GetMapping("/user/{userId}")
    public SuccessResponse<List<GetFavoritesRes>> getFavorites(@PathVariable(name = "userId") Long userId) {
       return new SuccessResponse<>(favoritesService.getFavorites(userId));
    }

    @ApiOperation(value = "즐겨찾기 삭제", notes = "즐겨찾기 ID를 받아 즐겨찾기를 삭제한다.")
    @ApiImplicitParam(name = "favoritesId", value = "즐겨찾기 ID")
    @DeleteMapping("/delete/{favoritesId}")
    public SuccessResponse<String> deleteFavorites(@PathVariable(name = "favoritesId") Long favoritesId) {
        return new SuccessResponse<>(favoritesService.deleteFavorites(favoritesId));
    }

}
