package shop.cazait.domain.menu.api;

import static shop.cazait.global.error.status.SuccessStatus.CREATE_MENU;
import static shop.cazait.global.error.status.SuccessStatus.NO_CONTENT_SUCCESS;
import static shop.cazait.global.error.status.SuccessStatus.SUCCESS;

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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.cazait.domain.cafe.exception.CafeException;
import shop.cazait.domain.menu.dto.request.MenuUpdateInDTO;
import shop.cazait.domain.menu.dto.response.MenuListOutDTO;
import shop.cazait.domain.menu.dto.response.MenuUpdateOutDTO;
import shop.cazait.domain.menu.dto.request.MenuCreateInDTO;
import shop.cazait.domain.menu.dto.response.MenuCreateOutDTO;
import shop.cazait.domain.menu.service.MenuService;
import shop.cazait.global.common.dto.response.FailResponse;
import shop.cazait.global.common.dto.response.SuccessResponse;
import shop.cazait.global.config.encrypt.NoAuth;
import shop.cazait.global.error.status.SuccessStatus;

@Tag(name = "카페 메뉴 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuApiController {

    private final MenuService menuService;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * 카페 메뉴 등록
     */
    @Operation(summary = "카페 메뉴 등록", description = "카페 ID와 메뉴에 대한 정보를 받아 등록한다.")
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
    @PostMapping(value ="/cafe/{cafeId}")
    public SuccessResponse<MenuCreateOutDTO> createMenu(
            @RequestBody MenuCreateInDTO menuCreateInDTO
    ) throws CafeException {
        return new SuccessResponse<>(CREATE_MENU, menuService.createMenu(menuCreateInDTO));
    }

    @NoAuth
    @Operation(summary = "카페 메뉴 조회", description = "카페 ID를 받아 해당 카페에 대한 모든 메뉴를 조회한다.")
    @Parameter(name = "cafeId", description = "카페 ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "메뉴 조회 완료"),
            @ApiResponse(responseCode = "204", description = "메뉴 조회 완료(단, 등록된 메뉴 없음)"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 요청"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 카페"),
    })
    @GetMapping("/cafe/{cafeId}")
    public SuccessResponse<List<MenuListOutDTO>> getMenu(@PathVariable UUID cafeId) {

        List<MenuListOutDTO> result = menuService.getMenu(cafeId);
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
    public SuccessResponse<MenuUpdateOutDTO> updateMenu(@RequestBody MenuUpdateInDTO menuUpdateInDTO)
            throws IOException {
        return new SuccessResponse<>(SUCCESS, menuService.updateMenu(menuUpdateInDTO));
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

        return new SuccessResponse<>(SUCCESS, menuService.deleteMenu(menuId));

    }

}
