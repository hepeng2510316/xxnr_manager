package com.app.xxnr.bean;


import java.util.List;

/**
 * Created by 何鹏 on 2016/5/3.
 */
public class CustomerDetailResult extends BaseResult {

    public UserBean user;

    public static class UserBean {
        public String _id;
        public String registerAgent;
        public String id;
        public String account;
        public String password;
        public int __v;
        public String appLoginId;
        public String webLoginId;
        public String name;
        public int score;
        /**
         * _id : 5649bd6f8eba3c20360b0779
         * id : a05c6d01efd54beba716b1acdb606524
         * account : 15201532357
         * nickname : 靳小美
         * name : 靳美佳
         * photo : /images/original/1453881979592xh41jor.jpg
         */

        public InviterBean inviter;
        public String dateinvited;
        /**
         * name : 齐美佳
         * IDNo : 411223198309230623
         * phone : 13512075689
         * companyName : 佳美奇瑞宁陵直营店
         * supportEPOS : false
         * EPOSNo :
         * products : ["56a5b94a3e19193a6cc33a29","56d6968bc78fdecd15fb11a3"]
         * companyAddress : {"province":"5649bd6c8eba3c20360afa0a","town":"5666f0a1f6b0560c11733835","details":"离车城200米左右，离宁陵县主街道300米左右","county":"5649bd6e8eba3c20360b01b0","city":"5649bd6c8eba3c20360afabe"}
         */

        public RSCInfoBean RSCInfo;
        /**
         * province : {"_id":"5649bd6c8eba3c20360afa0a","shortname":"H","uppername":"河南","name":"河南","id":"58054e5ba551445","tid":"410000"}
         * city : {"_id":"5649bd6c8eba3c20360afab0","provinceid":"58054e5ba551445","uppername":"鄭州","name":"郑州","id":"493e8b00ce261e9","tid":"410100"}
         * county : {"_id":"5649bd6e8eba3c20360b0135","provinceid":"58054e5ba551445","cityid":"493e8b00ce261e9","uppername":"中原","name":"中原","id":"d68f4bb95a8557f","tid":"410102"}
         * town : {"_id":"5666f082f6b0560c1173314b","id":"9e9bba584d","tid":"410102003","name":"棉纺路街道","chinesepinyin":"mian fang lu jie dao","countyid":"d68f4bb95a8557f","cityid":"493e8b00ce261e9","provinceid":"58054e5ba551445"}
         */

        public AddressBean address;
        public boolean isUserInfoFullFilled;
        public boolean sex;
        public String type;
        public int nameInitialType;
        public String nameInitial;
        public String namePinyin;
        public String datecreated;
        public boolean isVerified;
        public boolean isXXNRAgent;
        public boolean isRSC;
        public List<String> typeVerified;

        public static class InviterBean {
            public String _id;
            public String id;
            public String account;
            public String nickname;
            public String name;
            public String photo;
        }

        public static class RSCInfoBean {
            public String name;
            public String IDNo;
            public String phone;
            public String companyName;
            public boolean supportEPOS;
            public String EPOSNo;
            /**
             * province : 5649bd6c8eba3c20360afa0a
             * town : 5666f0a1f6b0560c11733835
             * details : 离车城200米左右，离宁陵县主街道300米左右
             * county : 5649bd6e8eba3c20360b01b0
             * city : 5649bd6c8eba3c20360afabe
             */

            public CompanyAddressBean companyAddress;
            public List<String> products;

            public static class CompanyAddressBean {
                public String province;
                public String town;
                public String details;
                public String county;
                public String city;
            }
        }

        public static class AddressBean {
            /**
             * _id : 5649bd6c8eba3c20360afa0a
             * shortname : H
             * uppername : 河南
             * name : 河南
             * id : 58054e5ba551445
             * tid : 410000
             */

            public ProvinceBean province;
            /**
             * _id : 5649bd6c8eba3c20360afab0
             * provinceid : 58054e5ba551445
             * uppername : 鄭州
             * name : 郑州
             * id : 493e8b00ce261e9
             * tid : 410100
             */

            public CityBean city;
            /**
             * _id : 5649bd6e8eba3c20360b0135
             * provinceid : 58054e5ba551445
             * cityid : 493e8b00ce261e9
             * uppername : 中原
             * name : 中原
             * id : d68f4bb95a8557f
             * tid : 410102
             */

            public CountyBean county;
            /**
             * _id : 5666f082f6b0560c1173314b
             * id : 9e9bba584d
             * tid : 410102003
             * name : 棉纺路街道
             * chinesepinyin : mian fang lu jie dao
             * countyid : d68f4bb95a8557f
             * cityid : 493e8b00ce261e9
             * provinceid : 58054e5ba551445
             */

            public TownBean town;

            public static class ProvinceBean {
                public String _id;
                public String shortname;
                public String uppername;
                public String name;
                public String id;
                public String tid;
            }

            public static class CityBean {
                public String _id;
                public String provinceid;
                public String uppername;
                public String name;
                public String id;
                public String tid;
            }

            public static class CountyBean {
                public String _id;
                public String provinceid;
                public String cityid;
                public String uppername;
                public String name;
                public String id;
                public String tid;
            }

            public static class TownBean {
                public String _id;
                public String id;
                public String tid;
                public String name;
                public String chinesepinyin;
                public String countyid;
                public String cityid;
                public String provinceid;
            }
        }
    }
}
