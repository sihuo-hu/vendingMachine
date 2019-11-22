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
import com.royal.admin.core.common.constant.state.GoodsStatus;
import com.royal.admin.core.common.constant.state.ManagerStatus;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.core.log.LogObjectHolder;
import com.royal.admin.core.shiro.ShiroKit;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.User;
import com.royal.admin.modular.system.factory.UserFactory;
import com.royal.admin.modular.system.model.UserDto;
import com.royal.admin.modular.system.service.CommodityService;
import com.royal.admin.modular.system.service.ReplenishmentService;
import com.royal.admin.modular.system.service.UserService;
import com.royal.admin.modular.system.warpper.ReplenishmentWrapper;
import com.royal.admin.modular.system.warpper.UserWrapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 商品控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController extends BaseController {

    private static String PREFIX = "/modular/operation/commodity/";

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private ReplenishmentService replenishmentService;

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String timeLimit) {

        //拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }

        Page<Map<String, Object>> users = commodityService.selectCommodity(name, beginTime, endTime);
        return LayuiPageFactory.createPageInfo(users);
    }

    /**
     * 跳转到添加商品的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_add")
    public String addView() {
        return PREFIX + "add.html";
    }


    /**
     * 添加商品
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(@Valid Commodity commodity, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        String commodityDesc = commodity.getCommodityDesc().replace("& ", "&");
        commodityDesc = StringEscapeUtils.unescapeHtml(commodityDesc);
        commodity.setCommodityDesc(commodityDesc);
        this.commodityService.save(commodity);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到编辑商品页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_edit")
    public String userEdit(@RequestParam String commodityId) {
        if (ToolUtil.isEmpty(commodityId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        return PREFIX + "edit.html";
    }

    /**
     * 获取商品详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getCommodity")
    @ResponseBody
    public Object getCommodity(@RequestParam String commodityId) {
        if (ToolUtil.isEmpty(commodityId)) {
            throw new RequestEmptyException();
        }
        Commodity commodity = this.commodityService.getById(commodityId);
        return ResponseData.success(commodity);
    }

    /**
     * 修改商品
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseData edit(@Valid Commodity commodity, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        String commodityDesc = commodity.getCommodityDesc().replace("& ", "&");
        commodityDesc = StringEscapeUtils.unescapeHtml(commodityDesc);
        commodity.setCommodityDesc(commodityDesc);
        this.commodityService.updateById(commodity);
        return SUCCESS_TIP;
    }


    /**
     * 删除管理员（逻辑删除）
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam String commodityId) {
        if (ToolUtil.isEmpty(commodityId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.commodityService.deleteCommodity(commodityId);
        return SUCCESS_TIP;
    }


    /**
     * 冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public ResponseData freeze(@RequestParam String commodityId) {
        if (ToolUtil.isEmpty(commodityId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.commodityService.setStatus(commodityId, GoodsStatus.SOLDOUT.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public ResponseData unfreeze(@RequestParam String commodityId) {
        if (ToolUtil.isEmpty(commodityId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.commodityService.setStatus(commodityId, GoodsStatus.PUTAWAY.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 跳转到编辑商品页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/replenishment")
    public String replenishment() {
        return PREFIX + "replenishment.html";
    }

    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/replenishment/list")
    @Permission
    @ResponseBody
    public Object replenishmentList(@RequestParam(required = false) String status,
                                    @RequestParam(required = false) String timeLimit) {
        //拼接查询条件
        String beginTime = "";
        String endTime = "";
        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }
        Page<Map<String, Object>> users = replenishmentService.selectReplenishment(status, beginTime, endTime);
        Page wrapped = new ReplenishmentWrapper(users).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }

}
