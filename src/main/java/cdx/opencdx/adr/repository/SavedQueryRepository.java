package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.SavedQueryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedQueryRepository extends JpaRepository<SavedQueryModel, Long> {
}
