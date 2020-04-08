/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.royal.admin.modular.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.config.properties.GunsProperties;
import com.royal.admin.core.common.annotion.BussinessLog;
import com.royal.admin.core.common.annotion.Permission;
import com.royal.admin.core.common.constant.Const;
import com.royal.admin.core.common.constant.dictmap.UserDict;
import com.royal.admin.core.common.constant.factory.ConstantFactory;
import com.royal.admin.core.common.constant.state.ManagerStatus;
import com.royal.admin.core.common.constant.state.MenuStatus;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.log.LogObjectHolder;
import com.royal.admin.core.shiro.ShiroKit;
import com.royal.admin.modular.system.entity.User;
import com.royal.admin.modular.system.factory.UserFactory;
import com.royal.admin.modular.system.model.ListResult;
import com.royal.admin.modular.system.model.UserDto;
import com.royal.admin.modular.system.service.CommodityService;
import com.royal.admin.modular.system.service.FloorService;
import com.royal.admin.modular.system.service.MachinesService;
import com.royal.admin.modular.system.service.UserService;
import com.royal.admin.modular.system.warpper.FloorWrapper;
import com.royal.admin.modular.system.warpper.MachinesWrapper;
import com.royal.admin.modular.system.warpper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统设备控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/machines")
public class MachinesController extends BaseController {

    private static String PREFIX = "/modular/operation/machines/";

    @Autowired
    private MachinesService machinesService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private CommodityService commodityService;

    /**
     * 跳转到查看设备列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 查询设备列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String machinesId,
                       @RequestParam(required = false) String machinesName,
                       @RequestParam(required = false) String onlineStatus) {
        Page<Map<String, Object>> users = machinesService.selectMachines(machinesId, machinesName, onlineStatus);
        Page wrapped = new MachinesWrapper(users).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

    /**
     * 同步设备
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/refresh")
    @ResponseBody
    public ResponseData refresh() {
        this.machinesService.syncMachines();
        return SUCCESS_TIP;
    }

    /**
     * 下线设备
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData freeze(@RequestParam String machinesId) {
        if (ToolUtil.isEmpty(machinesId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.machinesService.setStatus(machinesId, MenuStatus.DISABLE.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 上线设备
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public ResponseData unfreeze(@RequestParam String machinesId) {
        if (ToolUtil.isEmpty(machinesId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.machinesService.setStatus(machinesId, MenuStatus.ENABLE.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 跳转到设置商品列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_get_goods")
    public String goodsList(@RequestParam String machinesId) {
        if (ToolUtil.isEmpty(machinesId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        return PREFIX + "floor_list.html";
    }

    /**
     * 查询货道列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getFloor")
    @ResponseBody
    public Object floorList(@RequestParam(required = false) String machinesId) {
        List<Map<String, Object>> users = floorService.selectFloorInfo(machinesId);
        List wrapped = new FloorWrapper(users).wrap();
        return ListResult.success(wrapped);
    }

    /**
     * 跳转到选择商品列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_set_goods")
    public String setGoods(@RequestParam Integer floorId) {
        if (ToolUtil.isEmpty(floorId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        return PREFIX + "goods_list.html";
    }

    /**
     * 查询商品列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getGoodsList")
    @ResponseBody
    public Object getGoodsList( Integer floorId) {
        List<Map<String, Object>> users = commodityService.selectGoodsInfo(floorId);
        return ListResult.success(users);
    }

    /**
     * 绑定商品
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/setGoods")
    @ResponseBody
    public ResponseData setGoods(@RequestParam Integer floorId,@RequestParam String commodityId) {
        if (ToolUtil.isAllEmpty(floorId,commodityId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.floorService.setGoods(floorId, commodityId);
        return SUCCESS_TIP;
    }

    /**
     * 清除商品
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/clearGoods")
    @ResponseBody
    public ResponseData clearGoods(@RequestParam Integer floorId) {
        if (ToolUtil.isAllEmpty(floorId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.floorService.clearGoods(floorId);
        return SUCCESS_TIP;
    }

    /**
     * 设置库存或积分
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/setGradeOrStock")
    @ResponseBody
    public ResponseData setGradeOrStock(@RequestParam String floorName,@RequestParam Integer stock,@RequestParam Integer floorId,@RequestParam Integer grade) {
        if (ToolUtil.isEmpty(floorId)&&ToolUtil.isAllEmpty(floorName,stock,grade)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.floorService.updateGradeOrStock(floorName,stock,floorId,grade);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到商品分组的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_spfz")
    public String spfz(@RequestParam String machinesId) {
        if (ToolUtil.isEmpty(machinesId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        return PREFIX + "spfz_list.html";
    }

    /**
     * 查询商品列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getSpfz")
    @ResponseBody
    public Object getSpfz( String machinesId) {
        List<Map<String, Object>> users = commodityService.getSpfz(machinesId);
        return ListResult.success(users);
    }

    /**
     * 跳转到商品分组的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_set_floor")
    public String setFloor(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        return PREFIX + "floor_ok_list.html";
    }

    /**
     * 查询商品列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getFloorFz")
    @ResponseBody
    public Object getFloor( Integer id) {
        List<Map<String, Object>> users = commodityService.getFloor(id);
        return ListResult.success(users);
    }

    /**
     * 设置库存或积分
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/bindingFloor")
    @ResponseBody
    public ResponseData bindingFloor(@RequestParam String displayCodes,@RequestParam String names,@RequestParam Integer spfzId) {
        if (ToolUtil.isOneEmpty(displayCodes,names,spfzId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.commodityService.updateSpfz(displayCodes, names,spfzId);
        return SUCCESS_TIP;
    }
}
