package com.gloryjewel.book.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloryjewel.book.domain.posts.Posts;
import com.gloryjewel.book.domain.posts.PostsRepository;
import com.gloryjewel.book.web.dto.PostsSaveRequestDto;
import com.gloryjewel.book.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
    }


    @AfterEach
    public void tearDown(){
        postsRepository.deleteAll();
    }

    @WithMockUser(roles = "USER")
    @Test
    void save() throws Exception {

        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)
                                                            .content(content)
                                                            .author("author")
                                                            .build();

        String url = "http://localhost:" + port + "api/v1/posts";

//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);
//
//        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//        assertTrue(responseEntity.getBody() > 0L );

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        List<Posts> postses = postsRepository.findAll();
        assertEquals(postses.get(0).getTitle(), title);
        assertEquals(postses.get(0).getContent(), content);
    }

    @WithMockUser(roles = "USER")
    @Test
    public void update() throws Exception {

        Posts savedPosts = postsRepository.save(Posts.builder()
                                                        .title("title")
                                                        .content("content")
                                                        .author("author")
                                                        .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                                                                .title(expectedTitle)
                                                                .content(expectedContent)
                                                                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

//        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(requestDto);
//
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);
//
//        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
//        assertTrue(responseEntity.getBody() > 0L);

        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        List<Posts> postses = postsRepository.findAll();

        assertEquals(postses.get(0).getTitle(), expectedTitle);
        assertEquals(postses.get(0).getContent(), expectedContent);
    }
}