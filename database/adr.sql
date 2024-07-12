-- Table for Measure
CREATE TABLE DimMeasure (
                         id BIGSERIAL PRIMARY KEY,
                         upper_bound VARCHAR,
                         lower_bound VARCHAR,
                         include_upper_bound BOOLEAN,
                         include_lower_bound BOOLEAN,
                         semantic VARCHAR,
                         resolution VARCHAR
);

-- Table for Participant
CREATE TABLE DimParticipant (
                             id BIGSERIAL PRIMARY KEY,
                             practitioner_value VARCHAR,
                             code VARCHAR
);

-- Table for Practitioner
CREATE TABLE DimPractitioner (
                              id BIGSERIAL PRIMARY KEY,
                              practitioner_value VARCHAR,
                              code VARCHAR
);

-- Table for AssociatedStatement
CREATE TABLE DimAssociatedStatement (
                                     id BIGSERIAL PRIMARY KEY,
                                     semantic VARCHAR
);

-- Table for Status Enum
CREATE TYPE Status AS ENUM ('STATUS_UNSPECIFIED', 'STATUS_ACTIVE', 'STATUS_DELETED');

-- Table for Repetition
CREATE TABLE DimRepetition (
                            id BIGSERIAL PRIMARY KEY,
                            period_start INTEGER REFERENCES DimMeasure(id),
                            period_duration INTEGER REFERENCES DimMeasure(id),
                            event_frequency INTEGER REFERENCES DimMeasure(id),
                            event_separation INTEGER REFERENCES DimMeasure(id),
                            event_duration INTEGER REFERENCES DimMeasure(id)
);

-- Table for PerformanceCircumstance
CREATE TABLE FactPerformanceCircumstance (
                                         id BIGSERIAL PRIMARY KEY,
                                         timing INTEGER REFERENCES DimMeasure(id),
                                         purpose TEXT[],
                                         status VARCHAR,
                                         result INTEGER REFERENCES DimMeasure(id),
                                         health_risk VARCHAR,
                                         normal_range INTEGER REFERENCES DimMeasure(id)
);

-- Junction table for PerformanceCircumstance Participants
CREATE TABLE UnionPerformanceCircumstanceParticipant (
                                                     performance_circumstance_id INTEGER REFERENCES FactPerformanceCircumstance(id),
                                                     participant_id INTEGER REFERENCES DimParticipant(id),
                                                     PRIMARY KEY (performance_circumstance_id, participant_id)
);

-- Table for RequestCircumstance
CREATE TABLE FactRequestCircumstance (
                                     id BIGSERIAL PRIMARY KEY,
                                     timing INTEGER REFERENCES DimMeasure(id),
                                     purpose TEXT[],
                                     priority TEXT[],
                                     requested_result INTEGER REFERENCES DimMeasure(id),
                                     repetition INTEGER REFERENCES DimRepetition(id)
);

-- Junction table for RequestCircumstance Conditional Triggers
CREATE TABLE UnionRequestCircumstanceConditionalTrigger (
                                                        request_circumstance_id INTEGER REFERENCES FactRequestCircumstance(id),
                                                        associated_statement_id INTEGER REFERENCES DimAssociatedStatement(id),
                                                        PRIMARY KEY (request_circumstance_id, associated_statement_id)
);

-- Junction table for RequestCircumstance Participants
CREATE TABLE UnionRequestCircumstanceParticipant (
                                                 request_circumstance_id INTEGER REFERENCES FactRequestCircumstance(id),
                                                 participant_id INTEGER REFERENCES DimParticipant(id),
                                                 PRIMARY KEY (request_circumstance_id, participant_id)
);

-- Table for NarrativeCircumstance
CREATE TABLE FactNarrativeCircumstance (
                                       id BIGSERIAL PRIMARY KEY,
                                       timing INTEGER REFERENCES DimMeasure(id),
                                       purpose TEXT[],
                                       text VARCHAR
);

-- Table for ANFStatement
CREATE TABLE DimANFStatement (
                              id BIGSERIAL PRIMARY KEY,
                              time INTEGER REFERENCES DimMeasure(id),
                              subject_of_record INTEGER REFERENCES DimParticipant(id),
                              subject_of_information VARCHAR,
                              topic VARCHAR,
                              type VARCHAR,
                              created TIMESTAMP,
                              modified TIMESTAMP,
                              creator VARCHAR,
                              modifier VARCHAR,
                              status VARCHAR
);

