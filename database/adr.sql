-- Table for Measure
CREATE TABLE Measure (
                         id SERIAL PRIMARY KEY,
                         upper_bound VARCHAR,
                         lower_bound VARCHAR,
                         include_upper_bound BOOLEAN,
                         include_lower_bound BOOLEAN,
                         semantic VARCHAR,
                         resolution VARCHAR
);

-- Table for Participant
CREATE TABLE Participant (
                             id SERIAL PRIMARY KEY,
                             practitioner_value VARCHAR,
                             code VARCHAR
);

-- Table for Practitioner
CREATE TABLE Practitioner (
                              id SERIAL PRIMARY KEY,
                              practitioner_value VARCHAR,
                              code VARCHAR
);

-- Table for AssociatedStatement
CREATE TABLE AssociatedStatement (
                                     id SERIAL PRIMARY KEY,
                                     semantic VARCHAR
);

-- Table for Reference
CREATE TABLE Reference (
                           id SERIAL PRIMARY KEY,
                           type VARCHAR
);

-- Table for CircumstancePriority Enum
CREATE TYPE CircumstancePriority AS ENUM ('ROUTINE', 'STAT');

-- Table for DurationType Enum
CREATE TYPE DurationType AS ENUM (
    'DURATION_TYPE_NOT_SPECIFIED',
    'DURATION_TYPE_MILLISECONDS',
    'DURATION_TYPE_SECONDS',
    'DURATION_TYPE_MINUTES',
    'DURATION_TYPE_HOURS',
    'DURATION_TYPE_DAYS',
    'DURATION_TYPE_WEEKS',
    'DURATION_TYPE_MONTHS',
    'DURATION_TYPE_YEARS'
);

-- Table for Status Enum
CREATE TYPE Status AS ENUM ('STATUS_UNSPECIFIED', 'STATUS_ACTIVE', 'STATUS_DELETED');

-- Table for Repetition
CREATE TABLE Repetition (
                            id SERIAL PRIMARY KEY,
                            period_start TIMESTAMP,
                            period_duration INTEGER,
                            period_duration_type DurationType,
                            event_frequency INTEGER,
                            event_frequency_type DurationType,
                            event_separation INTEGER,
                            event_separation_type DurationType,
                            event_duration INTEGER,
                            event_duration_type DurationType
);

-- Table for PerformanceCircumstance
CREATE TABLE PerformanceCircumstance (
                                         id SERIAL PRIMARY KEY,
                                         timing INTEGER REFERENCES Measure(id),
                                         purpose TEXT[],
                                         status VARCHAR,
                                         result INTEGER REFERENCES Measure(id),
                                         health_risk VARCHAR,
                                         normal_range INTEGER REFERENCES Measure(id)
);

-- Junction table for PerformanceCircumstance Participants
CREATE TABLE PerformanceCircumstance_Participant (
                                                     performance_circumstance_id INTEGER REFERENCES PerformanceCircumstance(id),
                                                     participant_id INTEGER REFERENCES Participant(id),
                                                     PRIMARY KEY (performance_circumstance_id, participant_id)
);

-- Table for RequestCircumstance
CREATE TABLE RequestCircumstance (
                                     id SERIAL PRIMARY KEY,
                                     timing INTEGER REFERENCES Measure(id),
                                     purpose TEXT[],
                                     priority CircumstancePriority,
                                     requested_result INTEGER REFERENCES Measure(id),
                                     repetition INTEGER REFERENCES Repetition(id)
);

-- Junction table for RequestCircumstance Conditional Triggers
CREATE TABLE RequestCircumstance_ConditionalTrigger (
                                                        request_circumstance_id INTEGER REFERENCES RequestCircumstance(id),
                                                        associated_statement_id INTEGER REFERENCES AssociatedStatement(id),
                                                        PRIMARY KEY (request_circumstance_id, associated_statement_id)
);

-- Junction table for RequestCircumstance Participants
CREATE TABLE RequestCircumstance_Participant (
                                                 request_circumstance_id INTEGER REFERENCES RequestCircumstance(id),
                                                 participant_id INTEGER REFERENCES Participant(id),
                                                 PRIMARY KEY (request_circumstance_id, participant_id)
);

-- Table for NarrativeCircumstance
CREATE TABLE NarrativeCircumstance (
                                       id SERIAL PRIMARY KEY,
                                       timing INTEGER REFERENCES Measure(id),
                                       purpose TEXT[],
                                       text VARCHAR
);

-- Table for ANFStatement
CREATE TABLE ANFStatement (
                              id SERIAL PRIMARY KEY,
                              time INTEGER REFERENCES Measure(id),
                              subject_of_record INTEGER REFERENCES Participant(id),
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
CREATE TABLE ANFStatement_Authors (
                                      anfstatement_id INTEGER REFERENCES ANFStatement(id),
                                      practitioner_id INTEGER REFERENCES Practitioner(id),
                                      PRIMARY KEY (anfstatement_id, practitioner_id)
);

-- Junction table for ANFStatement Associated Statements
CREATE TABLE ANFStatement_AssociatedStatement (
                                                  anfstatement_id INTEGER REFERENCES ANFStatement(id),
                                                  associated_statement_id INTEGER REFERENCES AssociatedStatement(id),
                                                  PRIMARY KEY (anfstatement_id, associated_statement_id)
);

-- Oneof Circumstance Choice (Performance, Request, Narrative)
CREATE TABLE ANFStatement_PerformanceCircumstance (
                                                 anfstatement_id INTEGER REFERENCES ANFStatement(id),
                                                 performance_circumstance_id INTEGER REFERENCES PerformanceCircumstance(id),
                                                 PRIMARY KEY (anfstatement_id)
);
CREATE TABLE ANFStatement_RequestCircumstance (
                                                 anfstatement_id INTEGER REFERENCES ANFStatement(id),
                                                 request_circumstance_id INTEGER REFERENCES RequestCircumstance(id),
                                                 PRIMARY KEY (anfstatement_id)
);
CREATE TABLE ANFStatement_NarrativeCircumstance (
                                                 anfstatement_id INTEGER REFERENCES ANFStatement(id),
                                                 narrative_circumstance_id INTEGER REFERENCES NarrativeCircumstance(id),
                                                 PRIMARY KEY (anfstatement_id)
);