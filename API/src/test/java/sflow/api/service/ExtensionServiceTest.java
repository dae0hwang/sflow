package sflow.api.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sflow.api.dto.AllExtensionDto;
import sflow.api.dto.CustomExtensionDto;
import sflow.api.dto.DefaultExtensionDto;
import sflow.api.entity.Extension;
import sflow.api.exceptionadvice.exception.DuplicatedNameException;
import sflow.api.exceptionadvice.exception.ExtensionNameException;
import sflow.api.exceptionadvice.exception.OverSizeException;
import sflow.api.repository.ExtensionRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ExtensionServiceTest {
    @Autowired
    ExtensionService extensionService;
    @SpyBean
    ExtensionRepository extensionRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);

        extensionRepository.save(
            Extension.builder().extensionName("a").defaultCheck(true).activeCheck(true).build());
        extensionRepository.save(
            Extension.builder().extensionName("b").defaultCheck(true).activeCheck(true).build());
        extensionRepository.save(
            Extension.builder().extensionName("c").defaultCheck(true).activeCheck(true).build());
        extensionRepository.save(
            Extension.builder().extensionName("d").defaultCheck(false).activeCheck(true).build());
        extensionRepository.save(
            Extension.builder().extensionName("e").defaultCheck(false).activeCheck(true).build());
        extensionRepository.save(
            Extension.builder().extensionName("f").defaultCheck(false).activeCheck(true).build());
        extensionRepository.save(
            Extension.builder().extensionName("g").defaultCheck(false).activeCheck(true).build());
    }
    @Test
    void getDefaultExtensionInfo() {
        //given
        //when
        List<DefaultExtensionDto> list = extensionService.getDefaultExtensionInfo();
        //then
        Assertions.assertThat(list.size()).isEqualTo(3);
    }

    @Test
    void CheckAndModifyName() {
        //given
        String str1 = "az19";
        String str2 = "ABC";
        String str3 = "  ";
        String str4 = "a   z";
        String str5 = "az&!";
        String str6 = "over 20 character occur nameException";
        //when
        String result1 = extensionService.CheckAndModifyName(str1);
        String result2 = extensionService.CheckAndModifyName(str2);
        String result4 = extensionService.CheckAndModifyName(str4);
        //then
        Assertions.assertThat(result1).isEqualTo("az19");
        Assertions.assertThat(result2).isEqualTo("abc");
        Assertions.assertThatThrownBy(() -> extensionService.CheckAndModifyName(str3)).isInstanceOf(
            ExtensionNameException.class);
        Assertions.assertThat(result4).isEqualTo("az");
        Assertions.assertThatThrownBy(() -> extensionService.CheckAndModifyName(str5)).isInstanceOf(
            ExtensionNameException.class);
        Assertions.assertThatThrownBy(() -> extensionService.CheckAndModifyName(str6)).isInstanceOf(
            ExtensionNameException.class);
    }

    @Test
    void getCustomExtensionInfo() {
        //given
        //when
        List<CustomExtensionDto> list = extensionService.getCustomExtensionInfo();
        //then
        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    void saveCustomExtension() {
        //given
        //when
        extensionService.saveCustomExtension(new String("newExtension"));
        //then
        List<Extension> all = extensionRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(8);
    }

    @Test
    void checkDuplicatedExtensionName() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> extensionService.checkDuplicatedExtensionName("a"))
            .isInstanceOf(DuplicatedNameException.class);
    }

    @Test
    void checkCustomExtensionNumber() {
        //given
        Mockito.doReturn(201).when(extensionRepository).countAllByDefaultCheckIsFalse();
        //when
        //then
        Assertions.assertThatThrownBy(() -> extensionService.checkCustomExtensionNumber())
            .isInstanceOf(OverSizeException.class);
    }

    @Test
    void deleteExtensionById() {
        //given
        Long id = extensionRepository.findByExtensionName("a").orElseThrow().getId();
        //when
        extensionService.deleteExtensionById(id);
        //then
        Assertions.assertThat(extensionRepository.findAll().size()).isEqualTo(6);
    }

    @Test
    void changeDefaultActive() {
        //given
        Long id = extensionRepository.findByExtensionName("a").orElseThrow().getId();
        //when
        extensionService.changeDefaultActive(id);
        //then
        Assertions.assertThat(extensionRepository.findById(id).orElseThrow().isActiveCheck())
            .isEqualTo(false);
    }

    @Test
    void getAllExtensionsOrderedByDefaultCheck() {
        //given
        //when
        List<AllExtensionDto> list = extensionService.getAllExtensionsOrderedByDefaultCheck();
        //then
        Assertions.assertThat(list.size()).isEqualTo(7);
    }
}