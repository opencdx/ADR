package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.DimParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimParticipantRepository extends JpaRepository<DimParticipant, Integer> {
}
