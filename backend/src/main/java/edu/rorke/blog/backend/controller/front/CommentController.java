package edu.rorke.blog.backend.controller.front;

import edu.rorke.blog.backend.entity.Comment;
import edu.rorke.blog.backend.entity.dto.CommentInfo;
import edu.rorke.blog.backend.entity.oauth.OauthUser;
import edu.rorke.blog.backend.service.CommentService;
import edu.rorke.blog.backend.service.OauthService;
import edu.rorke.blog.backend.util.OauthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Rorke
 * @date 2020/5/3 23:54
 */

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final OauthService oauthService;
    private final OauthUtil oauthUtil;


    public CommentController(CommentService commentService, OauthService oauthService, OauthUtil oauthUtil) {
        this.commentService = commentService;
        this.oauthUtil = oauthUtil;
        this.oauthService = oauthService;
    }

    @GetMapping("/{articleId}")
    public List<CommentInfo> getCommentsOfArticle(@PathVariable int articleId) {
        List<Comment> commentsOfArticle = commentService.getCommentsOfArticle(articleId);
        Map<String, OauthUser> userMap = new HashMap<>(16);
        //TODO: 并发发送获取信息的请求
        commentsOfArticle.forEach(comment -> {
            String infoLink = null, accessToken = comment.getAccessToken();
            if (oauthUtil.GITHUB_PLATFORM.equals(comment.getPlatform())) {
                infoLink = oauthUtil.githubInfoLink;
            }
            if (!userMap.containsKey(accessToken)) {
                OauthUser user = oauthService.getUserInfoFromToken(infoLink, accessToken);
                userMap.put(accessToken, user);
            }
        });
        List<CommentInfo> commentInfos = new ArrayList<>();
        for (Comment comment : commentsOfArticle) {
            CommentInfo commentInfo = new CommentInfo();
            String accessToken = comment.getAccessToken();
            BeanUtils.copyProperties(userMap.get(accessToken), commentInfo);
            BeanUtils.copyProperties(comment, commentInfo);
            commentInfos.add(commentInfo);
        }
        return commentInfos;
    }

    @PostMapping
    public CommentInfo createNewComment(@RequestBody Comment newComment) {
        CommentInfo info = null;
        Comment savedComment = commentService.postNewCommentForArticle(newComment);
        if(savedComment != null){
            if (oauthUtil.GITHUB_PLATFORM.equals(newComment.getPlatform())) {
                String infoLink = oauthUtil.githubInfoLink;
                OauthUser oauthUser = oauthService.getUserInfoFromToken(infoLink,newComment.getAccessToken());
                info = new CommentInfo();
                BeanUtils.copyProperties(oauthUser,info);
                BeanUtils.copyProperties(savedComment,info);
            }
        }
        return info;
    }
}
