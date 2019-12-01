package com.royal.admin.modular.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.constant.state.GoodsStatus;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.DateUtils;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Spfz;
import com.royal.admin.modular.system.entity.Verification;
import com.royal.admin.modular.system.mapper.CommodityMapper;
import com.royal.admin.modular.system.mapper.SpfzMapper;
import com.royal.admin.modular.system.mapper.VerificationMapper;
import com.royal.admin.modular.system.model.echarts.XYDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
public class VerificationService extends ServiceImpl<VerificationMapper, Verification> {

    /**
     * 查询一分钟内的验证码
     *
     * @param phone
     * @return
     */
    public Verification getByPhoneAndDate(String phone) {
        Date date = DateUtils.getDateBeforeOrAfterMinute(new Date(), -1);
        QueryWrapper<Verification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).ge("create_time", date).orderByDesc("create_time").last("LIMIT 1");
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 验证验证码是否正确
     *
     * @param phone
     * @param code
     * @return
     */
    public boolean verification(String phone, String code) {
        Date date = DateUtils.getDateBeforeOrAfterMinute(new Date(), -10);
        QueryWrapper<Verification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone).eq("b_code", code).ge("create_time", date).orderByDesc("create_time").last("LIMIT 1");
        Verification verification = this.baseMapper.selectOne(queryWrapper);
        if (verification == null) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteByPhone(String phone) {
        QueryWrapper<Verification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        this.baseMapper.delete(queryWrapper);
    }
}
