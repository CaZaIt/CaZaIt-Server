package shop.cazait.domain.cafefavorites.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafefavorites.dto.PostCafeFavoritesRes;
import shop.cazait.domain.cafefavorites.service.CafeFavoritesService;
import shop.cazait.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
public class CafeFavoritesApiController {

    private final CafeFavoritesService cafeFavoritesService;

    @PostMapping("/user/{userId}/favorites/{cafeId}")
    public BaseResponse<PostCafeFavoritesRes> addCafeFavorites(@PathVariable(name = "userId") Long userId,
                                                              @PathVariable(name = "cafeId") Long cafeId) {

        Long resultId = cafeFavoritesService.addCafeFavorites(userId, cafeId);
        PostCafeFavoritesRes postCafeFavoritesRes = PostCafeFavoritesRes.builder()
                .id(resultId)
                .build();

        return new BaseResponse<>(postCafeFavoritesRes);

    }



}
