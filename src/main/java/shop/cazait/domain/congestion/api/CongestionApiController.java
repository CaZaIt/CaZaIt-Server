package shop.cazait.domain.congestion.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.congestion.dto.PostCongestionReq;
import shop.cazait.domain.congestion.dto.PostCongestionRes;
import shop.cazait.domain.congestion.service.CongestionService;
import shop.cazait.global.common.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/congestions")
public class CongestionApiController {

    private final CongestionService congestionService;

    @PostMapping("/{masterId}/{cafeId}")
    public BaseResponse<PostCongestionRes> addCongestion(@PathVariable(name = "masterId") Long masterId,
                                                         @PathVariable(name = "cafeId") Long cafeId,
                                                         @RequestBody PostCongestionReq postCongestionReq) {

        // masterId 유효 확인
        // cafeId NULL 확인
        // PostCongestionReq NULL 확인

        PostCongestionRes postCongestionRes = congestionService.addAndUpdateCongestion(cafeId, postCongestionReq);
        return new BaseResponse<>(postCongestionRes);

    }

}
