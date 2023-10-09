package com.ledger.api_interface.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ledger.api_interface.model.domain.InterfaceInfo;
import org.apache.ibatis.annotations.Param;


/**
* @author 22866
* @description 针对表【interface_info(存储接口信息的表)】的数据库操作Mapper
* @createDate 2023-10-02 14:29:00
* @Entity generator.domain.InterfaceInfo
*/
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    void addCount(@Param("interfaceId") String interfaceId);
}




