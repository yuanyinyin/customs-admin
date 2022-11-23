package com.nteport.admin.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.MenuEntity;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.MenuMapper;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.system.IMenuService;
import com.nteport.admin.service.system.IPageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements IMenuService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取菜单列表，带分页
     *
     * @param params
     * @return
     */
    @Transactional
    @Override
    public JSONObject getMenuPageList(Map<String, String> params) {
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            //分页
            long pageSize = Long.parseLong(params.get("limit"));
            long pageNum = Long.parseLong(params.get("page"));
            IPage<MenuEntity> userPage = new Page<>(pageNum, pageSize);
            //组装查询条件
            IPageHelper<MenuEntity> pageHelper = new PageHelperImpl<>();
            QueryWrapper<MenuEntity> queryWrapper = pageHelper.query(params);
            //查询结果
            userPage = menuMapper.selectPage(userPage, queryWrapper);
            List<MenuEntity> list = userPage.getRecords();
            Long total = menuMapper.selectCount(queryWrapper);
            //组装json数据
            data.put("items", list);
            data.put("total", total);
            result.put("code", 20000);
            result.put("data", data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 99999);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }
    }

    /**
     * 获取全部菜单
     *
     * @return
     */
    @Override
    public String getMenuList(Map params) {
        String pid = params.get("pid").toString();
        String title = params.get("title") == null ? "" : String.valueOf(params.get("title"));
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        if (!"".equals(title) && null != title) {
            queryWrapper.like("title", title);
        }
        if (pid.equals("-1")) {
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            queryWrapper.eq("pid", "0");
            List<MenuEntity> topMenus = menuMapper.selectList(queryWrapper);
            for (MenuEntity topMenu : topMenus) {
                sb.append("{");
                sb.append("\"id\":\"" + topMenu.getId() + "\",");
                sb.append("\"title\":\"" + topMenu.getTitle() + "\",");
                if (!getMenu(topMenu.getId()).equals("")) {
                    sb.append("\"children\":[" + getMenu(topMenu.getId()) + "]");
                } else {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("}");
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
            return sb.toString();
        } else {
            queryWrapper.eq("pid", pid);
            List<MenuEntity> list = menuMapper.selectList(queryWrapper);
            return JSONObject.toJSONString(list);
        }
    }

    @Override
    public String getMenu(Long pid) {
        StringBuffer sb = new StringBuffer();
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        List<MenuEntity> menus = menuMapper.selectList(queryWrapper);
        for (int i = 0; i < menus.size(); i++) {
            if (!menus.get(i).getType().equals("3")) {
                sb.append("{");
                sb.append("\"id\":\"" + menus.get(i).getId() + "\",");
                sb.append("\"title\":\"" + menus.get(i).getTitle() + "\",");
                if (!getMenu(menus.get(i).getId()).equals("")) {
                    sb.append("\"children\":[" + getMenu(menus.get(i).getId()) + "]");
                } else {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("}");
                sb.append(",");
                if (menus.size() == (i + 1)) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        return sb.toString();
    }

    @Transactional
    @Override
    public ApiResponse getMenuInfo(String token) {
        ApiResponse response = new ApiResponse();
        List<Map> result = new ArrayList();
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        List<Map> menuList;
        if (!"".equals(token)) {
            queryWrapper.eq("token", token);
            UserEntity userEntity = userMapper.selectOne(queryWrapper);
            menuList = menuMapper.getMenuByUser(userEntity.getId());
        } else {
            menuList = menuMapper.getMenu();
        }
        //拼装返回
        for (Map map : menuList) {
            List<Map> children = new ArrayList<>();
            map.put("id", String.valueOf(map.get("id")));
            map.put("children", children);
            map.put("text", map.get("title").toString());
            map.put("hidden", String.valueOf(map.get("hidden")).equals("0") ? true : false);
            if (0 == Integer.parseInt(String.valueOf(map.get("pid")))) {
                Map<String, Object> meta = new HashMap();
                meta.put("title", map.get("title").toString());
                meta.put("icon", map.get("icon").toString());
                meta.put("roles", new ArrayList<>());
                meta.put("noCache", String.valueOf(map.get("noCache")));
                map.put("meta", meta);
                result.add(map);
            }
        }
        for (Map map : result) {
            recursiveTree(map, menuList);
        }

        response.fillMessage(result);
        return response;
    }

    public static Map recursiveTree(Map parent, List<Map> list) {
        for (Map map : list) {
            if ((null != map.get("pid")) && parent.get("id").toString().equals(map.get("pid").toString())) {
                map = recursiveTree(map, list);
                List<Map> children = (List<Map>) parent.get("children");
                Map<String, Object> meta = new HashMap();
                meta.put("title", map.get("title").toString());
                meta.put("icon", map.get("icon").toString());
                meta.put("roles", new ArrayList<>());
                meta.put("noCache", String.valueOf(map.get("noCache")));
                map.put("meta", meta);
                children.add(map);
                parent.put("children", children);
            }
        }
        return parent;
    }

    /**
     * 创建菜单
     *
     * @param jsonString
     * @param token
     * @return
     */
    @Transactional
    @Override
    public ApiResponse createMenu(String jsonString, String token) {
        ApiResponse response = new ApiResponse();
        try {
            MenuEntity entity = JSON.parseObject(jsonString, MenuEntity.class);
            QueryWrapper<UserEntity> queryUser = new QueryWrapper<>();
            queryUser.eq("token", token);
            UserEntity user = userMapper.selectOne(queryUser);
            if (entity.getId() != null) {
                response.fillMessage("菜单已存在");
                return response;
            } else {
                if (!entity.getType().equals("1")) {
                    // 如果不是顶级目录，将父目录的hasChildren设置不为空
                    QueryWrapper<MenuEntity> queryPMenu = new QueryWrapper<>();
                    queryPMenu.eq("id", entity.getPid());
                    MenuEntity updateMenu = menuMapper.selectOne(queryPMenu);
                    updateMenu.setHasChildren("1");
                    menuMapper.updateById(updateMenu);
                }
                entity.createTimeStamp(user);
                menuMapper.insert(entity);
                response.fillMessage(EnumCode.SUCCESS);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response.fillMessage(EnumCode.ERROR_SERVER);
        }
    }

    /**
     * 修改菜单
     *
     * @param jsonString
     * @param token
     * @return
     */
    @Transactional
    @Override
    public ApiResponse updateMenu(Long id, String jsonString, String token) {
        ApiResponse response = new ApiResponse();
        try {
            if (jsonString.equals("")) {
                response.fillMessage(EnumCode.ERROR_HANDLE.getCode(), "数据为空");
                return response;
            } else {
                MenuEntity entity = JSON.parseObject(jsonString, MenuEntity.class);
                QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper();
                queryWrapper.eq("ID", id);
                MenuEntity exist = menuMapper.selectOne(queryWrapper);
                if (exist != null) {
                    QueryWrapper<UserEntity> queryUser = new QueryWrapper<>();
                    queryUser.eq("token", token);
                    UserEntity user = userMapper.selectOne(queryUser);
                    entity.updateTimeStamp(user);
                    menuMapper.update(entity, queryWrapper);
                    response.fillMessage(EnumCode.SUCCESS);
                    return response;
                } else {
                    response.fillMessage(EnumCode.ERROR_HANDLE.getCode(), "数据为空");
                    return response;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public ApiResponse deleteMenu(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            if (null == id) {
                response.fillMessage(EnumCode.ERROR_HANDLE.getCode(), "id为空");
                return response;
            } else {
                QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("pid", id);
                List<MenuEntity> menus = menuMapper.selectList(queryWrapper);
                for (MenuEntity menu : menus) {
                    deleteMenu(menu.getId());
                }
                //删除角色-菜单表里相关的信息
                menuMapper.deleteByMenuId(id);
                menuMapper.deleteById(id);
                response.fillMessage(EnumCode.SUCCESS);
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.fillMessage(EnumCode.ERROR_SERVER);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }
    }

}
