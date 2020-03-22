package q.community.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import q.community.community.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (NAME,ACCOUNT_ID,TOKEN,gmt_create,gmt_modified)values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
