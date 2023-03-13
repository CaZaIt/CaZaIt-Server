package shop.cazait.domain.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.cazait.domain.s3.dto.GetPreSignedUrlRes;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * Pre-Signed Url 생성
     */
    public GetPreSignedUrlRes createPreSignedUrl(String directory) {

        String objectKey = directory + "/" + UUID.randomUUID().toString();
        log.info(objectKey);

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        log.info("Pre-Signed Url Expiration : " + expiration.toString());

        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucket, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL, CannedAccessControlList.PublicRead.toString());

        URL preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        log.info("Pre-Signed URL : " + preSignedUrl.toString());
        return GetPreSignedUrlRes.of(objectKey, preSignedUrl.toString());

    }

    /**
     * 객체 주소 조회
     */
    public String getObjectUrl(String objectKey) {

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        log.info("Pre-Signed Url Expiration : " + expiration.toString());

        GeneratePresignedUrlRequest generatePresignedUrlRequest
                = new GeneratePresignedUrlRequest(bucket, objectKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        String objectUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();

        log.info("Object URL : " + objectUrl);
        return objectUrl.substring(0, objectUrl.lastIndexOf("?"));

    }
}
