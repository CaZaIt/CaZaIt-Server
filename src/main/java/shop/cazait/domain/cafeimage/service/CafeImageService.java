package shop.cazait.domain.cafeimage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafeimage.dto.PostCafeImageReq;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.repository.CafeImageRepository;
import shop.cazait.domain.cafeimage.exception.CafeImageException;
import shop.cazait.global.error.status.ErrorStatus;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeImageService {

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;

    public void addCafeImage(Long id, PostCafeImageReq cafeImageReq) throws CafeException {
        Cafe cafe = cafeRepository.findById(id).orElseThrow(() -> new CafeException(ErrorStatus.INVALID_CAFE_ID));
        CafeImage cafeImage = CafeImage.builder()
                .cafe(cafe)
                .imageUrl(cafeImageReq.getImageUrl())
                .build();
        cafeImageRepository.save(cafeImage);
    }

    public void deleteCafeImage(Long id) throws CafeImageException {
        CafeImage cafeImage = cafeImageRepository.findById(id).orElseThrow(() -> new CafeImageException(ErrorStatus.INVALID_CAFE_IMAGE_ID));
        cafeImageRepository.deleteById(id);
    }
}
