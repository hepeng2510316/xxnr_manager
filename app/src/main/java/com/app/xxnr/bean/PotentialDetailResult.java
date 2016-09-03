package com.app.xxnr.bean;


import java.util.List;

/**
 * Created by 何鹏 on 2016/5/4.
 */
public class PotentialDetailResult extends BaseResult {


    /**
     * _id : 57202ab47c66843e5cd6e38a
     * user : {"name":"呵呵"}
     * name : 张三丰
     * phone : 13271788711
     * remarks : 哈哈
     * isRegistered : false
     * buyIntentions : [{"_id":"56a72696c1813a7c109138c7","name":"瑞风S5"},{"_id":"56a72696c1813a7c109138c9","name":"瑞风M3"}]
     * address : {"province":{"shortname":"H","uppername":"河南","name":"河南","id":"58054e5ba551445","tid":"410000"},"county":{"provinceid":"58054e5ba551445","cityid":"24182401c85c496","uppername":"滑縣","name":"滑县","id":"20d6cd0ef90c839","tid":"410526"},"town":{"id":"74bd18f34a","tid":"410526106","name":"万古镇","chinesepinyin":"wan gu zhen","countyid":"20d6cd0ef90c839","cityid":"24182401c85c496","provinceid":"58054e5ba551445"},"city":{"provinceid":"58054e5ba551445","uppername":"安陽","name":"安阳","id":"24182401c85c496","tid":"410500"}}
     * sex : false
     * nameInitialType : 1
     * nameInitial : Z
     * namePinyin : zhangsanfeng
     */

    public PotentialCustomerBean potentialCustomer;

    public static class PotentialCustomerBean {
        public String _id;
        /**
         * name : 呵呵
         */

        public UserBean user;
        public String name;
        public String phone;
        public String remarks;
        public boolean isRegistered;
        /**
         * province : {"shortname":"H","uppername":"河南","name":"河南","id":"58054e5ba551445","tid":"410000"}
         * county : {"provinceid":"58054e5ba551445","cityid":"24182401c85c496","uppername":"滑縣","name":"滑县","id":"20d6cd0ef90c839","tid":"410526"}
         * town : {"id":"74bd18f34a","tid":"410526106","name":"万古镇","chinesepinyin":"wan gu zhen","countyid":"20d6cd0ef90c839","cityid":"24182401c85c496","provinceid":"58054e5ba551445"}
         * city : {"provinceid":"58054e5ba551445","uppername":"安陽","name":"安阳","id":"24182401c85c496","tid":"410500"}
         */

        public AddressBean address;
        public boolean sex;
        public int nameInitialType;
        public String nameInitial;
        public String namePinyin;
        /**
         * _id : 56a72696c1813a7c109138c7
         * name : 瑞风S5
         */

        public List<BuyIntentionsBean> buyIntentions;

        public static class UserBean {
            public String name;
        }

        public static class AddressBean {
            /**
             * shortname : H
             * uppername : 河南
             * name : 河南
             * id : 58054e5ba551445
             * tid : 410000
             */

            public ProvinceBean province;
            /**
             * provinceid : 58054e5ba551445
             * cityid : 24182401c85c496
             * uppername : 滑縣
             * name : 滑县
             * id : 20d6cd0ef90c839
             * tid : 410526
             */

            public CountyBean county;
            /**
             * id : 74bd18f34a
             * tid : 410526106
             * name : 万古镇
             * chinesepinyin : wan gu zhen
             * countyid : 20d6cd0ef90c839
             * cityid : 24182401c85c496
             * provinceid : 58054e5ba551445
             */

            public TownBean town;
            /**
             * provinceid : 58054e5ba551445
             * uppername : 安陽
             * name : 安阳
             * id : 24182401c85c496
             * tid : 410500
             */

            public CityBean city;

            public static class ProvinceBean {
                public String shortname;
                public String uppername;
                public String name;
                public String id;
                public String tid;
            }

            public static class CountyBean {
                public String provinceid;
                public String cityid;
                public String uppername;
                public String name;
                public String id;
                public String tid;
            }

            public static class TownBean {
                public String id;
                public String tid;
                public String name;
                public String chinesepinyin;
                public String countyid;
                public String cityid;
                public String provinceid;
            }

            public static class CityBean {
                public String provinceid;
                public String uppername;
                public String name;
                public String id;
                public String tid;
            }
        }

        public static class BuyIntentionsBean {
            public String _id;
            public String name;
        }
    }
}
