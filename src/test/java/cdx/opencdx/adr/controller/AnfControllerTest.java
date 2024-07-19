package cdx.opencdx.adr.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AnfControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void queryTest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(get("/query"))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertFalse(content.isEmpty());
    }

    @Test
    void anfTest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/anf")
                        .content(
                                """
                                    {
                                                "id" : "UUID",
                                                "time" : {
                                                  "upperBound" : "1562631151",
                                                  "lowerBound" : "1562631151",
                                                  "includeUpperBound" : true,
                                                  "includeLowerBound" : true,
                                                  "semantic" : {
                                                    "expression" : "Seconds | 1562631151"
                                                  },
                                                  "resolution" : "seconds"
                                                },
                                                "subjectOfRecord" : {
                                                  "id" : "UUID (PatientId)",
                                                  "practitionerValue" : "",
                                                  "code" : {
                                                    "expression" : "UUID (EncounterID)"
                                                  }
                                                },
                                                "authors" : [ {
                                                  "id" : "6696dcc976cf022c404529a3",
                                                  "practitionerValue" : "practitioner",
                                                  "code" : {
                                                    "expression" : "expression"
                                                  }
                                                } ],
                                                "subjectOfInformation" : {
                                                  "expression" : "UUID  (PatientId)"
                                                },
                                                "associatedStatement" : [ {
                                                  "id" : "",
                                                  "semantic" : {
                                                    "expression" : "Associated description"
                                                  }
                                                } ],
                                                "topic" : {
                                                  "expression" : "255585003 |Malignant neoplasm of liver |finding site|624008003 |Bone structure| "
                                                },
                                                "type" : {
                                                  "expression" : "PERFORMANCE"
                                                },
                                                "performanceCircumstance" : {
                                                  "timing" : {
                                                    "upperBound" : "",
                                                    "lowerBound" : "",
                                                    "includeUpperBound" : false,
                                                    "includeLowerBound" : false,
                                                    "resolution" : ""
                                                  },
                                                  "purpose" : [ {
                                                    "expression" : "purpose"
                                                  } ],
                                                  "status" : {
                                                    "expression" : "{\\"expressionType\\":\\"simple\\",\\"expressionLanguage\\":\\"local\\",\\"expressionValue\\":\\"performed\\",\\"expressionDescription\\":\\"Measurement action has been performed.\\"}"
                                                  },
                                                  "result" : {
                                                    "upperBound" : "120",
                                                    "lowerBound" : "90",
                                                    "includeUpperBound" : false,
                                                    "includeLowerBound" : false,
                                                    "semantic" : {
                                                      "expression" : ""
                                                    },
                                                    "resolution" : "mmHg / cm / in / kg / m / s / L / % / etc."
                                                  },
                                                  "healthRisk" : {
                                                    "expression" : "${{rules.engine.calculated[circumstanceChoice.result]}}"
                                                  },
                                                  "normalRange" : {
                                                    "upperBound" : "",
                                                    "lowerBound" : "",
                                                    "includeUpperBound" : false,
                                                    "includeLowerBound" : false,
                                                    "resolution" : ""
                                                  },
                                                  "participant" : [ {
                                                    "id" : "",
                                                    "practitionerValue" : ""
                                                  } ]
                                                },
                                                "status" : "STATUS_UNSPECIFIED"
                                              }
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("0", content);
    }
}