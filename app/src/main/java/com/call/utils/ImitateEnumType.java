package com.call.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 仿枚举类型
 */

public class ImitateEnumType {
    public static final String INTENT_KEY_AUTH_TYPE = "intent_key_rv_type";//认证类型key
    public static final String AUTH_TYPE_UN = "auth_type_un";//未验证 - 身份证
    public static final String AUTH_TYPE_ID_CARD = "auth_type_id_card";//确认身份证
    public static final String AUTH_TYPE_DRIVER_JUST = "auth_type_driver_just";//驾驶证正页
    public static final String AUTH_TYPE_DRIVER_JUST_Q = "auth_type_driver_just_q";//确认驾驶证正页
    public static final String AUTH_TYPE_DRIVER_BACK = "auth_type_driver_back";//驾驶证副页
    public static final String AUTH_TYPE_DRIVER_BACK_Q = "auth_type_driver_back_q";//确认驾驶证副页
    public static final String AUTH_TYPE_CERTIFIED = "auth_type_certified";//验证完成

    /**
     * 认证
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({AUTH_TYPE_UN, AUTH_TYPE_ID_CARD, AUTH_TYPE_DRIVER_JUST, AUTH_TYPE_DRIVER_JUST_Q,
            AUTH_TYPE_DRIVER_BACK, AUTH_TYPE_DRIVER_BACK_Q, AUTH_TYPE_CERTIFIED})
    public @interface AuthTypeDef {
    }

    //用户认证状态 pass("通过"), refused("拒绝"), review("待审核") = ['pass', 'refused', 'review']
    public static final String USER_PASS = "pass";
    public static final String USER_REFUSED = "refused";
    public static final String USER_REVIEW = "review";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({USER_PASS, USER_REFUSED, USER_REVIEW})
    public @interface UserAuthTypeDef {
    }

    //订单信息-服务状态 = ['init', 'inuse', 'used', 'finish']
    public static final String ORDER_INIT = "init";//未开始
    public static final String ORDER_INUSE = "inuse";//进行中
    public static final String ORDER_USED = "used";//已完成
    public static final String ORDER_FINISH = "finish";//已结束（退还押金）
    public static final String ORDER_UNPAID = "unpaid";//未支付
    public static final String ORDER_PAID = "paid";//已支付
    public static final String ORDER_CANCEL = "cancel";//已取消

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ORDER_INIT, ORDER_INUSE, ORDER_USED, ORDER_FINISH, ORDER_UNPAID, ORDER_PAID, ORDER_CANCEL})
    public @interface OrderTypeDef {
    }

    /**
     * "精选故事", "景点", "美食", "文化", "活动"
     */
    public static final String CIRCLE_TYPE = "circle_type";
    public static final String CIRCLE_STORIES = "circle_stories";//精选故事
    public static final String CIRCLE_SCENIC = "circle_scenic";//景点
    public static final String CIRCLE_FOOD = "circle_food";//美食
    public static final String CIRCLE_CULTURE = "circle_culture";//文化
    public static final String CIRCLE_ACTIVITY = "circle_activity";//活动

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({CIRCLE_STORIES, CIRCLE_SCENIC, CIRCLE_FOOD, CIRCLE_CULTURE, CIRCLE_ACTIVITY})
    public @interface CircleTypeDef {
    }

    /**
     * 评论类型
     */
    public static final String COMMENT_TYPE = "comment_type";
    public static final String COMMENT_TYPE_RV = "comment_type_rv";//房车等其他分类
    public static final String COMMENT_TYPE_CIRCLE = "comment_type_circle";//圈子

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({COMMENT_TYPE_RV, COMMENT_TYPE_CIRCLE})
    public @interface CommentTypeDef {
    }

}
