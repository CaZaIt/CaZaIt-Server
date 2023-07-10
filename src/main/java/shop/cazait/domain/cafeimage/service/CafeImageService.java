package shop.cazait.domain.cafeimage.service;

import static shop.cazait.global.error.status.ErrorStatus.*;
import static shop.cazait.global.error.status.ErrorStatus.NOT_EXIST_CAFE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafeimage.dto.request.CafeImageCreateInDTO;
import shop.cazait.domain.cafeimage.dto.response.CafeImageGetOutDTO;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.repository.CafeImageRepository;
import shop.cazait.domain.cafeimage.exception.CafeImageException;
import shop.cazait.domain.master.entity.Master;
import shop.cazait.domain.master.repository.MasterRepository;
import shop.cazait.global.error.status.ErrorStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import shop.cazait.infra.s3.service.S3Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeImageService {

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;
    private final MasterRepository masterRepository;
    private final S3Service S3Service;

    public String createCafeImage(CafeImageCreateInDTO req) throws CafeException {

        Cafe cafe = cafeRepository.findById(req.getCafeId())
                .orElseThrow(() -> new CafeException(NOT_EXIST_CAFE));

        List<CafeImage> cafeImage = req.getImageUrl().stream()
                    .map(url -> CafeImageCreateInDTO.toEntity(cafe, url))
                    .collect(Collectors.toList());

        cafeImageRepository.saveAll(cafeImage);
        return "등록 완료";

    }

    public CafeImageGetOutDTO getCafeImages(Long cafeId) {

        List<CafeImage> cafeImages = cafeImageRepository.findByCafeId(cafeId);
        return CafeImageGetOutDTO.of(cafeImages);

    }

    public void deleteCafeImage(Long cafeImageId, UUID masterId) throws CafeImageException {

        CafeImage cafeImage = cafeImageRepository.findById(cafeImageId)
                .orElseThrow(() -> new CafeImageException(ErrorStatus.NOT_EXIST_IMAGE));

        cafeImageRepository.deleteById(cafeImageId);

    }

}
