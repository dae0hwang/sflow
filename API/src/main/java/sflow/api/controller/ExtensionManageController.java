package sflow.api.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sflow.api.dto.AllExtensionDto;
import sflow.api.dto.CustomExtensionDto;
import sflow.api.dto.CustomExtensionRequest;
import sflow.api.dto.DefaultActiveRequest;
import sflow.api.dto.DefaultExtensionDto;
import sflow.api.dto.DeleteExtensionRequest;
import sflow.api.service.ExtensionService;

/**
 * 확장자 관련된 요청을 받는 controller 클래스
 */
@RestController
@RequestMapping("/api/extension/manage")
@RequiredArgsConstructor
public class ExtensionManageController {

    private final ExtensionService extensionService;

    /**
     * 모든 Default 확장자 리스트를 제공하는 메소드
     */
    @GetMapping("/default/extensions")
    public ResponseEntity<List<DefaultExtensionDto>> getDefaultExtensionInfo() {
        List<DefaultExtensionDto> defaultExtensionInfo = extensionService.getDefaultExtensionInfo();
        return new ResponseEntity<>(defaultExtensionInfo, HttpStatus.OK);
    }

    /**
     * 추가할 Custom 확장자를 검사하고, DB에 저장하는 메소드
     * 개수 200개 이하, 이름 규칙, 중복검사를 하여 예외가 발생하면 그에 맞는 response 제공
     * 모두 통과하면 DB에 저장
     */
    @PostMapping("/custom/extension")
    public ResponseEntity<Void> addCustomExtension(
        @RequestBody CustomExtensionRequest customExtensionRequest) {
        extensionService.checkCustomExtensionNumber();
        String modifiedName = extensionService.CheckAndModifyName(customExtensionRequest.getName());
        extensionService.checkDuplicatedExtensionName(modifiedName);
        extensionService.saveCustomExtension(modifiedName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 모든 Custom 확장자 리스트를 제공하는 메소드
     */
    @GetMapping("/custom/extensions")
    public ResponseEntity<List<CustomExtensionDto>> getCustomExtensionInfo() {
        List<CustomExtensionDto> customExtensionInfo = extensionService.getCustomExtensionInfo();
        return new ResponseEntity<>(customExtensionInfo, HttpStatus.OK);
    }

    /**
     * 특정 Custom 확장자를 삭제하는 메소드
     */
    @DeleteMapping("custom/extension")
    public ResponseEntity<Void> deleteCustomExtension(
        @RequestBody DeleteExtensionRequest deleteExtensionRequest) {
        extensionService.deleteExtensionById(deleteExtensionRequest.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 특정 Dafault 확장자의 Active(활성화여부)를 변경하는 메소드
     * true -> false or false -> true로 변경
     */
    @PostMapping("default/active")
    public ResponseEntity<Void> changeDefaultExtensionActive(
        @RequestBody DefaultActiveRequest defaultActiveRequest) {
        extensionService.changeDefaultActive(defaultActiveRequest.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 모든(Custom, Default) 확장자 리스트를 제공하는 메소드
     * Default 확장자 우선 정렬하여 리스트틀 제공
     */
    @GetMapping("all/list")
    public ResponseEntity<List<AllExtensionDto>> getAllExtensionList() {
        List<AllExtensionDto> allExtensionDtoList =
            extensionService.getAllExtensionsOrderedByDefaultCheck();
        return new ResponseEntity<>(allExtensionDtoList, HttpStatus.OK);
    }
}
