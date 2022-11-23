package com.nteport.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nteport.admin.dto.Dept4AppDTO;
import com.nteport.admin.entity.TDeptEntity;
import com.nteport.admin.entity.system.ApiResponse;
import com.nteport.admin.entity.system.EnumCode;
import com.nteport.admin.entity.system.UserEntity;
import com.nteport.admin.mapper.UserMapper;
import com.nteport.admin.service.TDeptService;
import com.nteport.admin.mapper.TDeptMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* @author qianjun
* @description 针对表【t_dept(部门表)】的数据库操作Service实现
* @createDate 2022-07-27 16:14:21
*/
@Service
public class TDeptServiceImpl extends ServiceImpl<TDeptMapper, TDeptEntity>
    implements TDeptService{

    @Autowired
    private TDeptMapper deptMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponse getDeptTree() {
        List<TDeptEntity> tDeptList = baseMapper.selectList(Wrappers.emptyWrapper());
        return ApiResponse.success(buildDeptTree(tDeptList));
    }

    @Override
    public List<TDeptEntity> getDeptTree2() {
        List<TDeptEntity> tDeptList = baseMapper.selectList(Wrappers.emptyWrapper());
        return buildDeptTree(tDeptList);
    }

    @Override
    public ApiResponse add(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(jsonString == null || user == null){
            response.fillMessage(EnumCode.BAD_REQUEST);
            return response;
        }
        try {
            TDeptEntity entity = JSON.parseObject(jsonString,TDeptEntity.class);
            entity.createTimeStamp(user);
            String ancestors = entity.getParentId().toString();
            Long parentId = entity.getParentId();
            while (parentId != 0L){
                QueryWrapper<TDeptEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id",parentId);
                TDeptEntity parentEntity = deptMapper.selectOne(queryWrapper);
                if(parentEntity != null){
                    parentId = parentEntity.getParentId();
                    ancestors = parentId + "," +ancestors;
                }else {
                    break;
                }
            }
            entity.setAncestors(ancestors);
            deptMapper.insert(entity);
            return ApiResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.fillMessage(EnumCode.ERROR_SERVER);
        }
        return response;
    }

    @Override
    public ApiResponse updateDept(String jsonString, UserEntity user) {
        ApiResponse response = new ApiResponse();
        if(jsonString == null || user == null){
            response.fillMessage(EnumCode.BAD_REQUEST);
            return response;
        }
        try {
            TDeptEntity entity = JSON.parseObject(jsonString,TDeptEntity.class);
            QueryWrapper<TDeptEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",entity.getId());
            TDeptEntity exist = deptMapper.selectOne(queryWrapper);
            if(exist == null){
                return response.fillMessage(EnumCode.ERROR_HANDLE);
            }
            entity.updateTimeStamp(user);
            deptMapper.updateById(entity);
            return ApiResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.fillMessage(EnumCode.ERROR_SERVER);
        }
        return response;
    }

    @Override
    public ApiResponse removeDept(Long id) {
        ApiResponse response = new ApiResponse();
        if(id == null){
            return response.fillMessage(EnumCode.BAD_REQUEST);
        }
        try {
            QueryWrapper<UserEntity> userQuery1 = new QueryWrapper<>();
            userQuery1.eq("dept_id",id);
            if(userMapper.selectOne(userQuery1) !=null){
                return ApiResponse.fail("该部门下存在员工，请调整后再删除！");
            }
            QueryWrapper<TDeptEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("ancestors","%"+id+"%");
            List<TDeptEntity> list = deptMapper.selectList(queryWrapper);
            for (TDeptEntity entity:
                list) {
                QueryWrapper<UserEntity> userQuery2 = new QueryWrapper<>();
                userQuery2.eq("dept_id",entity.getId());
                List<UserEntity> userList = userMapper.selectList(userQuery2);
                if(userList != null){
                    return ApiResponse.fail("子部门 "+entity.getDeptName()+" 下存在员工，请调整后再删除！");
                }
            }
            deptMapper.deleteById(id);
            deptMapper.delete(queryWrapper);
            return ApiResponse.success();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.fillMessage(EnumCode.ERROR_SERVER);
        }
        return response;
    }

    @Override
    public ApiResponse getDeptTreeChildren() {
        List<TDeptEntity> tDeptList = baseMapper.selectList(Wrappers.emptyWrapper());
        List<TDeptEntity> treeList = buildDeptTree(tDeptList);
        //不要顶级

//        List<Dept4AppDTO> list = new ArrayList<>();
//        for (TDeptEntity child : treeList.get(0).getChildren()) {
//            Dept4AppDTO dto = new Dept4AppDTO();
//            dto.setName(child.getDeptName());
//            dto.setId(child.getId());
//            dto.setTree(child.isTree());
//            dto.setChildren(child.getChildren());
//            list.add(dto);
//        }

        return ApiResponse.success(treeList.get(0).getChildren());
    }

    @Override
    public ApiResponse getUserByDept(Long id){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id",id);
        //已审核
        queryWrapper.eq("status","2");
        return ApiResponse.success(userMapper.selectList(queryWrapper));
    }

    @Override
    public ApiResponse getDeptById(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            if(id == null){
                return response.fillMessage(EnumCode.BAD_REQUEST);
            }
            TDeptEntity entity = deptMapper.selectById(id);
            return response.fillMessage(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return response.fillMessage(EnumCode.ERROR_HANDLE);
        }
    }

    @Override
    public TDeptEntity getDeptEntityById(Long id) {
        QueryWrapper<TDeptEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("status", "0");
        queryWrapper.eq("del_flag", "0");
        List<TDeptEntity> deptEntityList = deptMapper.selectList(queryWrapper);
        if(deptEntityList!=null&&deptEntityList.size()>0){
            return deptEntityList.get(0);
        }
        return null;
    }

    @Override
    public List<Long> queryAreaDept(Long id) {
        return deptMapper.queryAreaDept(id);
    }

    /**
     * 构建部门树
     * @param depts
     * @return
     */
    public List<TDeptEntity> buildDeptTree(List<TDeptEntity> depts)
    {
        List<TDeptEntity> returnList = new ArrayList<TDeptEntity>();
        List<Long> tempList = new ArrayList<Long>();
        for (TDeptEntity dept : depts)
        {
            tempList.add(dept.getId());
        }
        for (TDeptEntity dept : depts)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursion(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursion(List<TDeptEntity> list, TDeptEntity t)
    {
        // 得到子节点列表
        List<TDeptEntity> childList = getChildList(list, t);
        t.setChildren(childList);
//        if(childList.size()>0){
//            t.setHasChildren(true);
//        }
        for (TDeptEntity tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursion(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<TDeptEntity> getChildList(List<TDeptEntity> list, TDeptEntity t)
    {
        List<TDeptEntity> tlist = new ArrayList<TDeptEntity>();
        Iterator<TDeptEntity> it = list.iterator();
        while (it.hasNext())
        {
            TDeptEntity n =  it.next();
            if (null!=n.getParentId() && n.getParentId().longValue() == t.getId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<TDeptEntity> list, TDeptEntity t)
    {
        return getChildList(list, t).size() > 0;
    }

}




