package sflow.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sflow.api.entity.Extension;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {

    List<Extension> findAllByDefaultCheckIsTrue();
    List<Extension> findAllByDefaultCheckIsFalse();
    Optional<Extension> findByExtensionName(String extensionName);
    Integer countAllByDefaultCheckIsFalse();
    List<Extension> findAllByOrderByDefaultCheckDesc();
}
