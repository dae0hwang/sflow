package sflow.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sflow.api.dto.AllExtensionDto;
import sflow.api.dto.CustomExtensionDto;
import sflow.api.dto.DefaultExtensionDto;
import sflow.api.entity.Extension;
import sflow.api.exceptionadvice.enumforexception.ExtensionExceptionEnum;
import sflow.api.exceptionadvice.exception.DuplicatedNameException;
import sflow.api.exceptionadvice.exception.ExtensionNameException;
import sflow.api.exceptionadvice.exception.OverSizeException;
import sflow.api.repository.ExtensionRepository;

/**
 * 확장자관련 비지니스 로직을 실행하는 service 클래스
 */
@Service
@RequiredArgsConstructor
public class ExtensionService {

    private final ExtensionRepository extensionRepository;

    /**
     * 모든 Default 확장자 리스트를 제공하는 메소드
     */
    @Transactional(readOnly = true)
    public List<DefaultExtensionDto> getDefaultExtensionInfo() {
        List<Extension> findDefaultList = extensionRepository.findAllByDefaultCheckIsTrue();
        return convertToDefaultExtensionDto(findDefaultList);
    }

    /**
     * 모든 Custom 확장자 리스트를 제공하는 메소드
     */
    @Transactional(readOnly = true)
    public List<CustomExtensionDto> getCustomExtensionInfo() {
        List<Extension> findCustomList = extensionRepository.findAllByDefaultCheckIsFalse();
        return convertToCustomExtensionDto(findCustomList);
    }

    /**
     * 등록할 Custom 확장자명을 체크하고 수정하는 메소드
     * 문자열에 영어소문자 숫자를 제외한 문자가 예외를 발생
     * 빈 문자열이 들어와도 예외를 발생
     * 영어 대문자를 소문자로 변경하고 빈칸을 제거하여 수정된 문자열을 반환
     * @param extensionName 사용자가 입력한 확장자명
     * @return 수정된 확장자명
     * @throws ExtensionNameException
     */
    @Transactional
    public String CheckAndModifyName(String extensionName) {
        char[] chars = extensionName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c == 32) {
            } else if (c>=97 && c<=122) {
                sb.append(c);
            } else if (c >= 65 && c <= 90) {
                sb.append(Character.toLowerCase(c));
            } else if (c >= 48 && c <= 57) {
                sb.append(Character.toLowerCase(c));
            } else {
                throw new ExtensionNameException(
                    ExtensionExceptionEnum.NAME_VALIDATION.getErrorMessage());
            }
        }
        if (sb.toString().isEmpty()) {
            throw new ExtensionNameException(
                ExtensionExceptionEnum.NAME_VALIDATION.getErrorMessage());
        } else if (sb.toString().length() > 20) {
            throw new ExtensionNameException(
                ExtensionExceptionEnum.NAME_VALIDATION.getErrorMessage());
        }
        return sb.toString();
    }

    /**
     * 검사하고 수정된 확장자를 저장하는 메소드
     * defaultCheck 필드를 fasle로 설정하여 Custom 확장자로 저장
     * @param extensionName 검사하고 수정된 확장자명
     */
    @Transactional
    public void saveCustomExtension(String extensionName) {
        extensionRepository.save(
            Extension.builder().extensionName(extensionName).defaultCheck(false).activeCheck(true)
                .build());
    }

    /**
     * 중복된 확장자명이 있는 지 검사하는 메소드
     * @param extensionName 중복을 검사할 확장자명
     * @throws DuplicatedNameException
     */
    @Transactional(readOnly = true)
    public void checkDuplicatedExtensionName(String extensionName) {
        Optional<Extension> byExtensionName = extensionRepository.findByExtensionName(
            extensionName);
        if (byExtensionName.isPresent()) {
            throw new DuplicatedNameException(
                ExtensionExceptionEnum.DUPLICATED_NAME.getErrorMessage());
        }
    }

    /**
     * 저장된 Custom 확장자 개수가 200개 초과인지 검사하는 메소드
     * @throws OverSizeException
     */
    @Transactional(readOnly = true)
    public void checkCustomExtensionNumber() {
        Integer count = extensionRepository.countAllByDefaultCheckIsFalse();
        if (count >= 200) {
            throw new OverSizeException(ExtensionExceptionEnum.OVER_SIZE.getErrorMessage());
        }
    }

    /**
     * 지정된 확장자를 제거하는 메소드
     * @param id 지정된 확장자 db id
     */
    @Transactional
    public void deleteExtensionById(Long id) {
        extensionRepository.deleteById(id);
    }

    /**
     * 지정된 확장자의 활성화 여부를 변경하는 메소드
     * 기존 true -> false 또는 기존 false -> true
     * @param id 지정된 확장자 db id
     */
    @Transactional
    public void changeDefaultActive(Long id) {
        Extension findDefaultExtension = extensionRepository.findById(id).orElseThrow();
        findDefaultExtension.changeActiveCheck();
    }

    /**
     * 모든(Default, Custom) 확장자 리스트를 반환하는 메소드
     * Default 확장자가 앞에 오게 정렬한 리스트를 제공
     * @return 정렬된 모든 확장 리스트
     */
    @Transactional(readOnly = true)
    public List<AllExtensionDto> getAllExtensionsOrderedByDefaultCheck() {
        List<Extension> entityList = extensionRepository.findAllByOrderByDefaultCheckDesc();
        List<AllExtensionDto> list = new ArrayList<>();
        for (Extension entity : entityList) {
            list.add(AllExtensionDto.builder().id(entity.getId())
                .extensionName(entity.getExtensionName())
                .activeCheck(entity.isActiveCheck()).defaultCheck(entity.isDefaultCheck()).build());
        }
        return list;
    }

    /**
     * Default 확장자 entity list를 dto list로 변경하는 메소드
     * @param extensionList Default 확장자 entity list
     * @return Default 확장자 dto list
     */
    private List<DefaultExtensionDto> convertToDefaultExtensionDto(List<Extension> extensionList) {
        List<DefaultExtensionDto> defaultExtensionDtoList = new ArrayList<>();
        for (Extension extension : extensionList) {
            DefaultExtensionDto build = DefaultExtensionDto.builder().id(extension.getId())
                .extensionName(extension.getExtensionName())
                .defaultCheck(extension.isDefaultCheck()).activeCheck(
                    extension.isActiveCheck()).build();
            defaultExtensionDtoList.add(build);
        }
        return defaultExtensionDtoList;
    }

    /**
     * Custom 확장자 entity list를 dto list로 변경하는 메소드
     * @param extensionList Custom 확장자 entity list
     * @return Custom 확장자 dto list
     */
    private List<CustomExtensionDto> convertToCustomExtensionDto(List<Extension> extensionList) {
        List<CustomExtensionDto> customExtensionDtoList = new ArrayList<>();
        for (Extension extension : extensionList) {
            CustomExtensionDto build = CustomExtensionDto.builder().id(extension.getId())
                .extensionName(extension.getExtensionName())
                .defaultCheck(extension.isDefaultCheck()).activeCheck(
                    extension.isActiveCheck()).build();
            customExtensionDtoList.add(build);
        }
        return customExtensionDtoList;
    }
}
