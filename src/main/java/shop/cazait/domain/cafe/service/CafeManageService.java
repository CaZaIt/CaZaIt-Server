package shop.cazait.domain.cafe.service;

import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_MASTER;
import static shop.cazait.global.error.status.ErrorStatus.NOT_OPERATE_CAFE;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.model.dto.ImageInformation;
import shop.cazait.domain.cafe.model.dto.response.CafeCreateOutDTO;
import shop.cazait.domain.cafe.model.dto.request.CafeCreateInDTO;
import shop.cazait.domain.cafe.model.dto.response.CafeUpdateOutDTO;
import shop.cazait.domain.cafe.model.dto.response.ManageCafeListOutDTO;
import shop.cazait.domain.cafe.model.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.congestion.entity.Congestion;
import shop.cazait.domain.congestion.entity.CongestionStatus;
import shop.cazait.domain.coordinate.entity.Coordinate;
import shop.cazait.domain.coordinate.service.CoordinateService;
import shop.cazait.domain.master.model.entity.Master;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.common.status.BaseStatus;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeManageService {

    private final CoordinateService coordinateService;

    private final CafeRepository cafeRepository;
    private final MasterRepository masterRepository;

    /**
     * 카페 등록 좌표와 도로명 주소 받기 -> 카페 생성 -> 초기 혼잡도 등록 -> 이미지 S3 업로드 -> 이미지 객체  -> 마스터 계정에 카페 설정
     */
    public CafeCreateOutDTO createCafe(CafeCreateInDTO req)
            throws JsonProcessingException {

        Master master = masterRepository.findById(req.getMasterId())
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));

        // 좌표와 도로명 주소 받기
        Coordinate coordinate = coordinateService.createCoordinate(req);

        // 카페 생성
        Cafe cafe = Cafe.builder()
                .master(master)
                .name(req.getName())
                .address(req.getAddress())
                .coordinate(coordinate)
                .build();
        cafe = initCongestion(cafe);
        cafeRepository.save(cafe);


        return CafeCreateOutDTO.of(cafe);
    }

    private Cafe initCongestion(Cafe cafe) {
        Congestion congestion = Congestion.builder()
                .cafe(cafe)
                .congestionStatus(CongestionStatus.NONE)
                .build();
        cafe.initCongestion(congestion);
        return cafe;
    }

    /**
     *  마스터가 관리중인 카페 조회하기
     */
    public List<ManageCafeListOutDTO> getManageCafes(UUID masterId) {

        Master master = masterRepository.findById(masterId).get();
        List<Cafe> cafes = cafeRepository.findByMasterAndStatus(master, BaseStatus.ACTIVE).orElseGet(null);


        List<ManageCafeListOutDTO> results = cafes.stream()
                .map(cafe -> ManageCafeListOutDTO.of(
                        cafe,
                        cafe.getCafeImage().stream().map(
                                image -> ImageInformation.builder()
                                        .imageId(image.getId())
                                        .url(image.getImageUrl())
                                        .build()).collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());


        return results;

    }



    /**
     *  카페 수정
     */
    public CafeUpdateOutDTO updateCafe(UUID cafeId, UUID masterId, CafeCreateInDTO cafeReq)
            throws CafeException, JsonProcessingException {

        Coordinate coordinate = coordinateService.createCoordinate(cafeReq);

        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));

        if (cafeRepository.findByMasterAndStatus(master, BaseStatus.ACTIVE).isEmpty()) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }

        Cafe updateCafe = cafeRepository.save(cafe);

        return CafeUpdateOutDTO.of(updateCafe);
    }

    /**
     *  카페 삭제
     */
    public void deleteCafe(UUID cafeId, UUID masterId) throws CafeException {

        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));

        Master master = masterRepository.findById(masterId)
                .orElseThrow(() -> new CafeException(NOT_EXIST_MASTER));

        if (cafeRepository.findByMasterAndStatus(master, BaseStatus.ACTIVE).isEmpty()) {
            throw new CafeException(NOT_OPERATE_CAFE);
        }

        cafe.changeStatus(BaseStatus.INACTIVE);
        cafeRepository.save(cafe);

    }

}

