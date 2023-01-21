package shop.cazait.domain.cafeimage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.cazait.domain.cafe.entity.Cafe;
import shop.cazait.domain.cafeimage.dto.PostCafeImageReq;
import shop.cazait.domain.cafeimage.entity.CafeImage;
import shop.cazait.domain.cafe.repository.CafeRepository;
import shop.cazait.domain.cafeimage.repository.CafeImageRepository;
import shop.cazait.global.error.exception.BaseException;
import shop.cazait.global.error.status.ErrorStatus;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CafeImageService {

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;

    public void addCafeImage(Long id, PostCafeImageReq cafeImageReq) throws BaseException {
        Optional<Cafe> cafe = cafeRepository.findById(id);
        if (cafe.isPresent()) {
            CafeImage cafeImage = CafeImage.builder()
                    .cafe(cafe.get())
                    .imageUrl(cafeImageReq.getImageUrl())
                    .build();
            cafeImageRepository.save(cafeImage);
        }
        else {
            throw new BaseException(ErrorStatus.INVALID_CAFE_ID);
        }
    }

    public void deleteCafeImage(Long id) throws BaseException {
        Optional<CafeImage> cafeImage = cafeImageRepository.findById(id);
        if (cafeImage.isPresent()) {
            cafeImageRepository.deleteById(id);
        } else {
            throw new BaseException(ErrorStatus.INVALID_CAFE_IMAGE_ID);
        }
    }
}
