package com.example.mybatis.mapper;

import com.example.mybatis.entity.UserEntity;
import com.example.mybatis.enums.UserSexEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Spring在BeanFactoryPostProcessor时，将BeanClass替换为MapperFactoryBean.class
 * https://blog.csdn.net/gebitan505/article/details/54929287
 * user_sex、nick_name两个属性在数据库加了下划线和实体类属性名不一致，另外user_sex使用了枚举
 * @Result 修饰返回的结果集 如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰
 */
public interface UserMapper {
    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    List<UserEntity> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    UserEntity getOne(Long id);

    @Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
    void insert(UserEntity user);

    @Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);
}
