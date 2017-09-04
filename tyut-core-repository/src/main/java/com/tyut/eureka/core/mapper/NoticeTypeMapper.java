package com.tyut.eureka.core.mapper;

import com.tyut.eureka.core.pojo.NoticeType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeType record);

    int insertSelective(NoticeType record);

    NoticeType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeType record);

    int updateByPrimaryKey(NoticeType record);
}