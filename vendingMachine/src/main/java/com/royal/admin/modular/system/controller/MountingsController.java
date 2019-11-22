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

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.royal.admin.core.common.annotion.Permission;
import com.royal.admin.core.common.constant.state.GoodsStatus;
import com.royal.admin.core.common.exception.BizExceptionEnum;
import com.royal.admin.core.common.page.LayuiPageFactory;
import com.royal.admin.modular.system.entity.Commodity;
import com.royal.admin.modular.system.entity.Mountings;
import com.royal.admin.modular.system.model.ListResult;
import com.royal.admin.modular.system.service.CommodityService;
import com.royal.admin.modular.system.service.MountingsService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 跳舞机控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mountings")
public class MountingsController extends BaseController {

    private static String PREFIX = "/modular/operation/mountings/";

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private MountingsService mountingsService;

    /**
     * 跳转到查看跳舞机列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "list.html";
    }

    /**
     * 查询跳舞机列表
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

        Page<Map<String, Object>> users = mountingsService.selectMountings(name, beginTime, endTime);
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
    public ResponseData add(@Valid Mountings mountings, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.mountingsService.save(mountings);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到编辑商品页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_edit")
    public String userEdit(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
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
    @RequestMapping("/getMountings")
    @ResponseBody
    public Object getCommodity(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new RequestEmptyException();
        }
        Mountings mountings = this.mountingsService.getById(id);
        return ResponseData.success(mountings);
    }

    /**
     * 修改商品
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseData edit(@Valid Mountings mountings, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.mountingsService.updateById(mountings);
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
    public ResponseData delete(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.mountingsService.removeById(id);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到选择商品列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/to_binding")
    public String setGoods(@RequestParam Integer id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        return PREFIX + "machines_list.html";
    }

    /**
     * 查询商品列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getMachinesList")
    @ResponseBody
    public Object getMachinesList( Integer id) {
        List<Map<String, Object>> users = mountingsService.getMachinesList(id);
        return ListResult.success(users);
    }

    /**
     * 绑定商品
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/binding")
    @ResponseBody
    public ResponseData binding(@RequestParam Integer mountingId,@RequestParam String machinesId) {
        if (ToolUtil.isAllEmpty(mountingId,machinesId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.mountingsService.binding(mountingId, machinesId);
        return SUCCESS_TIP;
    }
}
