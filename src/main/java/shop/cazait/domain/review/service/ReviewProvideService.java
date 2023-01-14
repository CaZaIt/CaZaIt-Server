package shop.cazait.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.cazait.domain.review.repository.ReviewRepository;



@Service
@RequiredArgsConstructor
public class ReviewProvideService {
    private final ReviewRepository reviewRepository;
}
