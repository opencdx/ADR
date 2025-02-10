## Install instructions

Please follow the OpenCDx install instructions prior to running this project as all prerequisites are the same.

https://github.com/opencdx/opencdx

## Deployment Procedures

Running `./start_docker.sh` will start the docker containers for the project.
If Jmeter is installed, running `./adr-connect.sh` will populate sample data.

## Sample Submit ANF

```
curl --location 'localhost:8080/anf' \
--header 'Content-Type: application/json' \
--data '{
  "time" : {
    "upperBound" : "100",
    "lowerBound" : "0.0",
    "includeUpperBound" : true,
    "includeLowerBound" : true,
    "semantic" : "expression",
    "resolution" : "1.0"
  },
  "subjectOfRecord" : {
    "id" : "0df076fd-ed02-44bd-a311-b5764c53258c",
    "practitionerValue" : "",
    "code" : ""
  },
  "authors" : [ {
    "id" : "668c57a6e78f91431a4e2d88",
    "practitionerValue" : "practitioner",
    "code" : "expression"
  } ],
  "subjectOfInformation" : "0df076fd-ed02-44bd-a311-b5764c53258c",
  "associatedStatement" : [ {
    "id" : "",
    "semantic" : "Associated description"
  } ],
  "topic" : "ffc2d03c-e34b-4d98-a9e4-f152e2b7d4b2",
  "type" : "50373000",
  "performanceCircumstance" : {
    "timing" : {
      "upperBound" : "",
      "lowerBound" : "",
      "includeUpperBound" : false,
      "includeLowerBound" : false,
      "semantic" : "",
      "resolution" : ""
    },
    "purpose" : [ ],
    "status" : "{\"expressionType\":\"simple\",\"expressionLanguage\":\"local\",\"expressionValue\":\"performed\",\"expressionDescription\":\"Measurement action has been performed.\"}",
    "result" : {
      "upperBound" : "120",
      "lowerBound" : "90",
      "includeUpperBound" : false,
      "includeLowerBound" : false,
      "semantic" : "",
      "resolution" : "{{REPLACE_3079919224534}}"
    },
    "healthRisk" : "${{rules.engine.calculated[circumstanceChoice.result]}}",
    "normalRange" : {
      "upperBound" : "",
      "lowerBound" : "",
      "includeUpperBound" : false,
      "includeLowerBound" : false,
      "semantic" : "",
      "resolution" : ""
    },
    "participant" : [ {
      "id" : "",
      "practitionerValue" : "",
      "code" : ""
    } ]
  }
}
'
```
# Query Available fields
```
curl --location 'localhost:8080/query' \
--data ''
```