package sflow.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 모든(Custom, Default) 확장자를 나타내는 entity
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Extension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String extensionName;
    /**
     * Default와 Custom 확장자를 구분하는 필드
     * Default = true, Custom = false로 설정
     */
    private boolean defaultCheck;
    /**
     * 확장자의 활성화 여부를 체크하는 필드
     * true일 경우 해당 확장자 활성화
     */
    private boolean activeCheck;

    @Builder
    public Extension(String extensionName, boolean defaultCheck, boolean activeCheck) {
        this.extensionName = extensionName;
        this.defaultCheck = defaultCheck;
        this.activeCheck = activeCheck;
    }

    /**
     * 활성화 여부(boolean)를 기존과 반대로 바꾸는 메소드
     */
    public void changeActiveCheck() {
        this.activeCheck = !this.activeCheck;
    }
}
