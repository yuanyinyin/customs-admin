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

    List<String> selectTagsById(Long userId);

    Long selectDeptLeaderIdById(Long userId);

    List<UserEntity> selectAllUserAndDept();

    List<String> queryRoleCodes(Long userId);

    List<UserEntity> selectAdminByArea(Long areaId);

    List<UserEntity> selectManagerByDept(Long deptId);

}
