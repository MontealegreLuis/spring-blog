/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.services;

import com.codeup.models.Post;
import com.codeup.repositories.PostsRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private PostsRepository repository;

    public PostService(PostsRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "single-post", key = "#id")
    public Post findOnePost(long id) {
        return repository.findOne(id);
    }

    @Cacheable(value = "all-posts")
    public Iterable<Post> findAllPosts() {
        return repository.findAll();
    }

    @CacheEvict(value = "all-posts", allEntries = true)
    @Cacheable(value = "single-post", key = "#post.id")
    public void save(Post post) {
        repository.save(post);
    }

    @CacheEvict(value = "all-posts", allEntries = true)
    @CachePut(value = "single-post", key = "#post.id")
    public void update(Post post) {
        repository.save(post);
    }

    @Caching(evict = {
        @CacheEvict(value = "single-post", key = "#id"),
        @CacheEvict(value = "all-posts", allEntries = true)
    })
    public void deletePostWith(Long id) {
        repository.delete(id);
    }
}
