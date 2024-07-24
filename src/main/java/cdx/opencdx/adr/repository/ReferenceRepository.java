package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.ReferenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The ReferenceRepository interface is a repository for managing instances of the ReferenceModel class.
 * It extends the JpaRepository interface, providing basic CRUD operations.
 */
@Repository
public interface ReferenceRepository extends JpaRepository<ReferenceModel, Long> {
}
