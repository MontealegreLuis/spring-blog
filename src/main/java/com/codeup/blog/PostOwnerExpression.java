/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostOwnerExpression {
    private Posts posts;

    @Autowired
    public PostOwnerExpression(Posts posts) {
        this.posts = posts;
    }

    public boolean isAuthor(User currentUser, Long postId) {
        Post post = posts.findOne(postId);
        return post.isAuthoredBy(currentUser);
    }
}