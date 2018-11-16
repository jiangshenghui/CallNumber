package com.call.net;

import com.bg.baseutillib.net.tools.OSSUploadUrlBean;

import java.io.Serializable;
import java.util.List;

public class ReplysBean implements Serializable {

    /**
     * orderId : String-订单id
     * userId : String-用户id
     * userCore : {"id":"Long-用户id","mobile":"String-用户手机","nickname":"String-昵称","trueName":"String-真实姓名","sex":"int-用户性别（1：男；2：女；0：未知）","birthday":"String-用户生日","avatarUrl":"String-头像url"}
     * replyType : String-评论类型（camp：房车营地评论； rent：房车租赁评论）
     * star : int-评论星级
     * tags : String-评论标签（多个用英文逗号分隔）
     * images : [{"fileId":"Long-文件id，必填","fileKey":"String-文件key","fileName":"String-文件名","fileUrl":"String-文件url","targetType":"String-业务类型"}]
     * comments : String-评论内容
     */

    private String orderId;
//    private String userId;
    private UserCoreBean userCore;
//    private String replyType;
    private String star;
    private String tags;
    private String comments;
    private String gmtCreated;
    private List<OSSUploadUrlBean.BussDataBean> images;

    private String id;//评论ID ,
    private boolean isSelf ;//是否自己的评论/回复 ,
    private String msgId ;//信息ID ,
    private String parentId ;//回复评论的id ,
    private String replyAvatarUrl  ;//回复对象的头像 ,
    private String replyInfo  ;//内容 ,
    private String replyName  ;//回复对象的名字 ,
    private String replyType   ;//回复类型 = ['news', 'image', 'voice', 'video', 'location', 'share'],
    private String replyUserId  ;//回复对象ID ,
    private String sendTime   ;//发布时间 ,
    private String topId   ;//评论链的排序 ,
    private String userAvatarUrl    ;//评论发布者的头像 ,
    private String userId    ;//这条评论/回复的发布者的id ,
    private String userName    ;//评论发布者的名字

    private List<ReplysListBean> replyList;

    public List<ReplysListBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplysListBean> replyList) {
        this.replyList = replyList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getReplyAvatarUrl() {
        return replyAvatarUrl;
    }

    public void setReplyAvatarUrl(String replyAvatarUrl) {
        this.replyAvatarUrl = replyAvatarUrl;
    }

    public String getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserCoreBean getUserCore() {
        return userCore;
    }

    public void setUserCore(UserCoreBean userCore) {
        this.userCore = userCore;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<OSSUploadUrlBean.BussDataBean> getImages() {
        return images;
    }

    public void setImages(List<OSSUploadUrlBean.BussDataBean> images) {
        this.images = images;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public static class UserCoreBean {
        /**
         * id : Long-用户id
         * mobile : String-用户手机
         * nickname : String-昵称
         * trueName : String-真实姓名
         * sex : int-用户性别（1：男；2：女；0：未知）
         * birthday : String-用户生日
         * avatarUrl : String-头像url
         */

        private String id;
        private String mobile;
        private String nickname;
        private String trueName;
        private String sex;
        private String birthday;
        private String avatarUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }

    public static class ReplysListBean implements Serializable {
        private String orderId;
        //    private String userId;
        private UserCoreBean userCore;
        //    private String replyType;
        private String star;
        private String tags;
        private String comments;
        private String gmtCreated;
        private List<OSSUploadUrlBean.BussDataBean> images;

        private String id;//评论ID ,
        private boolean isSelf ;//是否自己的评论/回复 ,
        private String msgId ;//信息ID ,
        private String parentId ;//回复评论的id ,
        private String replyAvatarUrl  ;//回复对象的头像 ,
        private String replyInfo  ;//内容 ,
        private String replyName  ;//回复对象的名字 ,
        private String replyType   ;//回复类型 = ['news', 'image', 'voice', 'video', 'location', 'share'],
        private String replyUserId  ;//回复对象ID ,
        private String sendTime   ;//发布时间 ,
        private String topId   ;//评论链的排序 ,
        private String userAvatarUrl    ;//评论发布者的头像 ,
        private String userId    ;//这条评论/回复的发布者的id ,
        private String userName    ;//评论发布者的名字


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public UserCoreBean getUserCore() {
            return userCore;
        }

        public void setUserCore(UserCoreBean userCore) {
            this.userCore = userCore;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getGmtCreated() {
            return gmtCreated;
        }

        public void setGmtCreated(String gmtCreated) {
            this.gmtCreated = gmtCreated;
        }

        public List<OSSUploadUrlBean.BussDataBean> getImages() {
            return images;
        }

        public void setImages(List<OSSUploadUrlBean.BussDataBean> images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isSelf() {
            return isSelf;
        }

        public void setSelf(boolean self) {
            isSelf = self;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getReplyAvatarUrl() {
            return replyAvatarUrl;
        }

        public void setReplyAvatarUrl(String replyAvatarUrl) {
            this.replyAvatarUrl = replyAvatarUrl;
        }

        public String getReplyInfo() {
            return replyInfo;
        }

        public void setReplyInfo(String replyInfo) {
            this.replyInfo = replyInfo;
        }

        public String getReplyName() {
            return replyName;
        }

        public void setReplyName(String replyName) {
            this.replyName = replyName;
        }

        public String getReplyType() {
            return replyType;
        }

        public void setReplyType(String replyType) {
            this.replyType = replyType;
        }

        public String getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(String replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getTopId() {
            return topId;
        }

        public void setTopId(String topId) {
            this.topId = topId;
        }

        public String getUserAvatarUrl() {
            return userAvatarUrl;
        }

        public void setUserAvatarUrl(String userAvatarUrl) {
            this.userAvatarUrl = userAvatarUrl;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
