/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers.blog;

import com.codeup.blog.Post;
import com.codeup.blog.PostInformation;
import com.codeup.blog.actions.ReadPost;
import com.codeup.blog.actions.UpdatePost;
import com.codeup.infrastructure.web.FlashMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UpdatePostController {
    private ReadPost readPost;
    private UpdatePost updatePost;

    public UpdatePostController(ReadPost readPost, UpdatePost updatePost) {
        this.readPost = readPost;
        this.updatePost = updatePost;
    }

    @GetMapping("/posts/{id}/edit")
    @PreAuthorize("@postOwnerExpression.isAuthor(principal, #id)")
    public String showEditPostForm(@PathVariable Long id, Model viewModel) {
        viewModel.addAttribute("id", id);
        viewModel.addAttribute("postInformation", readPost.readPostWith(id));
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    @PreAuthorize("@postOwnerExpression.isAuthor(principal, #id)")
    public String updatePost(
        @Valid PostInformation postInformation,
        BindingResult validation,
        @PathVariable Long id,
        @RequestParam(name = "image_file") MultipartFile image,
        RedirectAttributes redirect
    ) throws IOException {
        if (validation.hasErrors()) return "posts/edit";

        Post post = updatePost.update(id, postInformation, image);

        redirect.addFlashAttribute("message", FlashMessage.success("Your post has been updated!"));
        return String.format("redirect:/posts/%d", post.getId());
    }
}
