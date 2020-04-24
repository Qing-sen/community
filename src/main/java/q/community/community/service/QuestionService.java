package q.community.community.service;

import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import q.community.community.dto.PaginationDTO;
import q.community.community.dto.QuestionDTO;
import q.community.community.mapper.QuestionMapper;
import q.community.community.mapper.UserMapper;
import q.community.community.model.Question;
import q.community.community.model.QuestionExample;
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

        Integer totalpage;
        //执行count（）语句，从数据库中查到question表中的数据总条数。
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        if (totalCount % size == 0) {
            totalpage = totalCount / size;
        } else {
            totalpage = totalCount / size + 1;
        }
        //当在地址栏中输入的页码为负数或者超过最大页码数时，进行进行处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalpage) {
            page = totalpage;
        }
        paginationDTO.setpagination(totalpage,page);
        //page——页码    size——一页显示多少
        // size*(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalpage;
        //执行count（）语句，从数据库中查到question表中的数据总条数。
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());

        if (totalCount % size == 0) {
            totalpage = totalCount / size;
        } else {
            totalpage = totalCount / size + 1;
        }

        //当在地址栏中输入的页码为负数或者超过最大页码数时，进行进行处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalpage) {
            page = totalpage;
        }

        paginationDTO.setpagination(totalpage, page);

        //page——页码    size——一页显示多少
        // size*(page-1)
        Integer offset = size * (page - 1);

        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);

        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id   ) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
             //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            //更新
            Question uodatequestion=new Question();
            uodatequestion.setGmtModified(System.currentTimeMillis());
            uodatequestion.setTitle(question.getTitle());
            uodatequestion.setDescription(question.getDescription());
            uodatequestion.setTag(question.getTag());
            QuestionExample example =new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId()) ;
            questionMapper.updateByExampleSelective(uodatequestion, example);
        }
    }
}
