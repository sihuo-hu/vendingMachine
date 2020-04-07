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
import com.royal.admin.modular.system.model.ListResult;
import com.royal.admin.modular.system.model.echarts.Histogram;
import com.royal.admin.modular.system.model.echarts.Line;
import com.royal.admin.modular.system.model.echarts.XYDate;
import com.royal.admin.modular.system.service.CommodityService;
import com.royal.admin.modular.system.service.ReplenishmentService;
import com.royal.admin.modular.system.warpper.ReplenishmentWrapper;
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
 * 数据报表
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/instrument")
public class InstrumentController extends BaseController {

    private static String PREFIX = "/modular/operation/instrument/";

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
     * 柱状图
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/histogram")
    @ResponseBody
    public Object histogram() {
        List<XYDate> xyDateList = commodityService.selectHistogram();
        Histogram histogram = new Histogram();
        histogram.setxyAxis(xyDateList);
        return histogram;
    }

    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/pie")
    @ResponseBody
    public Object pie() {
        List<Map<String,Integer>> maps = commodityService.selectPie();
        return maps;
    }

    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/line")
    @ResponseBody
    public Object line() {
        List<Map<String,Object>> maps = commodityService.selectLine();
        Line line = new Line(maps);
        return line;
    }


}
