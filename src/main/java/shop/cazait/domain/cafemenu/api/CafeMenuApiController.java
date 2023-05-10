package shop.cazait.domain.cafemenu.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_MENU;
import static shop.cazait.global.error.status.SuccessStatus.NO_CONTENT_SUCCESS;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.cafemenu.dto.request.MenuUpdateInDTO;
import shop.cazait.domain.cafemenu.dto.response.MenuListOutDTO;
import shop.cazait.domain.cafemenu.dto.response.MenuUpdateOutDTO;
import shop.cazait.domain.cafemenu.dto.request.MenuCreateInDTO;
import shop.cazait.domain.cafemenu.dto.response.MenuCreateOutDTO;
import shop.cazait.domain.cafemenu.service.CafeMenuService;
import shop.cazait.global.common.dto.response.FailResponse;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.error.status.SuccessStatus;

@Tag(name = "카페 메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class CafeMenuApiController {

    private final CafeMenuService cafeMenuService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * 카페 메뉴 등록
     */
    @Operation(summary = "카페 메뉴 등록", description = "카페 ID와 메뉴에 대한 정보를 받아 등록한다.")
    @Parameter(name = "cafeId", description = "메뉴를 추가할 카페에 대한 id 입력")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "메뉴 등록 성공",
                    content = @Content(schema = @Schema(implementation = MenuCreateOutDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 요청",
                    content = @Content(schema = @Schema(implementation = FailResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 카페",
                    content = @Content(schema = @Schema(implementation = FailResponse.class))
            ),
    })
    @PostMapping(value ="/cafe/{cafeId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<MenuCreateOutDTO> registerMenu(@PathVariable Long cafeId,
                                                          @Parameter(description = "메뉴 정보", example = "{\"name\": \"아메리카노\", \"description\": \"맛있어!\", \"price\": 4500}")
                                                         @RequestParam @Valid String information,
                                                          @Parameter(description = "메뉴 이미지") @RequestPart(required = false) MultipartFile image)
            throws CafeException, IOException {
        MenuCreateInDTO menuCreateInDTO = objectMapper.readValue(information, new TypeReference<>() {});
        return new SuccessResponse<>(CREATE_MENU, cafeMenuService.registerMenu(cafeId, menuCreateInDTO, image));
    }

    @Operation(summary = "카페 메뉴 조회", description = "카페 ID를 받아 해당 카페에 대한 모든 메뉴를 조회한다.")
    @Parameter(name = "cafeId", description = "카페 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메뉴 조회 완료"),
            @ApiResponse(responseCode = "204", description = "메뉴 조회 완료(단, 등록된 메뉴 없음)"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 카페"),
    })
    @GetMapping("/cafe/{cafeId}")
    public SuccessResponse<List<MenuListOutDTO>> getMenu(@PathVariable Long cafeId) {

        List<MenuListOutDTO> result = cafeMenuService.getMenu(cafeId);
        SuccessStatus resultStatus = SUCCESS;
        if (result == null) {
            resultStatus = NO_CONTENT_SUCCESS;
        }

        return new SuccessResponse<>(resultStatus, result);
    }

    @Operation(summary = "카페 메뉴 수정", description = "카페 메뉴 ID를 받아 수정한다.")
    @Parameter(name = "menuId", description = "카페 메뉴 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메뉴 수정 완료"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 카페"),
    })
    @PatchMapping("/{menuId}")
    public SuccessResponse<MenuUpdateOutDTO> updateMenu(@PathVariable Long menuId,
                                                        @Parameter(description = "수정할 메뉴 정보 : {\"name\": \"아메리카노\", \"description\": \"맛있어!\", \"price\": 4500}")
                                                        @RequestParam @Valid String menuInfo,
                                                        @Parameter(description = "수정할 메뉴 이미지") @RequestPart(required = false) MultipartFile menuImage)
            throws IOException {
        MenuUpdateInDTO menuUpdateInDTO = objectMapper.readValue(menuInfo, new TypeReference<>() {});
        return new SuccessResponse<>(SUCCESS, cafeMenuService.updateMenu(menuId, menuUpdateInDTO, menuImage));
    }

    @Operation(summary = "카페 메뉴 삭제", description = "카페 메뉴 ID를 받아 삭제한다.")
    @Parameter(name = "menuId", description = "메뉴 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메뉴 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 메뉴"),
    })
    @DeleteMapping("/{menuId}")
    public SuccessResponse<String> updateMenu(@PathVariable Long menuId) {

        return new SuccessResponse<>(SUCCESS, cafeMenuService.deleteMenu(menuId));

    }

}
