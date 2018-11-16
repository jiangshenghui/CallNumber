package com.call.net.login.response;

public class UserBean {

    /**
     * bussData : {"avatarKey":"img.jpg","avatarUrl":"http://file.icebartech.com/img.jpg","creator":"string","creditScore":74,"gmtCreated":"2018-10-11T03:08:01.341Z","gmtModified":"2018-10-11T03:08:01.341Z","id":0,"isDeleted":"string","isOwner":"y: 是车主, n: 不是车主","mobile":"18871231747","modifier":"string","nickname":"匿名用户","password":"$2a$11$X3tMWQEbEG0f7Ogca/mw.u5Drpm9ibP2lH7YsbOeUhnHVKO5jKm0m","userAuthDTO":{"authStatus":"pass","authStatusText":"拒绝","avatarKey":"img.jpg","creator":"string","driverCard1":"img.jpg","driverCard1Key":"img.jpg","driverCard2":"img.jpg","driverCard2Key":"img.jpg","gmtCreated":"2018-10-11T03:08:01.341Z","gmtModified":"2018-10-11T03:08:01.341Z","id":0,"idCard":"img.jpg","idCardKey":"img.jpg","isDeleted":"string","mobile":"18871231747","modifier":"string","userId":0},"userSex":"male","userSexText":"女","userStatus":"pass","userStatusText":"拒绝"}
     */
    public String invitCount;
    public String inviteCode;
    public boolean isPartner;
    public String  level;
    public String inviteUrl;
    public String mobile;
    public BussDataBean income;


//    public BussDataBean getBussData() {
//        return income;
//    }
//
//    public void setBussData(BussDataBean income) {
//        this.income = income;
//    }

    public static class BussDataBean {
        /**
         * avatarKey : img.jpg
         * avatarUrl : http://file.icebartech.com/img.jpg
         * creator : string
         * creditScore : 74
         * gmtCreated : 2018-10-11T03:08:01.341Z
         * gmtModified : 2018-10-11T03:08:01.341Z
         * id : 0
         * isDeleted : string
         * isOwner : y: 是车主, n: 不是车主
         * mobile : 18871231747
         * modifier : string
         * nickname : 匿名用户
         * password : $2a$11$X3tMWQEbEG0f7Ogca/mw.u5Drpm9ibP2lH7YsbOeUhnHVKO5jKm0m
         * userAuthDTO : {"authStatus":"pass","authStatusText":"拒绝","avatarKey":"img.jpg","creator":"string","driverCard1":"img.jpg","driverCard1Key":"img.jpg","driverCard2":"img.jpg","driverCard2Key":"img.jpg","gmtCreated":"2018-10-11T03:08:01.341Z","gmtModified":"2018-10-11T03:08:01.341Z","id":0,"idCard":"img.jpg","idCardKey":"img.jpg","isDeleted":"string","mobile":"18871231747","modifier":"string","userId":0}
         * userSex : male
         * userSexText : 女
         * userStatus : pass
         * userStatusText : 拒绝
         */
        public String money;
        public String invitCount;
        public String inviteCode;
        public boolean isPartner;
        public String inviteUrl;

        private String avatarKey;
        private String avatarUrl;
        private String creator;
        private int creditScore;
        private String gmtCreated;
        private String gmtModified;
        private String id;
        private String isDeleted;
        private String isOwner;
        public String mobile;
        private String modifier;
        private String nickname;
        private String password;
        private UserAuthDTOBean userAuthDTO;
        private String userSex;
        private String userSexText;
        private String userStatus;
        private String userStatusText;

        public String getAvatarKey() {
            return avatarKey;
        }

        public void setAvatarKey(String avatarKey) {
            this.avatarKey = avatarKey;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public int getCreditScore() {
            return creditScore;
        }

        public void setCreditScore(int creditScore) {
            this.creditScore = creditScore;
        }

        public String getGmtCreated() {
            return gmtCreated;
        }

        public void setGmtCreated(String gmtCreated) {
            this.gmtCreated = gmtCreated;
        }

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getIsOwner() {
            return isOwner;
        }

        public void setIsOwner(String isOwner) {
            this.isOwner = isOwner;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public UserAuthDTOBean getUserAuthDTO() {
            return userAuthDTO;
        }

        public void setUserAuthDTO(UserAuthDTOBean userAuthDTO) {
            this.userAuthDTO = userAuthDTO;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getUserSexText() {
            return userSexText;
        }

        public void setUserSexText(String userSexText) {
            this.userSexText = userSexText;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getUserStatusText() {
            return userStatusText;
        }

        public void setUserStatusText(String userStatusText) {
            this.userStatusText = userStatusText;
        }

        public static class UserAuthDTOBean {
            /**
             * authStatus : pass
             * authStatusText : 拒绝
             * avatarKey : img.jpg
             * creator : string
             * driverCard1 : img.jpg
             * driverCard1Key : img.jpg
             * driverCard2 : img.jpg
             * driverCard2Key : img.jpg
             * gmtCreated : 2018-10-11T03:08:01.341Z
             * gmtModified : 2018-10-11T03:08:01.341Z
             * id : 0
             * idCard : img.jpg
             * idCardKey : img.jpg
             * isDeleted : string
             * mobile : 18871231747
             * modifier : string
             * userId : 0
             */

            private String authStatus;
            private String authStatusText;
            private String avatarKey;
            private String creator;
            private String driverCard1;
            private String driverCard1Key;
            private String driverCard2;
            private String driverCard2Key;
            private String gmtCreated;
            private String gmtModified;
            private int id;
            private String idCard;
            private String idCardKey;
            private String isDeleted;
            private String mobile;
            private String modifier;
            private int userId;

            public String getAuthStatus() {
                return authStatus;
            }

            public void setAuthStatus(String authStatus) {
                this.authStatus = authStatus;
            }

            public String getAuthStatusText() {
                return authStatusText;
            }

            public void setAuthStatusText(String authStatusText) {
                this.authStatusText = authStatusText;
            }

            public String getAvatarKey() {
                return avatarKey;
            }

            public void setAvatarKey(String avatarKey) {
                this.avatarKey = avatarKey;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public String getDriverCard1() {
                return driverCard1;
            }

            public void setDriverCard1(String driverCard1) {
                this.driverCard1 = driverCard1;
            }

            public String getDriverCard1Key() {
                return driverCard1Key;
            }

            public void setDriverCard1Key(String driverCard1Key) {
                this.driverCard1Key = driverCard1Key;
            }

            public String getDriverCard2() {
                return driverCard2;
            }

            public void setDriverCard2(String driverCard2) {
                this.driverCard2 = driverCard2;
            }

            public String getDriverCard2Key() {
                return driverCard2Key;
            }

            public void setDriverCard2Key(String driverCard2Key) {
                this.driverCard2Key = driverCard2Key;
            }

            public String getGmtCreated() {
                return gmtCreated;
            }

            public void setGmtCreated(String gmtCreated) {
                this.gmtCreated = gmtCreated;
            }

            public String getGmtModified() {
                return gmtModified;
            }

            public void setGmtModified(String gmtModified) {
                this.gmtModified = gmtModified;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getIdCardKey() {
                return idCardKey;
            }

            public void setIdCardKey(String idCardKey) {
                this.idCardKey = idCardKey;
            }

            public String getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                this.isDeleted = isDeleted;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getModifier() {
                return modifier;
            }

            public void setModifier(String modifier) {
                this.modifier = modifier;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
