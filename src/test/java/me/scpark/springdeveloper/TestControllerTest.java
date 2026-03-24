package me.scpark.springdeveloper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    public void mockMvcSetup(){
        testRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("getAllMembers: 멤버 조회에 성공한다.")
    @Test
    public  void getAllMembers() throws Exception{
        //given(데이터 분비)
        Member saveMember = testRepository.save(new Member("홍길동")) ;

        //when(기능 실행)
        final ResultActions result = mockMvc.perform(get("/test").accept(MediaType.APPLICATION_JSON));

        //then(굘과 검증)
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(saveMember.getId()))
                .andExpect(jsonPath("$[0].name").value(saveMember.getName()));
    }


    @AfterEach
    public void cleanUp(){
        testRepository.deleteAll();
    }
}
