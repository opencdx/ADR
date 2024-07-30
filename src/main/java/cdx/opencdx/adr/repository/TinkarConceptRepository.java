package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.TinkarConceptModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The TinkarConceptRepository interface provides methods for accessing and manipulating TinkarConceptModel objects.
 */
@Repository
public interface TinkarConceptRepository extends JpaRepository<TinkarConceptModel, Long> {

    /**
     * Checks if a Tinkar concept exists by its concept ID.
     *
     * @param conceptId The ID of the concept to check.
     * @return true if a concept with the specified ID exists, false otherwise.
     */
    boolean existsByConceptId(UUID conceptId);

    /**
     * Retrieves a TinkarConceptModel object based on the given conceptId.
     *
     * @param conceptId The UUID representing the conceptId.
     * @return The TinkarConceptModel object corresponding to the conceptId, or null if not found.
     */
    TinkarConceptModel findByConceptId(UUID conceptId);


    /**
     * Retrieves a TinkarConceptModel object based on the given description.
     *
     * @param conceptDescription The description of the concept to search for.
     * @return The TinkarConceptModel object corresponding to the description, or null if not found.
     */
    TinkarConceptModel findByConceptDescription(String conceptDescription);
}
