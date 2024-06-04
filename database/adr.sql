-- Participant Dimension Table
CREATE TABLE DimParticipant (
    ParticipantID INT PRIMARY KEY,
    PractitionerValue VARCHAR(255),
    Code VARCHAR(255)
);

-- Practitioner Dimension Table
CREATE TABLE DimPractitioner (
    PractitionerID INT PRIMARY KEY,
    PractitionerValue VARCHAR(255),
    Code VARCHAR(255)
);

-- Measure Dimension Table
CREATE TABLE DimMeasure (
    MeasureID INT PRIMARY KEY,
    UpperBound VARCHAR(255),
    LowerBound VARCHAR(255),
    IncludeUpperBound BOOLEAN,
    IncludeLowerBound BOOLEAN,
    Semantic VARCHAR(255),
    Resolution VARCHAR(255)
);

-- Associated Statement Dimension Table
CREATE TABLE DimAssociatedStatement (
    AssociatedStatementID INT PRIMARY KEY,
    Semantic VARCHAR(255)
);

-- Performance Circumstance Dimension Table
CREATE TABLE DimPerformanceCircumstance (
    PerformanceCircumstanceID INT PRIMARY KEY,
    TimingMeasureID INT,
    Purpose VARCHAR(255),
    Status VARCHAR(255),
    ResultMeasureID INT,
    HealthRisk VARCHAR(255),
    NormalRangeMeasureID INT,
    FOREIGN KEY (TimingMeasureID) REFERENCES DimMeasure(MeasureID),
    FOREIGN KEY (ResultMeasureID) REFERENCES DimMeasure(MeasureID),
    FOREIGN KEY (NormalRangeMeasureID) REFERENCES DimMeasure(MeasureID)
);

-- Request Circumstance Dimension Table
CREATE TABLE DimRequestCircumstance (
    RequestCircumstanceID INT PRIMARY KEY,
    TimingMeasureID INT,
    Purpose VARCHAR(255),
    ConditionalTriggerID INT,
    RequestedParticipantID INT,
    Priority VARCHAR(255),
    RequestedResultMeasureID INT,
    RepetitionID INT,
    FOREIGN KEY (TimingMeasureID) REFERENCES DimMeasure(MeasureID),
    FOREIGN KEY (RequestedResultMeasureID) REFERENCES DimMeasure(MeasureID)
);

-- Narrative Circumstance Dimension Table
CREATE TABLE DimNarrativeCircumstance (
    NarrativeCircumstanceID INT PRIMARY KEY,
    TimingMeasureID INT,
    Purpose VARCHAR(255),
    Text VARCHAR(255),
    FOREIGN KEY (TimingMeasureID) REFERENCES DimMeasure(MeasureID)
);

-- Fact Table
CREATE TABLE FactANFStatement (
    ANFStatementID INT PRIMARY KEY,
    MeasureID INT,
    SubjectOfRecordID INT,
    SubjectOfInformation VARCHAR(255),
    Topic VARCHAR(255),
    Type VARCHAR(255),
    PerformanceCircumstanceID INT,
    RequestCircumstanceID INT,
    NarrativeCircumstanceID INT,
    Created TIMESTAMP,
    Modified TIMESTAMP,
    CreatorID INT,
    ModifierID INT,
    Status VARCHAR(255),
    FOREIGN KEY (MeasureID) REFERENCES DimMeasure(MeasureID),
    FOREIGN KEY (SubjectOfRecordID) REFERENCES DimParticipant(ParticipantID),
    FOREIGN KEY (PerformanceCircumstanceID) REFERENCES DimPerformanceCircumstance(PerformanceCircumstanceID),
    FOREIGN KEY (RequestCircumstanceID) REFERENCES DimRequestCircumstance(RequestCircumstanceID),
    FOREIGN KEY (NarrativeCircumstanceID) REFERENCES DimNarrativeCircumstance(NarrativeCircumstanceID),
    FOREIGN KEY (CreatorID) REFERENCES DimPractitioner(PractitionerID),
    FOREIGN KEY (ModifierID) REFERENCES DimPractitioner(PractitionerID)
);

