package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.PerformanceCircumstanceModel;
import cdx.opencdx.grpc.data.PerformanceCircumstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceCircumstanceRepository extends JpaRepository<PerformanceCircumstanceModel, Long> {
}
