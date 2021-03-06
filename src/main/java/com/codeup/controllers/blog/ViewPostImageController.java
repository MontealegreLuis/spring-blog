/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.controllers.blog;

import com.codeup.blog.ImageUploader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class ViewPostImageController {
    private ImageUploader uploader;

    public ViewPostImageController(ImageUploader uploader) {
        this.uploader = uploader;
    }

    @GetMapping("/posts/image/{filename:.+}")
    public HttpEntity<byte[]> showPostsImage(@PathVariable String filename) throws IOException {
        byte[] image = uploader.read(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);

        return new HttpEntity<>(image, headers);
    }
}
