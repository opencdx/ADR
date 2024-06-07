package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.NarrativeCircumstanceModel;
import cdx.opencdx.grpc.data.NarrativeCircumstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NarrativeCircumstanceRepository extends JpaRepository<NarrativeCircumstanceModel, Long> {
}
