-- Dimension Tables

-- Represents medical concepts (diseases, procedures, etc.)
CREATE TABLE DimTinkarConcept (
                                  id BIGSERIAL PRIMARY KEY,  -- Unique identifier
                                  concept_id UUID,           -- External concept identifier (likely a UUID)
                                  concept_name TEXT,         -- Name of the concept
                                  concept_description TEXT   -- Description of the concept
);

-- Represents references (e.g., medical literature, guidelines)
CREATE TABLE DimReference (
                              id BIGSERIAL PRIMARY KEY,      -- Unique identifier
                              identifier TEXT,               -- Reference identifier (e.g., DOI, PMID)
                              display TEXT,                 -- Display name for the reference
                              reference TEXT,               -- Full reference text
                              uri TEXT                      -- URI if applicable
);

-- Represents measurements (e.g., lab results, vital signs)
CREATE TABLE DimMeasure (
                            id BIGSERIAL PRIMARY KEY,          -- Unique identifier
                            upper_bound DOUBLE PRECISION,     -- Upper limit of the measurement range
                            lower_bound DOUBLE PRECISION,     -- Lower limit of the measurement range
                            include_upper_bound BOOLEAN,      -- Indicates if the upper bound is inclusive
                            include_lower_bound BOOLEAN,      -- Indicates if the lower bound is inclusive
                            semantic_id BIGINT REFERENCES DimTinkarConcept(id), -- Concept associated with the measurement
                            resolution DOUBLE PRECISION       -- Resolution of the measurement (e.g., decimal places)
);

-- Represents participants (patients, subjects) in medical events
CREATE TABLE DimParticipant (
                                id BIGSERIAL PRIMARY KEY,          -- Unique identifier
                                part_id UUID,                     -- External participant identifier (likely a UUID)
                                practitioner_value_id BIGINT REFERENCES DimReference(id), -- Reference to the participant's practitioner
                                code_id BIGINT REFERENCES DimTinkarConcept(id)  -- Concept representing the participant's role
);

-- Represents practitioners (doctors, nurses, etc.)
CREATE TABLE DimPractitioner (
                                 id BIGSERIAL PRIMARY KEY,          -- Unique identifier
                                 pract_id UUID,                     -- External practitioner identifier (likely a UUID)
                                 practitioner_value_id BIGINT REFERENCES DimReference(id), -- Reference to the practitioner's information
                                 code_id BIGINT REFERENCES DimTinkarConcept(id)  -- Concept representing the practitioner's role
);

-- Represents associated statements (e.g., conditions, observations)
CREATE TABLE DimAssociatedStatement (
                                        id BIGSERIAL PRIMARY KEY,              -- Unique identifier
                                        state_id BIGINT REFERENCES DimReference(id),  -- Reference to the associated statement
                                        semantic_id BIGINT REFERENCES DimTinkarConcept(id)  -- Concept representing the associated statement
);

-- Represents repetitions of medical events (e.g., medication schedules)
CREATE TABLE DimRepetition (
                               id BIGSERIAL PRIMARY KEY,                  -- Unique identifier
                               period_start_id BIGINT REFERENCES DimMeasure(id),      -- Measurement representing the start of the period
                               period_duration_id BIGINT REFERENCES DimMeasure(id),    -- Measurement representing the duration of the period
                               event_frequency_id BIGINT REFERENCES DimMeasure(id),    -- Measurement representing the frequency of events
                               event_separation_id BIGINT REFERENCES DimMeasure(id),   -- Measurement representing the separation between events
                               event_duration_id BIGINT REFERENCES DimMeasure(id)     -- Measurement representing the duration of each event
);

-- Represents circumstances surrounding the performance of a medical action
CREATE TABLE FactPerformanceCircumstance (
                                             id BIGSERIAL PRIMARY KEY,              -- Unique identifier
                                             timing_id BIGINT REFERENCES DimMeasure(id),      -- Timing of the performance
                                             status_id BIGINT REFERENCES DimTinkarConcept(id),  -- Status of the performance (e.g., completed, in progress)
                                             result_id BIGINT REFERENCES DimMeasure(id),      -- Result of the performance (if applicable)
                                             health_risk_id BIGINT REFERENCES DimTinkarConcept(id), -- Health risk associated with the performance
                                             normal_range_id BIGINT REFERENCES DimMeasure(id)    -- Normal range for the performance (if applicable)
);

