package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.ParticipantModel;
import cdx.opencdx.grpc.data.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantModel, Long> {
}
