package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.RepetitionModel;
import cdx.opencdx.grpc.data.Repetition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepetitionRepository extends JpaRepository<RepetitionModel, Long> {
}