-- Represents circumstances surrounding a medical request
CREATE TABLE FactRequestCircumstance (
                                         id BIGSERIAL PRIMARY KEY,          -- Unique identifier
                                         timing_id BIGINT REFERENCES DimMeasure(id),  -- Timing of the request
                                         priority_id BIGINT REFERENCES DimTinkarConcept(id), -- Priority of the request
                                         requested_result_id BIGINT REFERENCES DimMeasure(id), -- Requested result of the action
                                         repetition_id BIGINT REFERENCES DimRepetition(id) -- Repetition pattern for the request (if applicable)
);

-- Represents narrative circumstances surrounding a medical event
CREATE TABLE FactNarrativeCircumstance (
                                           id BIGSERIAL PRIMARY KEY,      -- Unique identifier
                                           timing_id BIGINT REFERENCES DimMeasure(id), -- Timing of the narrative
                                           text TEXT                     -- Narrative text
);

-- This table stores the atomic narrative findings. It is a fact table that relates to the various dimensions and fact tables
CREATE TABLE DimANFStatement (
                                 id BIGSERIAL PRIMARY KEY,                      -- Unique identifier
                                 anfId UUID NOT NULL,                            -- External statement identifier (likely a UUID)
                                 time_id BIGINT REFERENCES DimMeasure(id),          -- Time of the statement
                                 subject_of_record_id BIGINT REFERENCES DimParticipant(id), -- Participant the statement is about
                                 subject_of_information_id BIGINT REFERENCES DimTinkarConcept(id), -- Concept representing the subject of the information
                                 topic_id BIGINT REFERENCES DimTinkarConcept(id),          -- Main topic of the statement
                                 type_id BIGINT REFERENCES DimTinkarConcept(id),          -- Type of statement (e.g., observation, procedure)
                                 performance_circumstance_id BIGINT REFERENCES FactPerformanceCircumstance(id), -- Circumstances of performance (if applicable)
                                 request_circumstance_id BIGINT REFERENCES FactRequestCircumstance(id),    -- Circumstances of request (if applicable)
                                 narrative_circumstance_id BIGINT REFERENCES FactNarrativeCircumstance(id) -- Narrative circumstances (if applicable)
);

CREATE TABLE UnionANFStatement_TinkarConcept (
                                                 anf_statement_id BIGINT REFERENCES DimANFStatement(id),
                                                 concept_id BIGINT REFERENCES DimTinkarConcept(id),
                                                 PRIMARY KEY (anf_statement_id, concept_id)
);

CREATE TABLE UnionPerformanceCircumstance_Purpose (
                                                 performance_circumstance_id BIGINT REFERENCES FactPerformanceCircumstance(id),
                                                 purpose_id BIGINT REFERENCES DimTinkarConcept(id),
                                                 PRIMARY KEY (performance_circumstance_id, purpose_id)
);
CREATE TABLE UnionPerformanceCircumstance_Participant (
                                                     performance_circumstance_id BIGINT REFERENCES FactPerformanceCircumstance(id),
                                                     participant_id BIGINT REFERENCES DimParticipant(id),
                                                     PRIMARY KEY (performance_circumstance_id, participant_id)
);
CREATE TABLE UnionRequestCircumstance_Purpose (
                                             request_circumstance_id BIGINT REFERENCES FactRequestCircumstance(id),
                                             purpose_id BIGINT REFERENCES DimTinkarConcept(id),
                                             PRIMARY KEY (request_circumstance_id, purpose_id)
);
CREATE TABLE UnionRequestCircumstance_ConditionalTrigger (
                                                        request_circumstance_id BIGINT REFERENCES FactRequestCircumstance(id),
                                                        conditional_trigger_id BIGINT REFERENCES DimAssociatedStatement(id),
                                                        PRIMARY KEY (request_circumstance_id, conditional_trigger_id)
);
CREATE TABLE UnionRequestCircumstance_RequestedParticipant (
                                                          request_circumstance_id BIGINT REFERENCES FactRequestCircumstance(id),
                                                          requested_participant_id BIGINT REFERENCES DimReference(id),
                                                          PRIMARY KEY (request_circumstance_id, requested_participant_id)
);
CREATE TABLE UnionNarrativeCircumstance_Purpose (
                                               narrative_circumstance_id BIGINT REFERENCES FactNarrativeCircumstance(id),
                                               purpose_id BIGINT REFERENCES DimTinkarConcept(id),
                                               PRIMARY KEY (narrative_circumstance_id, purpose_id)
);

