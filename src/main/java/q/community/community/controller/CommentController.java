package q.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import q.community.community.dto.CommentDTO;
import q.community.community.dto.ResultDTO;
import q.community.community.exception.CustomizeErrorCode;
import q.community.community.mapper.CommentMapper;
import q.community.community.model.Comment;
import q.community.community.model.User;
import q.community.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.PrimitiveIterator;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorof(CustomizeErrorCode.NO_LOGIN);
        }


        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);

        return ResultDTO.okof();
    }

}
