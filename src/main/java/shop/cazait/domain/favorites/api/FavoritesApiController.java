package shop.cazait.domain.favorites.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.favorites.dto.GetFavoritesRes;
import shop.cazait.domain.favorites.dto.PostFavoritesRes;
import shop.cazait.domain.favorites.service.FavoritesService;
import shop.cazait.global.common.response.BaseResponse;

@Api(tags = "즐겨찾기 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoritesApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FavoritesService favoritesService;

    @ApiOperation(value = "즐겨찾기 등록", notes = "사용자 ID와 카페 ID를 받아 즐겨찾기 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID"),
            @ApiImplicitParam(name = "cafeId", value = "카페 ID")
    })
    @PostMapping("/user/{userId}/cafe/{cafeId}")
    public BaseResponse<PostFavoritesRes> addFavorites(@PathVariable(name = "userId") Long userId,
                                                           @PathVariable(name = "cafeId") Long cafeId) {

        PostFavoritesRes postCafeFavoritesRes = favoritesService.addFavorites(userId, cafeId);

        return new BaseResponse<>(postCafeFavoritesRes);

    }

    @GetMapping("/user/{userId}")
    public BaseResponse<List<GetFavoritesRes>> getFavorites(@PathVariable(name = "userId") Long userId) {

       List<GetFavoritesRes> result =  favoritesService.getFavorites(userId);
       return new BaseResponse<>(result);

    }

    @DeleteMapping("/{favoritesId}")
    public BaseResponse<String> deleteFavorites(@PathVariable(name = "favoritesId") Long favoritesId) {

        String result =  favoritesService.deleteFavorites(favoritesId);
        return new BaseResponse<>(result);

    }

}
