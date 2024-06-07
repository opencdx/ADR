package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.MeasureModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureRepository extends JpaRepository<MeasureModel, Long> {
}
