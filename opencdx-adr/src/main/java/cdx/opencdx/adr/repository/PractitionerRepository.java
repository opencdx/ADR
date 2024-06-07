package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.PractitionerModel;
import cdx.opencdx.grpc.data.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PractitionerRepository extends JpaRepository<PractitionerModel, Long> {
}
