package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.ReferenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

@Repository
public interface ReferenceRepository extends JpaRepository<ReferenceModel, Long> {
}
