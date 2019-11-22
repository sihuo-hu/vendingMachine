package com.royal.admin.modular.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.constant.Const;
import com.royal.admin.core.common.constant.state.GoodsStatus;
import com.royal.admin.core.common.constant.state.ManagerStatus;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.node.MenuNode;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.shiro.ShiroKit;
import com.royal.admin.core.shiro.ShiroUser;
import com.royal.admin.core.shiro.service.UserAuthService;
import com.royal.admin.core.util.ApiMenuFilter;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Spfz;
import com.royal.admin.modular.system.entity.User;
import com.royal.admin.modular.system.factory.UserFactory;
import com.royal.admin.modular.system.mapper.CommodityMapper;
import com.royal.admin.modular.system.mapper.SpfzMapper;
import com.royal.admin.modular.system.mapper.UserMapper;
import com.royal.admin.modular.system.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class CommodityService extends ServiceImpl<CommodityMapper, Commodity> {

    @Resource
    private SpfzMapper spfzMapper;

    /**
     * 根据条件查询商品列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public Page<Map<String, Object>> selectCommodity(String name, String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectCommodity(page, name, beginTime, endTime);
    }

    public void deleteCommodity(String commodityId) {
        this.setStatus(commodityId, GoodsStatus.SOLDOUT.getCode());
    }

    /**
     * 修改用户状态
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public int setStatus(String commodityId, String status) {
        return this.baseMapper.updateStatusById(commodityId, status);
    }


    /**
     * 获取正常状态的商品
     *
     * @param floorId
     * @return
     */
    public List<Map<String, Object>> selectGoodsInfo(Integer floorId) {
        return this.baseMapper.selectGoodsInfo(floorId);
    }

    public List<Map<String, Object>> getSpfz(String machinesId) {
        QueryWrapper<Spfz> spfzQueryWrapper = new QueryWrapper<>();
        spfzQueryWrapper.eq("machines_id", machinesId).orderByAsc("b_id");
        List<Map<String, Object>> list = spfzMapper.selectMaps(spfzQueryWrapper);
        if (list == null || list.size() <= 0) {
            Spfz spfz1 = new Spfz("一星",machinesId);
            Spfz spfz2 = new Spfz("二星",machinesId);
            Spfz spfz3 = new Spfz("三星",machinesId);
            Spfz spfz4 = new Spfz("四星",machinesId);
            Spfz spfz5 = new Spfz("五星",machinesId);
            spfzMapper.insert(spfz1);
            spfzMapper.insert(spfz2);
            spfzMapper.insert(spfz3);
            spfzMapper.insert(spfz4);
            spfzMapper.insert(spfz5);
            list = spfzMapper.selectMaps(spfzQueryWrapper);
        }
        return list;
    }

    public List<Map<String, Object>> getFloor(Integer id) {
        Spfz spfz = spfzMapper.selectById(id);
        List<Map<String, Object>> list = spfzMapper.selectSpfzAndFloor(id,spfz.getMachinesId());
        return list;
    }

    public void updateSpfz(String displayCodes, String names, Integer spfzId) {
        Spfz spfz = spfzMapper.selectById(spfzId);
        spfz.setFloorCodes(displayCodes);
        spfz.setGoodsNames(names);
        spfzMapper.updateById(spfz);
    }

}
