
package com.royal.admin.modular.api;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.royal.admin.core.shiro.ShiroKit;
import com.royal.admin.core.shiro.ShiroUser;
import com.royal.admin.core.util.*;
import com.royal.admin.modular.system.entity.*;
import com.royal.admin.modular.system.mapper.UserMapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import com.royal.admin.modular.system.model.GoodsList;
import com.royal.admin.modular.system.model.ParaResult;
import com.royal.admin.modular.system.model.baiduyun.BdyResult;
import com.royal.admin.modular.system.model.zdy.ZdyReturn;
import com.royal.admin.modular.system.model.zdy.ZdySubmitOrder;
import com.royal.admin.modular.system.service.*;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 接口控制器提供
 *
 * @author stylefeng
 * @Date 2018/7/20 23:39
 */
@RestController
@CrossOrigin
@RequestMapping("/gunsApi")
public class ApiController extends BaseController {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private FachRecognitionService fachRecognitionService;
    @Autowired
    private SpfzService spfzService;
    @Autowired
    private FloorService floorService;
    @Autowired
    private MachinesService machinesService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MountingsService mountingsService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private CommodityService commodityService;

    @RequestMapping("/not/test")
    @ResponseBody
    public Object test(@RequestBody ParaResult result) {
        if(result.getOrg_info()!=null&& ToolUtil.isNotEmpty(result.getOrg_info())){
            System.out.println("收到了组织数据："+JSONUtils.toJSONObject(result).toString());
            return ResponseData.success();
        }
        if(result.getPerson_info()!=null&& ToolUtil.isNotEmpty(result.getPerson_info())){
            System.out.println("收到了用户数据："+JSONUtils.toJSONObject(result).toString());
            return ResponseData.success();
        }
        if(result.getJob_info()!=null&& ToolUtil.isNotEmpty(result.getJob_info())){
            System.out.println("收到了岗位数据："+JSONUtils.toJSONObject(result).toString());
            return ResponseData.success();
        }
        return ResponseData.error("我错了");
    }

    @RequestMapping("/not/a")
    @ResponseBody
    public Object sss(@RequestParam("code") String code,@RequestParam("machineId") String machineId) {
        HashMap<String, String> param = new HashMap<>();
        param.put("machine_no", machineId);
        param.put("code", code);
        param.put("out_trade_no", "cdxpz" + Tools.getUUId());
        String sign = ZdySignUtil.getSign(param);
        param.put("sign", sign);
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("appid", com.royal.admin.core.common.constant.state.ZdyMachines.APPID.getMessage());
        String s = HttpUtils.doPost(com.royal.admin.core.common.constant.state.ZdyMachines.PLACE_AN_ORDER_URL.getMessage(), param, headerMap);
        ZdyReturn z = JSONUtils.toBean(s, ZdyReturn.class, "data", ZdySubmitOrder.class);
        return ResponseData.success(z);
    }

    /**
     * H5调的第一个接口，获取验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping("/not/getVerification")
    @ResponseBody
    public Object getVerification(@RequestParam("phone") String phone) {
        Verification verification = verificationService.getByPhoneAndDate(phone);
        if (verification == null) {
            String code = Tools.getRandomCode(6, 0);
            if (AliyunSMSUtils.sendVerification(phone, code)) {
                verification = new Verification();
                verification.setCode(code);
                verification.setPhone(phone);
                verification.setType(1);
                verificationService.save(verification);
            } else {
                return ResponseData.error(105, "发送失败");
            }
        } else {
            return ResponseData.error(106, "请勿重复点击");
        }
        return ResponseData.success();
    }

    @RequestMapping("/not/getGoods")
    @ResponseBody
    public Object getGoods(@RequestParam("machinesId") String machinesId, @RequestParam("phone") String phone, @RequestParam("code") String code) {
        if (verificationService.verification(phone, code)) {
            verificationService.deleteByPhone(phone);
            List<GoodsList> list = spfzService.fzList(machinesId);
            return ResponseData.success(list);
        } else {
            return ResponseData.error(106, "验证码错误");
        }

    }

    @RequestMapping("/not/submitSpfz")
    @ResponseBody
    public Object submitSpfz(@RequestParam("id") Integer id, @RequestParam("userId") String userId) {
        Floor floor = floorService.getById(id);
        Machines machines = machinesService.getById(floor.getMachineId());
        if (!machines.getOnlineStatus().equals("ENABLE")) {
            return ResponseData.error(100, "跳舞机正在休息中，请稍后再试");
        }

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.in("b_status", "1", "2", "3").eq("machines_id", machines.getMachinesId());
        Order order = orderService.getOne(orderQueryWrapper);
        if (order != null) {
            return ResponseData.error(101, "有人正在挑战，请稍后再试");
        }

        QueryWrapper<Mountings> mountingsQueryWrapper = new QueryWrapper<>();
        mountingsQueryWrapper.eq("machines_id", machines.getMachinesId());
        Mountings mountings = mountingsService.getOne(mountingsQueryWrapper);

        order = new Order();
        order.setMachinesId(machines.getMachinesId());
        order.setFloorId(id);
        order.setStatus("1");
        order.setMountingsId(mountings.getId());
        order.setUserId(userId);
        orderService.save(order);
        return ResponseData.success(mountings.getId());
    }

    /**
     * 获取当前跳舞机状态
     * 1-创建 2-跳舞中 3-结算中 4-出货中 5-已完成 6-已取消 7-出货失败
     *
     * @param mountingsCode
     * @return
     */
    @RequestMapping("/not/getStatus")
    @ResponseBody
    public Object getStatus(@RequestParam("mountingsCode") String mountingsCode) {
        QueryWrapper<Mountings> mountingsQueryWrapper = new QueryWrapper<>();
        mountingsQueryWrapper.eq("b_code", mountingsCode);
        Mountings mountings = mountingsService.getOne(mountingsQueryWrapper);
        Order order = orderService.selectRecentlyOne(mountings.getId());
        if (order == null) {
            return ResponseData.error(201, "当前没有人扫码");
        } else if (order.getStatus().equals("1")) {
            order.setStatus("2");
            orderService.updateById(order);
            Floor floor = floorService.getById(order.getFloorId());
            Commodity commodity = commodityService.getById(floor.getGoodsId());
            return ResponseData.success(commodity.getDifficulty());
        } else if (order.getStatus().equals("2")) {
            return ResponseData.error(202, "还在跳舞中");
        } else if (order.getStatus().equals("3")) {
            return ResponseData.error(203, "结算中");
        } else if (order.getStatus().equals("5")) {
            return ResponseData.error(205, "当前没有人扫码");
        } else if (order.getStatus().equals("6")) {
            return ResponseData.error(206, "当前没有人扫码");
        } else if (order.getStatus().equals("7")) {
            return ResponseData.error(207, "当前没有人扫码");
        }
        return ResponseData.success();
    }

