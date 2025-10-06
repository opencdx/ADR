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
                                       "subjectOfInformation" : "b8fe0c7e-e22f-478b-a88b-de1deab2c123",
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
                                         "status" : "{\\"expressionType\\":\\"simple\\",\\"expressionLanguage\\":\\"local\\",\\"expressionValue\\":\\"performed\\",\\"expressionDescription\\":\\"Measurement action has been performed.\\"}",
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
                                """)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("0", content);
    }
}