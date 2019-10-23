package com.royal.admin.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushUtil {
    private static Logger log = LoggerFactory.getLogger(PushUtil.class);
    private static String MASTER_SECRET = "0447bf22528d2472aeff2fce";
    private static String APP_KEY = "4d8f3751bd9fdefde585aa6c";
    private static ClientConfig clientConfig = ClientConfig.getInstance();
    private static JPushClient jpushClient = new JPushClient(MASTER_SECRET,
            APP_KEY, null, clientConfig);

    /**
     * 推送自定义消息接口,根据所传ID（List）
     *
     * @param registrationIds 别名ids
     * @param timeToLive      有效时间
     * @param content         推送内容
     * @param title
     * @param map
     * @return
     */
    public static PushPayload sendPushMessage(Set<String> registrationIds,
                                              long timeToLive, String content, String title,
                                              Map<String, String> map) {
        return PushPayload
                .newBuilder()
                // 设置所有平台
                .setPlatform(Platform.all())
                // 使用别名
                .setAudience(Audience.alias(registrationIds))
                // 设置自定义消息推送内容
                .setMessage(
                        Message.newBuilder().setMsgContent(content)
                                .setTitle(title).setContentType("text")
                                .addExtras(map).build())
                // 设置有效时长
                .setOptions(
                        Options.newBuilder().setTimeToLive(timeToLive).build())
                .build();
    }

    /**
     * 推送自定义消息接口,根据所传ID
     *
     * @param registrationId 别名ids
     * @param timeToLive     有效时间
     * @param content        推送内容
     * @param title
     * @param map
     * @return
     */
    public static PushPayload sendPushMessage(String registrationId,
                                              long timeToLive, String content, String title,
                                              Map<String, String> map) {
        return PushPayload
                .newBuilder()
                // 设置所有平台
                .setPlatform(Platform.all())
                // 使用别名
                .setAudience(Audience.alias(registrationId))
                // 设置自定义消息推送内容
                .setMessage(
                        Message.newBuilder().setMsgContent(content)
                                .setTitle(title).setContentType("text")
                                .addExtras(map).build())
                // 设置有效时长
                .setOptions(
                        Options.newBuilder().setTimeToLive(timeToLive).build())
                .build();
    }

    /**
     * 推送自定义消息接口.发送所有
     *
     * @param timeToLive 有效时间
     * @param content    推送内容
     * @param title
     * @param map
     * @return
     */
    public static PushPayload sendPushMessage(long timeToLive, String content,
                                              String title, Map<String, String> map) {
        return PushPayload
                .newBuilder()
                // 设置所有平台
                .setPlatform(Platform.all())
                // 使用别名
                .setAudience(Audience.all())
                // 设置自定义消息推送内容
                .setMessage(
                        Message.newBuilder().setMsgContent(content)
                                .setTitle(title).setContentType("text")
                                .addExtras(map).build())
                // 设置有效时长
                .setOptions(
                        Options.newBuilder().setTimeToLive(timeToLive).build())
                .build();
    }

    public static void push(PushPayload pushPayload) throws Exception {
        PushResult pushResult = jpushClient.sendPush(pushPayload);
        Thread.sleep(5000);
        // 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
        jpushClient.close();
    }

    /**
     * 极光推送
     */
    public static void jiguangPush() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("title", "1221");
        //1显示于通知栏 2不显示于通知栏
        map.put("pushType", "222");
        map.put("message", "11111111111");
        String[] alias = new String[]{"17706519951"};//声明别名
        log.info("对别名" + alias + "的用户推送信息");
        try {
//            push(alias, "你好啊", map);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * alert存放图片或者文本或者语音
     * title 你收到一条消息
     * 生成极光推送对象PushPayload
     *
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert(List<String> alias, String alert, Map<String, String> map) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(alias==null?Audience.all():Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(map)
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtras(map)
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * alert存放图片或者文本或者语音
     * title 你收到一条消息
     * 生成极光推送对象PushPayload
     *
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert(String alias, String alert, Map<String, String> map) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(alias==null?Audience.all():Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(map)
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtras(map)
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 系统消息推送
     * 极光推送方法
     *
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static void push(String alias, String alert, Map<String, String> map) throws Exception {
        PushPayload payload = buildPushObject_android_ios_alias_alert(alias, alert, map);
        PushResult result = jpushClient.sendPush(payload);
        Thread.sleep(5000);
        // 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
        jpushClient.close();
    }

    /**
     * 系统消息推送
     * 极光推送方法
     *
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static void push(List<String> alias, String alert, Map<String, String> map) throws Exception {
        PushPayload payload = buildPushObject_android_ios_alias_alert(alias, alert, map);
        PushResult result = jpushClient.sendPush(payload);
        Thread.sleep(5000);
        // 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
        jpushClient.close();
    }


    /**
     * 生成极光推送对象PushPayload
     *
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObjectalert(String alert, String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("type", "infomation").setTitle(title)
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 针对某个用户进行推送消息
     * 极光推送方法
     *
     * @param alert
     * @return PushResult
     */
    public static boolean sendPushMessage(String alert, String title) {
        PushPayload payload = buildPushObjectalert(alert, title);
        try {
            PushResult result = jpushClient.sendPush(payload);
            if (result != null && result.isResultOK()) {
                return true;
            }
            return false;
        } catch (APIConnectionException e) {
            log.error("Connection error. Should retry later. ", e);
            return false;
        } catch (APIRequestException e) {
            log.error("Error response from JPush server. Should review and fix it. ", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
            log.info("Msg ID: " + e.getMsgId());
            return false;
        }
    }


    public static void main(String[] args) {
        jiguangPush();
    }
}
