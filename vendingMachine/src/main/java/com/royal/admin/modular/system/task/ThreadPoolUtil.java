package com.royal.admin.modular.system.task;

import com.royal.admin.core.util.HttpUtils;
import com.royal.admin.core.util.JSONUtils;
import com.royal.admin.core.util.SpringContextUtils;
import com.royal.admin.core.util.ZdySignUtil;
import com.royal.admin.modular.system.entity.Floor;
import com.royal.admin.modular.system.entity.Order;
import com.royal.admin.modular.system.model.zdy.ZdyOrder;
import com.royal.admin.modular.system.model.zdy.ZdyReturn;
import com.royal.admin.modular.system.service.FloorService;
import com.royal.admin.modular.system.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    /**
     * 止损止盈处理
     *
     * @param
     */
    public static void selectZdyOrder(List<Order> orders) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                OrderService orderService = (OrderService) SpringContextUtils.getBean(
                        "orderService");

                for (Order order : orders) {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("order_no", order.getOrderNo());
                    String sign = ZdySignUtil.getSign(param);
                    param.put("sign", sign);
                    HashMap<String, String> headerMap = new HashMap<>();
                    headerMap.put("appid", com.royal.admin.core.common.constant.state.ZdyMachines.APPID.getMessage());
                    String s = HttpUtils.doGet(com.royal.admin.core.common.constant.state.ZdyMachines.SELECT_ORDER_URL.getMessage(), param, headerMap);
                    ZdyReturn z = JSONUtils.toBean(s, ZdyReturn.class, "data", ZdyOrder.class);
                    if (z.getCode() != 1) {
                        continue;
                    }
                    ZdyOrder zdyOrder = (ZdyOrder) z.getData();
                    if (zdyOrder.getOrder_status() == 2) {
                        FloorService floorService = (FloorService) SpringContextUtils.getBean(
                                "floorService");
                        order.setStatus("5");
                        order.setSelectStatus(3);
                        order.setDeliverNote(zdyOrder.getDeliver_note());
                        orderService.updateById(order);
                        Floor floor = floorService.getById(order.getFloorId());
                        floor.setStock(floor.getStock()-1);
                        floorService.updateById(floor);
                    } else if (zdyOrder.getOrder_status() == 3) {
                        order.setDeliverNote(zdyOrder.getDeliver_note());
                        order.setStatus("7");
                        order.setSelectStatus(3);
                        orderService.updateById(order);
                    }
                }

            }
        });
    }
}
