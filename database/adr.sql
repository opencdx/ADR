-- Participant Table
CREATE TABLE DimReference (
                              id BIGSERIAL PRIMARY KEY,
                              identifier TEXT,
                              display TEXT,
                              reference TEXT,
                              uri TEXT
);

-- Practitioner Table
CREATE TABLE DimLogicalExpression (
                                      id BIGSERIAL PRIMARY KEY,
                                      expression TEXT
);

-- AssociatedStatement Table
CREATE TABLE DimMeasure (
                            id BIGSERIAL PRIMARY KEY,
                            upper_bound DOUBLE PRECISION,
                            lower_bound DOUBLE PRECISION,
                            include_upper_bound BOOLEAN,
                            include_lower_bound BOOLEAN,
                            semantic_id BIGINT REFERENCES DimLogicalExpression(id),
                            resolution DOUBLE PRECISION
);

-- Reference Table
CREATE TABLE DimParticipant (
                             id BIGSERIAL PRIMARY KEY,
                             part_id UUID,
                             practitioner_value_id BIGINT REFERENCES DimReference(id),
                             code_id BIGINT REFERENCES DimLogicalExpression(id)
);

-- Measure Table
CREATE TABLE DimPractitioner (
                              id BIGSERIAL PRIMARY KEY,
                              pract_id UUID,
                              practitioner_value_id BIGINT REFERENCES DimReference(id),
                              code_id BIGINT REFERENCES DimLogicalExpression(id)
);

-- Repetition Table
CREATE TABLE DimAssociatedStatement (
                                     id BIGSERIAL PRIMARY KEY,
                                     state_id BIGINT REFERENCES DimReference(id),
                                     semantic_id BIGINT REFERENCES DimLogicalExpression(id)
);

-- LogicalExpression Table (for all logical expressions)
CREATE TABLE DimRepetition (
                            id BIGSERIAL PRIMARY KEY,
                            period_start_id BIGINT REFERENCES DimMeasure(id),
                            period_duration_id BIGINT REFERENCES DimMeasure(id),
                            event_frequency_id BIGINT REFERENCES DimMeasure(id),
                            event_separation_id BIGINT REFERENCES DimMeasure(id),
                            event_duration_id BIGINT REFERENCES DimMeasure(id)
);

-- PerformanceCircumstance Table
CREATE TABLE FactPerformanceCircumstance (
                                         id BIGSERIAL PRIMARY KEY,
                                         timing_id BIGINT REFERENCES DimMeasure(id),
                                         status_id BIGINT REFERENCES DimLogicalExpression(id),
                                         result_id BIGINT REFERENCES DimMeasure(id),
                                         health_risk_id BIGINT REFERENCES DimLogicalExpression(id),
                                         normal_range_id BIGINT REFERENCES DimMeasure(id)
);

-- RequestCircumstance Table
CREATE TABLE FactRequestCircumstance (
                                     id BIGSERIAL PRIMARY KEY,
                                     timing_id BIGINT REFERENCES DimMeasure(id),
                                     priority_id BIGINT REFERENCES DimLogicalExpression(id),
                                     requested_result_id BIGINT REFERENCES DimMeasure(id),
                                     repetition_id BIGINT REFERENCES DimRepetition(id)
);

-- NarrativeCircumstance Table
CREATE TABLE FactNarrativeCircumstance (
                                       id BIGSERIAL PRIMARY KEY,
                                       timing_id BIGINT REFERENCES DimMeasure(id),
                                       text TEXT
);

-- ANFStatement Table (Main Table)
CREATE TABLE DimANFStatement (
                              id BIGSERIAL PRIMARY KEY,
                              anfId UUID NOT NULL,
                              time_id BIGINT REFERENCES DimMeasure(id),
                              subject_of_record_id BIGINT REFERENCES DimParticipant(id),
                              subject_of_information_id BIGINT REFERENCES DimLogicalExpression(id),
                              topic_id BIGINT REFERENCES DimLogicalExpression(id),
                              type_id BIGINT REFERENCES DimLogicalExpression(id),
                              performance_circumstance_id BIGINT REFERENCES FactPerformanceCircumstance(id),
                              request_circumstance_id BIGINT REFERENCES FactRequestCircumstance(id),
                              narrative_circumstance_id BIGINT REFERENCES FactNarrativeCircumstance(id)
);

-- Union Tables (Many-to-Many Relationships)
CREATE TABLE UnionPerformanceCircumstance_Purpose (
                                                 performance_circumstance_id BIGINT REFERENCES FactPerformanceCircumstance(id),
                                                 purpose_id BIGINT REFERENCES DimLogicalExpression(id),
                                                 PRIMARY KEY (performance_circumstance_id, purpose_id)
);
CREATE TABLE UnionPerformanceCircumstance_Participant (
                                                     performance_circumstance_id BIGINT REFERENCES FactPerformanceCircumstance(id),
                                                     participant_id BIGINT REFERENCES DimParticipant(id),
                                                     PRIMARY KEY (performance_circumstance_id, participant_id)
);
CREATE TABLE UnionRequestCircumstance_Purpose (
                                             request_circumstance_id BIGINT REFERENCES FactRequestCircumstance(id),
                                             purpose_id BIGINT REFERENCES DimLogicalExpression(id),
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
                                               purpose_id BIGINT REFERENCES DimLogicalExpression(id),
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

