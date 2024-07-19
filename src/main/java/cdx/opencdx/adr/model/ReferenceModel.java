package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Reference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "dimreference")
public class ReferenceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dimreference_id_gen")
    @SequenceGenerator(name = "dimreference_id_gen", sequenceName = "dimreference_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "identifier", length = Integer.MAX_VALUE)
    private String identifier;

    @Column(name = "display", length = Integer.MAX_VALUE)
    private String display;

    @Column(name = "reference", length = Integer.MAX_VALUE)
    private String reference;

    @Column(name = "uri", length = Integer.MAX_VALUE)
    private String uri;

    public ReferenceModel(Reference reference, ANFRepo anfRepo) {
        this.identifier = reference.getIdentifier();
        this.display = reference.getDisplay();
        this.reference = reference.getReference();
        this.uri = reference.getUri();
    }
}