CREATE TABLE UnionANFStatement_Authors (
                                      anf_statement_id BIGINT REFERENCES DimANFStatement(id),
                                      author_id BIGINT REFERENCES DimPractitioner(id),
                                      PRIMARY KEY (anf_statement_id, author_id)
);

CREATE TABLE UnionANFStatement_AssociatedStatement (
                                                  anf_statement_id BIGINT REFERENCES DimANFStatement(id),
                                                  associated_statement_id BIGINT REFERENCES DimAssociatedStatement(id),
                                                  PRIMARY KEY (anf_statement_id, associated_statement_id)
);

CREATE TABLE PerformanceCircumstance_DeviceId (
                                                  performance_circumstance_id BIGINT REFERENCES FactPerformanceCircumstance(id),
                                                  deviceId TEXT,
                                                  PRIMARY KEY (performance_circumstance_id, deviceId)
);

CREATE TABLE RequestCircumstance_DeviceId (
                                              request_circumstance_id BIGINT REFERENCES FactRequestCircumstance(id),
                                              deviceId TEXT,
                                              PRIMARY KEY (request_circumstance_id, deviceId)
);

-- DimTinkarConcept
CREATE INDEX idx_tinkarconcept_concept_id ON DimTinkarConcept (concept_id);
CREATE INDEX idx_tinkarconcept_concept_name ON DimTinkarConcept (concept_name);

-- DimReference
CREATE INDEX idx_reference_identifier ON DimReference (identifier);

-- DimMeasure
CREATE INDEX idx_measure_semantic_id ON DimMeasure (semantic_id);

-- DimParticipant
CREATE INDEX idx_participant_part_id ON DimParticipant (part_id);
CREATE INDEX idx_participant_practitioner_value_id ON DimParticipant (practitioner_value_id);
CREATE INDEX idx_participant_code_id ON DimParticipant (code_id);

-- DimPractitioner
CREATE INDEX idx_practitioner_pract_id ON DimPractitioner (pract_id);
CREATE INDEX idx_practitioner_practitioner_value_id ON DimPractitioner (practitioner_value_id);
CREATE INDEX idx_practitioner_code_id ON DimPractitioner (code_id);

-- DimAssociatedStatement
CREATE INDEX idx_associatedstatement_state_id ON DimAssociatedStatement (state_id);
CREATE INDEX idx_associatedstatement_semantic_id ON DimAssociatedStatement (semantic_id);

-- FactPerformanceCircumstance
CREATE INDEX idx_performancecircumstance_timing_id ON FactPerformanceCircumstance (timing_id);
CREATE INDEX idx_performancecircumstance_status_id ON FactPerformanceCircumstance (status_id);
CREATE INDEX idx_performancecircumstance_result_id ON FactPerformanceCircumstance (result_id);
CREATE INDEX idx_performancecircumstance_health_risk_id ON FactPerformanceCircumstance (health_risk_id);
CREATE INDEX idx_performancecircumstance_normal_range_id ON FactPerformanceCircumstance (normal_range_id);

-- FactRequestCircumstance
CREATE INDEX idx_requestcircumstance_timing_id ON FactRequestCircumstance (timing_id);
CREATE INDEX idx_requestcircumstance_priority_id ON FactRequestCircumstance (priority_id);
CREATE INDEX idx_requestcircumstance_requested_result_id ON FactRequestCircumstance (requested_result_id);
CREATE INDEX idx_requestcircumstance_repetition_id ON FactRequestCircumstance (repetition_id);

-- FactNarrativeCircumstance
CREATE INDEX idx_narrativecircumstance_timing_id ON FactNarrativeCircumstance (timing_id);

-- PerformanceCircumstance_DeviceId
CREATE INDEX idx_performancecircumstance_deviceid ON PerformanceCircumstance_DeviceId (deviceId);

-- RequestCircumstance_DeviceId
CREATE INDEX idx_requestcircumstance_deviceid ON RequestCircumstance_DeviceId (deviceId);
