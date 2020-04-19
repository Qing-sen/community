package q.community.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import q.community.community.dto.PaginationDTO;
import q.community.community.dto.QuestionDTO;
import q.community.community.mapper.QuestionMapper;
import q.community.community.mapper.UserMapper;
import q.community.community.model.Question;
import q.community.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        //执行count（）语句，从数据库中查到question表中的数据总条数。
        Integer totalCount = questionMapper.count();
        paginationDTO.setpagination(totalCount,page,size);

        //当在地址栏中输入的页码为负数或者超过最大页码数时，进行进行处理
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }



        //page——页码    size——一页显示多少
        // size*(page-1)
        Integer offset=size*(page-1);

        List<Question> questions=  questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();


        for (Question question: questions){
            User user =userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }
}
