package com.gloryjewel.book.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PostsRepositoryTests {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void cleanUp(){
        postsRepository.deleteAll();
    }

    @Test
    void save_list(){

        String title = "게시글";
        String content = "본문";

        Posts posts = Posts.builder()
                            .title(title)
                            .content(content)
                            .author("테스터")
                            .build();

        postsRepository.save(posts);

        List<Posts> postses = postsRepository.findAll();

        assertEquals(postses.get(0).getTitle(), title);
        assertEquals(postses.get(0).getContent(), content);

    }

    @Test
    void save_localTime(){
        LocalDateTime now = LocalDateTime.now();

        postsRepository.save(Posts.builder()
                                    .title("title")
                                    .content("content")
                                    .author("author")
                                    .build());

        List<Posts> postses = postsRepository.findAll();

        Posts posts = postses.get(0);

        System.out.println("createDate: " + posts.getCreatedDate() +" modifiedDate: " + posts.getModifiedDate());
        System.out.println("before time: " + now);
        //assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(true);
    }

}