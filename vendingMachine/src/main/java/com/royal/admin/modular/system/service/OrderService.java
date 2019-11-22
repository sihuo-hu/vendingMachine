package com.royal.admin.modular.system.service;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.royal.admin.core.common.constant.state.GoodsStatus;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.util.*;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Order;
import com.royal.admin.modular.system.entity.Spfz;
import com.royal.admin.modular.system.mapper.CommodityMapper;
import com.royal.admin.modular.system.mapper.OrderMapper;
import com.royal.admin.modular.system.mapper.SpfzMapper;
import com.royal.admin.modular.system.model.zdy.ZdyMachines;
import com.royal.admin.modular.system.model.zdy.ZdyOrder;
import com.royal.admin.modular.system.model.zdy.ZdyReturn;
import com.royal.admin.modular.system.model.zdy.ZdySubmitOrder;
import com.royal.admin.modular.system.task.ThreadPoolUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
public class OrderService extends ServiceImpl<OrderMapper, Order> {


    public Order selectRecentlyOne(Integer id) {
        return this.baseMapper.selectRecentlyOne(id);
    }

    public void updateByCreateTime(Date createTime, String oldStatus, String newStatus) {
        Order order = new Order();
        order.setStatus(newStatus);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("b_status", oldStatus).lt("create_time", createTime);
        this.baseMapper.update(order, orderQueryWrapper);
    }

    public void updateByUpdate(Date updateTime, String oldStatus, String newStatus) {
        Order order = new Order();
        order.setStatus(newStatus);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("b_status", oldStatus).lt("update_time", updateTime);
        this.baseMapper.update(order, orderQueryWrapper);
    }

    public void selectZdyOrder() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Date updateTime = DateUtils.getDateBeforeOrAfterSecond(new Date(), -10);
        queryWrapper.eq("b_status", "3").lt("update_time", updateTime).eq("select_status", 1);
        List<Order> orders = this.baseMapper.selectList(queryWrapper);
        if (orders != null && orders.size() > 0) {
            for (Order order : orders) {
                order.setSelectStatus(2);
            }
        } else {
            return;
        }
        this.updateBatchById(orders);
        ThreadPoolUtil.selectZdyOrder(orders);
    }

    /**
     * 下单出货
     *
     * @param floorCode
     * @param order
     */
    public void specifiedCodeDelivery(String floorCode, Order order) {
        HashMap<String, String> param = new HashMap<>();
        param.put("machine_no", order.getMachinesId());
        param.put("code", floorCode);
        param.put("out_trade_no", "cdxpz" + order.getId());
        String sign = ZdySignUtil.getSign(param);
        param.put("sign", sign);
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("appid", com.royal.admin.core.common.constant.state.ZdyMachines.APPID.getMessage());
        String s = HttpUtils.doPost(com.royal.admin.core.common.constant.state.ZdyMachines.PLACE_AN_ORDER_URL.getMessage(), param, headerMap);
        ZdyReturn z = JSONUtils.toBean(s, ZdyReturn.class, "data", ZdySubmitOrder.class);
        if (z.getCode() != 1) {
            order.setStatus("6");
            order.setDeliverNote(z.getMsg());
            this.updateById(order);
        } else {
            order.setStatus("3");
            order.setOrderNo(((ZdySubmitOrder) z.getData()).getOrder_no());
            order.setDeliverNote(z.getMsg());
            this.updateById(order);
        }

    }

    public Page<Map<String, Object>> selectOrder(String commodityName, String beginTime, String endTime) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.selectOrder(page,commodityName,beginTime,endTime);
    }
}