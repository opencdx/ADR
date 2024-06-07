package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.RequestCircumstanceModel;
import cdx.opencdx.grpc.data.RequestCircumstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestCircumstanceRepository extends JpaRepository<RequestCircumstanceModel, Long> {
}