    @RequestMapping("/not/submitGrade")
    @ResponseBody
    public Object submitGrade(@RequestParam("mountingsCode") String mountingsCode, @RequestParam("grade") Integer grade) {
        QueryWrapper<Mountings> mountingsQueryWrapper = new QueryWrapper<>();
        mountingsQueryWrapper.eq("b_code", mountingsCode);
        Mountings mountings = mountingsService.getOne(mountingsQueryWrapper);
        Order order = orderService.selectRecentlyOne(mountings.getId());
        if (order == null) {
            return ResponseData.error(201, "未找到对应信息");
        } else if (order.getStatus().equals("2")) {
            order.setGrade(grade);
            if (orderService.verification(order.getUserId())) {
                //计算商品-----------
                Floor floor = floorService.getById(order.getFloorId());
                if (floor.getGrade() > grade) {
                    order.setStatus("8");
                    orderService.updateById(order);
                    return ResponseData.error(103, "请继续努力，下次会有更好成绩");
                }
                order.setGoodId(floor.getGoodsId());
                order.setStatus("3");
                orderService.updateById(order);
                //调用出货接口-----------
                orderService.specifiedCodeDelivery(floor.getFloorCode(), order);
                return ResponseData.success();
            } else {
                order.setStatus("4");
                orderService.updateById(order);
                return ResponseData.error(109, "今天你已经获得过奖品");
            }


        } else {
            return ResponseData.error(201, "未找到对应信息");
        }
    }

    /**
     * 1-创建 2-跳舞中 3-结算中 4-出货中 5-已完成 6-已取消 7-出货失败
     *
     * @param mountingsId
     * @return
     */
    @RequestMapping("/not/getH5Status")
    @ResponseBody
    public Object getH5Status(@RequestParam("mountingsId") Integer mountingsId, String userId) {
        Order order = orderService.selectRecentlyOne(mountingsId);
        if (order == null) {
            return ResponseData.error(201, "请重新扫码");
        } else if (order.getStatus().equals("1")) {
            if (order.getUserId().equals(userId)) {
                return ResponseData.success("正在连接中");
            } else {
                return ResponseData.error(202, "设备正在使用中");
            }
        } else if (order.getStatus().equals("2")) {
            if (order.getUserId().equals(userId)) {
                return ResponseData.success("连接成功，请在跳舞机上继续操作");
            } else {
                return ResponseData.error(202, "设备正在使用中");
            }
        } else if (order.getStatus().equals("3")) {
            if (order.getUserId().equals(userId)) {
                return ResponseData.success(1, "得分为", order.getGrade());
            } else {
                return ResponseData.error(202, "设备正在使用中");
            }
        } else if (order.getStatus().equals("4") && order.getUserId().equals(userId)) {
            return ResponseData.success(1, "得分为", order.getGrade());
        } else if (order.equals("5")) {
            if (order.getUserId().equals(userId)) {
                return ResponseData.success("已完成");
            } else {
                return ResponseData.success();
            }
        } else if (order.getStatus().equals("6")) {
            return ResponseData.error(201, "请重新扫码");
        } else if (order.getStatus().equals("7")) {
            return ResponseData.error(201, "请重新扫码");
        }
        return ResponseData.success();
    }


    /**
     * api登录接口，通过账号密码获取token
     */
    @RequestMapping("/auth")
    public Object auth(@RequestParam("username") String username,
                       @RequestParam("password") String password) {

        //封装请求账号密码为shiro可验证的token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());

        //获取数据库中的账号密码，准备比对
        User user = userMapper.getByAccount(username);

        String credentials = user.getPassword();
        String salt = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(salt);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(), credentials, credentialsSalt, "");

        //校验用户账号密码
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(
                usernamePasswordToken, simpleAuthenticationInfo);

        if (passwordTrueFlag) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("token", JwtTokenUtil.generateToken(String.valueOf(user.getUserId())));
            return result;
        } else {
            return new ErrorResponseData(500, "账号密码错误！");
        }
    }

    /**
     * 测试接口是否走鉴权
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Object test() {
        return SUCCESS_TIP;
    }

}

