/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.validatingforminput;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkPersonInfoWhenNameMissingNameThenFailure() throws Exception {
        mockMvc.perform(post("/").param("age", "20"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkPersonInfoWhenNameTooShortThenFailure() throws Exception {
        mockMvc.perform(post("/")
                        .param("name", "R")
                        .param("age", "20"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkPersonInfoWhenAgeMissingThenFailure() throws Exception {
        mockMvc.perform(post("/")
                        .param("name", "Rob"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkPersonInfoWhenAgeTooYoungThenFailure() throws Exception {
        mockMvc.perform(post("/")
                        .param("age", "1")
                        .param("name", "Rob"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkPersonInfoWhenValidRequestThenSuccess() throws Exception {
        mockMvc.perform(post("/")
                        .param("name", "Rob")
                        .param("age", "20"))
                .andExpect(model().hasNoErrors());
    }
}
