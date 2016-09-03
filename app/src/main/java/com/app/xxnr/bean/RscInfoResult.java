package com.app.xxnr.bean;


import java.util.List;

/**
 * Created by 何鹏 on 2016/5/4.
 */
public class RscInfoResult extends BaseResult {


    /**
     * companyAddress : {"city":{"_id":"5649bd6c8eba3c20360afabe","provinceid":"58054e5ba551445","uppername":"商丘","name":"商丘","id":"4bc576e06918d90","tid":"411400"},"county":{"_id":"5649bd6e8eba3c20360b01b0","provinceid":"58054e5ba551445","cityid":"4bc576e06918d90","uppername":"寧陵","name":"宁陵","id":"68ff11f48bbb314","tid":"411423"},"details":"离车城200米左右，离宁陵县主街道300米左右","town":{"_id":"5666f0a1f6b0560c11733835","id":"ff826d8b5a","tid":"411423100","name":"城关镇","chinesepinyin":"cheng guan zhen","countyid":"68ff11f48bbb314","cityid":"4bc576e06918d90","provinceid":"58054e5ba551445"},"province":{"_id":"5649bd6c8eba3c20360afa0a","shortname":"H","uppername":"河南","name":"河南","id":"58054e5ba551445","tid":"410000"}}
     * products : [{"_id":"56a5b94a3e19193a6cc33a29","category":"汽车","brand":"56a217800ef93e784e27c349","name":"为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦"},{"_id":"56d6968bc78fdecd15fb11a3","category":"汽车","brand":"56a217800ef93e784e27c349","name":"奇瑞汽车 - 艾瑞泽M7"}]
     * EPOSNo :
     * supportEPOS : false
     * companyName : 佳美奇瑞宁陵直营店
     * phone : 13512075689
     * IDNo : 411223198309230623
     * name : 齐美佳
     */

    public RSCInfoBean RSCInfo;
    /**
     * RSCInfo : {"companyAddress":{"city":{"_id":"5649bd6c8eba3c20360afabe","provinceid":"58054e5ba551445","uppername":"商丘","name":"商丘","id":"4bc576e06918d90","tid":"411400"},"county":{"_id":"5649bd6e8eba3c20360b01b0","provinceid":"58054e5ba551445","cityid":"4bc576e06918d90","uppername":"寧陵","name":"宁陵","id":"68ff11f48bbb314","tid":"411423"},"details":"离车城200米左右，离宁陵县主街道300米左右","town":{"_id":"5666f0a1f6b0560c11733835","id":"ff826d8b5a","tid":"411423100","name":"城关镇","chinesepinyin":"cheng guan zhen","countyid":"68ff11f48bbb314","cityid":"4bc576e06918d90","provinceid":"58054e5ba551445"},"province":{"_id":"5649bd6c8eba3c20360afa0a","shortname":"H","uppername":"河南","name":"河南","id":"58054e5ba551445","tid":"410000"}},"products":[{"_id":"56a5b94a3e19193a6cc33a29","category":"汽车","brand":"56a217800ef93e784e27c349","name":"为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦"},{"_id":"56d6968bc78fdecd15fb11a3","category":"汽车","brand":"56a217800ef93e784e27c349","name":"奇瑞汽车 - 艾瑞泽M7"}],"EPOSNo":"","supportEPOS":false,"companyName":"佳美奇瑞宁陵直营店","phone":"13512075689","IDNo":"411223198309230623","name":"齐美佳"}
     * id : 7d1853749c
     * account : 18732199883
     */

    public String id;
    public String account;

    public static class RSCInfoBean {
        /**
         * city : {"_id":"5649bd6c8eba3c20360afabe","provinceid":"58054e5ba551445","uppername":"商丘","name":"商丘","id":"4bc576e06918d90","tid":"411400"}
         * county : {"_id":"5649bd6e8eba3c20360b01b0","provinceid":"58054e5ba551445","cityid":"4bc576e06918d90","uppername":"寧陵","name":"宁陵","id":"68ff11f48bbb314","tid":"411423"}
         * details : 离车城200米左右，离宁陵县主街道300米左右
         * town : {"_id":"5666f0a1f6b0560c11733835","id":"ff826d8b5a","tid":"411423100","name":"城关镇","chinesepinyin":"cheng guan zhen","countyid":"68ff11f48bbb314","cityid":"4bc576e06918d90","provinceid":"58054e5ba551445"}
         * province : {"_id":"5649bd6c8eba3c20360afa0a","shortname":"H","uppername":"河南","name":"河南","id":"58054e5ba551445","tid":"410000"}
         */

        public CompanyAddressBean companyAddress;
        public String EPOSNo;
        public boolean supportEPOS;
        public String companyName;
        public String phone;
        public String IDNo;
        public String name;
        /**
         * _id : 56a5b94a3e19193a6cc33a29
         * category : 汽车
         * brand : 56a217800ef93e784e27c349
         * name : 为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦
         */

        public List<ProductsBean> products;

        public static class CompanyAddressBean {
            /**
             * _id : 5649bd6c8eba3c20360afabe
             * provinceid : 58054e5ba551445
             * uppername : 商丘
             * name : 商丘
             * id : 4bc576e06918d90
             * tid : 411400
             */

            public CityBean city;
            /**
             * _id : 5649bd6e8eba3c20360b01b0
             * provinceid : 58054e5ba551445
             * cityid : 4bc576e06918d90
             * uppername : 寧陵
             * name : 宁陵
             * id : 68ff11f48bbb314
             * tid : 411423
             */

            public CountyBean county;
            public String details;
            /**
             * _id : 5666f0a1f6b0560c11733835
             * id : ff826d8b5a
             * tid : 411423100
             * name : 城关镇
             * chinesepinyin : cheng guan zhen
             * countyid : 68ff11f48bbb314
             * cityid : 4bc576e06918d90
             * provinceid : 58054e5ba551445
             */

            public TownBean town;
            /**
             * _id : 5649bd6c8eba3c20360afa0a
             * shortname : H
             * uppername : 河南
             * name : 河南
             * id : 58054e5ba551445
             * tid : 410000
             */

            public ProvinceBean province;

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

            public static class ProvinceBean {
                public String _id;
                public String shortname;
                public String uppername;
                public String name;
                public String id;
                public String tid;
            }
        }

        public static class ProductsBean {
            public String _id;
            public String category;
            public String brand;
            public String name;
        }
    }
}
