package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.CalculatedConcept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The interface DimCalculatedConceptRepository is a repository interface used for accessing DimCalculatedConcept entities.
 * It extends the JpaRepository interface which provides basic CRUD operations.
 */
@Repository
public interface CalculatedConceptRepository extends JpaRepository<CalculatedConcept, Long> {
    /**
     * Finds all DimCalculatedConcept objects based on the given participant ID and thread name.
     *
     * @param participantId The ID of the participant.
     * @param threadName The name of the thread.
     * @return A List of DimCalculatedConcept objects that match the participant ID and thread name.
     */
    List<CalculatedConcept> findAllByParticipantIdAndThreadName(UUID participantId, String threadName);

    /**
     * Deletes all CalculatedConcept objects with the specified thread name.
     *
     * @param threadName The name of the thread.
     */
    void deleteAllByThreadName(String threadName);


    /**
     * Finds all CalculatedConcept objects with the specified thread name.
     *
     * @param threadName The name of the thread.
     * @return A List of CalculatedConcept objects that match the thread name.
     */
    List<CalculatedConcept> findAllByThreadName(String threadName);
}