-- Junction table for ANFStatement Authors
CREATE TABLE UnionANFStatementAuthors (
                                      anfstatement_id INTEGER REFERENCES DimANFStatement(id),
                                      practitioner_id INTEGER REFERENCES DimPractitioner(id),
                                      PRIMARY KEY (anfstatement_id, practitioner_id)
);

-- Junction table for ANFStatement Associated Statements
CREATE TABLE UnionANFStatementAssociatedStatement (
                                                  anfstatement_id INTEGER REFERENCES DimANFStatement(id),
                                                  associated_statement_id INTEGER REFERENCES DimAssociatedStatement(id),
                                                  PRIMARY KEY (anfstatement_id, associated_statement_id)
);

-- Oneof Circumstance Choice (Performance, Request, Narrative)
CREATE TABLE UnionANFStatementPerformanceCircumstance (
                                                 anfstatement_id INTEGER REFERENCES DimANFStatement(id),
                                                 performance_circumstance_id INTEGER REFERENCES FactPerformanceCircumstance(id),
                                                 PRIMARY KEY (anfstatement_id)
);
CREATE TABLE UnionANFStatementRequestCircumstance (
                                                 anfstatement_id INTEGER REFERENCES DimANFStatement(id),
                                                 request_circumstance_id INTEGER REFERENCES FactRequestCircumstance(id),
                                                 PRIMARY KEY (anfstatement_id)
);
CREATE TABLE UnionANFStatementNarrativeCircumstance (
                                                 anfstatement_id INTEGER REFERENCES DimANFStatement(id),
                                                 narrative_circumstance_id INTEGER REFERENCES FactNarrativeCircumstance(id),
                                                 PRIMARY KEY (anfstatement_id)
);


-- DimMeasure
CREATE INDEX idx_dimmeasure_semantic ON DimMeasure (semantic);

-- dimtinkarconcept
CREATE TABLE dimtinkarconcept (
                                  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                  concept_id UUID NOT NULL,
                                  parent_concept_id UUID,
                                  description TEXT,
                                  "count" BIGINT NOT NULL
);

-- DimParticipant
CREATE INDEX idx_dimparticipant_practitioner_value ON DimParticipant (practitioner_value);
CREATE INDEX idx_dimparticipant_code ON DimParticipant (code);

-- DimPractitioner
CREATE INDEX idx_dimpractitioner_practitioner_value ON DimPractitioner (practitioner_value);
CREATE INDEX idx_dimpractitioner_code ON DimPractitioner (code);

-- DimAssociatedStatement
CREATE INDEX idx_dimassociatedstatement_semantic ON DimAssociatedStatement (semantic);

-- DimRepetition
CREATE INDEX idx_dimrepetition_period_start ON DimRepetition (period_start);

-- FactPerformanceCircumstance
CREATE INDEX idx_factperformancecircumstance_timing ON FactPerformanceCircumstance (timing);
CREATE INDEX idx_factperformancecircumstance_status ON FactPerformanceCircumstance (status);
CREATE INDEX idx_factperformancecircumstance_result ON FactPerformanceCircumstance (result);

-- FactRequestCircumstance
CREATE INDEX idx_factrequestcircumstance_timing ON FactRequestCircumstance (timing);
CREATE INDEX idx_factrequestcircumstance_priority ON FactRequestCircumstance (priority);

-- FactNarrativeCircumstance
CREATE INDEX idx_factnarrativecircumstance_timing ON FactNarrativeCircumstance (timing);

-- DimANFStatement
CREATE INDEX idx_dimanfstatement_time ON DimANFStatement (time);
CREATE INDEX idx_dimanfstatement_subject_of_record ON DimANFStatement (subject_of_record);
CREATE INDEX idx_dimanfstatement_created ON DimANFStatement (created);
CREATE INDEX idx_dimanfstatement_modified ON DimANFStatement (modified);
CREATE INDEX idx_dimanfstatement_status ON DimANFStatement (status);

-- Create a GIN index on the topic field
CREATE INDEX idx_dimanfstatement_topic_fts ON DimANFStatement USING GIN (to_tsvector('english', topic));
