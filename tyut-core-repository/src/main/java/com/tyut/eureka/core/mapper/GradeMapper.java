package com.tyut.eureka.core.mapper;

import com.tyut.eureka.core.pojo.Grade;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Grade record);

    int insertSelective(Grade record);

    Grade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);
}