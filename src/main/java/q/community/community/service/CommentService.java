package q.community.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import q.community.community.dto.PaginationDTO;
import q.community.community.enums.CommentTypeEnum;
import q.community.community.exception.CustomizeErrorCode;
import q.community.community.exception.CustomizeException;
import q.community.community.mapper.CommentMapper;
import q.community.community.mapper.QuestionMapper;
import q.community.community.model.Comment;
import sun.misc.CompoundEnumeration;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                //评论不存在
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            questionMapper.selectByPrimaryKey(comment.getParentId());
        }
    }
}
