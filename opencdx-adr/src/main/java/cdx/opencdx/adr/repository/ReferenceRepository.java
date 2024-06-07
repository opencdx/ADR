package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.ReferenceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceRepository extends JpaRepository<ReferenceModel, Long> {
}
