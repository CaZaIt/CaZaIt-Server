package shop.cazait.domain.auth.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.cazait.domain.auth.config.FeignConfiguration;
import shop.cazait.domain.auth.dto.sens.ExtSensSendMessageCodeInDTO;
import shop.cazait.domain.auth.dto.sens.ExtSensSendMessageCodeOutDTO;


import java.net.URI;

@FeignClient(name="sensClient",configuration = FeignConfiguration.class)
public interface SensClient {
    @PostMapping
    ExtSensSendMessageCodeOutDTO sendMessage(URI baseUrl,
                                                @RequestHeader("x-ncp-apigw-timestamp") String timeStamp,
                                                @RequestHeader("x-ncp-iam-access-key") String accessKey,
                                                @RequestHeader("x-ncp-apigw-signature-v2") String signature,
                                                @RequestBody ExtSensSendMessageCodeInDTO smsRequest);
}
