package com.nteport.admin.mapper;

import com.nteport.admin.entity.system.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * 用户接口
 *
 * @author zzq
 * @since 2020-11-19
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    List<UserEntity> selectUserList(HashMap params);

    List<String> selectTagsById(String userId);

    Long selectDeptLeaderIdById(String userId);

    List<UserEntity> selectAllUserAndDept();

    List<String> queryRoleCodes(String userId);

    List<UserEntity> selectAdminByArea(String areaId);

    List<UserEntity> selectManagerByDept(String deptId);

}
