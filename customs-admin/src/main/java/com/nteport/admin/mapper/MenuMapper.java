package com.nteport.admin.mapper;

import com.nteport.admin.entity.system.MenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单接口
 */
@Repository
public interface MenuMapper extends BaseMapper<MenuEntity> {

    List getMenu();

    List getMenuByUser(String userId);

    void deleteByMenuId(String menuId);

}
