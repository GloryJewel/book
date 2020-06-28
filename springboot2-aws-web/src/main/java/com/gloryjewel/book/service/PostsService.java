package com.gloryjewel.book.service;

import com.gloryjewel.book.domain.posts.Posts;
import com.gloryjewel.book.domain.posts.PostsRepository;
import com.gloryjewel.book.web.dto.PostsListResponseDto;
import com.gloryjewel.book.web.dto.PostsResponseDto;
import com.gloryjewel.book.web.dto.PostsSaveRequestDto;
import com.gloryjewel.book.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = getPosts(id);

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return posts.getId();
    }

    public PostsResponseDto findById(Long id){
        Posts entity = getPosts(id);

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                                            .map(PostsListResponseDto::new)
                                            .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = getPosts(id);

        postsRepository.delete(posts);
    }

    private Posts getPosts(Long id) {
        return postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
    }
}
