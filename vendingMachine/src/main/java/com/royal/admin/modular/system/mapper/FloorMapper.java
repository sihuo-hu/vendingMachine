package com.royal.admin.modular.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.royal.admin.modular.system.entity.Floor;
import com.royal.admin.modular.system.model.zdy.ZdyFloor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface FloorMapper extends BaseMapper<Floor> {

    List<String> selectDisplayCode(@Param("machinesId") String machinesId);

    void batchUpdateStatus(@Param("zdyFloorList") List<ZdyFloor> list, @Param("machinesId") String machineNo);

    List<Map<String, Object>> selectFloorInfo(@Param("machinesId") String machinesId);
//
//    /**
//     * 修改密码
//     */
//    int changePwd(@Param("userId") Long userId, @Param("pwd") String pwd);
//
//    /**
//     * 根据条件查询用户列表
//     */
//    Page<Map<String, Object>> selectUsers(@Param("page") Page page, @Param("dataScope") DataScope dataScope, @Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptId") Long deptId);
//
//    /**
//     * 设置用户的角色
//     */
//    int setRoles(@Param("userId") Long userId, @Param("roleIds") String roleIds);
//
//    /**
//     * 通过账号获取用户
//     */
//    User getByAccount(@Param("account") String account);

}
