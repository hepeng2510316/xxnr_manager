package com.app.xxnr.bean;


import java.io.Serializable;
import java.util.List;

/**
 * 类描述：
 * 作者：何鹏 on 2016/5/3 17:57
 * 邮箱：hepeng@xinxinnongren.com
 */
public class CustomerListResult extends BaseResult implements Serializable{

    public Users users;

    public static class Users implements Serializable{

        /**
         * count : 189
         * items : [{"_id":"57147da88c55dcf23405f386","registerAgent":"web","id":"b5097d74d1","account":"18331058523","password":"$2a$10$i0mop9qORy3kfkzSrsDjsO9ZSheMvLBHgI7UBg3CgA0Yf.b3fUJ9q","__v":0,"webLoginId":"b7512f997c","nickname":"啊啦啦","dateinvited":"2016-04-19T02:47:47.124Z","inviter":{"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"},"RSCInfo":{"products":[]},"isUserInfoFullFilled":false,"sex":false,"typeVerified":[],"type":"1","nameInitialType":2,"nameInitial":"#","namePinyin":"#","datecreated":"2016-04-18T06:24:40.190Z"},{"_id":"5710c007e1cad66a5e88c66b","registerAgent":"web","id":"8b2d38d489","account":"15101578395","password":"$2a$10$1xR6oE55H4LyJeVmiVY8guIfFFVNiIARD0CkBj28k6VcDsQtV5w2y","__v":0,"webLoginId":"28ed7ce4d3","appLoginId":"cc0bb6f8a6","RSCInfo":{"products":[]},"isUserInfoFullFilled":false,"sex":false,"typeVerified":[],"type":"1","nameInitialType":2,"nameInitial":"#","namePinyin":"#","datecreated":"2016-04-15T10:18:47.372Z"},{"_id":"570f3aab626895776bc5cab3","registerAgent":"IOS-v2.0","id":"68b57e1794","account":"15210638317","password":"$2a$10$RlO5Io.3vUsdYjo2U7U1V.qD/iR20nhNop1SPGSrExRCZejPusfr6","__v":0,"appLoginId":"102eca0530","name":"Tao","score":100,"nickname":"Taiwan","photo":"/images/original/1460622786836i6ixjemi.jpg","dateinvited":"2016-04-19T02:47:47.124Z","inviter":{"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"},"RSCInfo":{"name":"信心弄人","IDNo":"134524197807303345","phone":"18732456367","companyName":"哈哈","products":[],"companyAddress":{"town":"566700abb4e70620282818b4","province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab8","details":""}},"address":{"province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab8","town":"56670149f8a9db9c24a26e33"},"isUserInfoFullFilled":true,"sex":false,"typeVerified":[],"type":"1","nameInitialType":1,"nameInitial":"T","namePinyin":"tao","datecreated":"2016-04-14T06:37:31.149Z"},{"_id":"56f8b79a7b5604fe1e6d26cf","registerAgent":"IOS-v2.0","id":"fc3e955321","account":"15294852357","password":"$2a$10$r7JYoOZhAtW4hhG2lgKGkeg4aczpXImsu8pVUv7xoHwAK2alkLnwi","__v":0,"appLoginId":"e06ccb84ae","name":"素慧","score":100,"dateinvited":"2016-04-19T02:47:47.124Z","inviter":{"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"},"RSCInfo":{"name":"晏宁你那地方","IDNo":"431126199809878907","phone":"18219083457","companyName":"可可可","products":[],"companyAddress":{"town":"5667014af8a9db9c24a26e3f","province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab8","details":"天堂"}},"address":{"province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab0","county":"5649bd6e8eba3c20360b0135","town":"5666f081f6b0560c11733149"},"isUserInfoFullFilled":true,"sex":true,"typeVerified":[],"type":"5","nameInitialType":1,"nameInitial":"S","namePinyin":"suhui","datecreated":"2016-03-28T04:48:26.500Z"},{"_id":"56f8b1533869d1db1eb13b9d","registerAgent":"IOS-v2.0","id":"8747cbf339","account":"15236456419","password":"$2a$10$0RSTeRnw6U.ok7rDKsiZ2enEMHL1a7Q9FSTPpTQnfPfx/uF4H/.8q","__v":0,"appLoginId":"bf55b08b12","dateinvited":"2016-04-19T02:47:47.124Z","inviter":{"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"},"RSCInfo":{"products":[]},"isUserInfoFullFilled":false,"sex":false,"typeVerified":[],"type":"6","nameInitialType":2,"nameInitial":"#","namePinyin":"#","datecreated":"2016-03-28T04:21:39.260Z"},{"_id":"56f8af217b5604fe1e6d263b","registerAgent":"IOS-v2.0","id":"221a6509fc","account":"13671288039","password":"$2a$10$NilQEP80JvtnAlfA4TERx.nYrd68elBMli8QFbJoyZcnyKviJpNgu","__v":0,"appLoginId":"657073b698","name":"His","score":100,"dateinvited":"2016-04-19T02:47:47.124Z","inviter":{"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"},"RSCInfo":{"name":"嘿嘿","IDNo":"431126189319567834","phone":"18211101020","companyName":"新新弄人","products":[],"companyAddress":{"town":"566700abb4e70620282818b4","province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab8","details":"天仙胡同"}},"address":{"province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab1","county":"5649bd6e8eba3c20360b0141","town":"5666f085f6b0560c11733211"},"isUserInfoFullFilled":true,"sex":true,"typeVerified":[],"type":"5","nameInitialType":1,"nameInitial":"H","namePinyin":"his","datecreated":"2016-03-28T04:12:17.790Z"},{"_id":"56d6d1c7ea36171d63d2a1e6","registerAgent":"Android-v2.0","id":"7d1853749c","account":"18732199883","password":"$2a$10$pL8F9RhV4C6N11pnbfX.IO7JXc9YoKB71bNRJHO.oiaRW4YD3F9/a","__v":0,"appLoginId":"feaf8d0a9c","webLoginId":"cbb67c838a","name":"看看改没改","score":102,"inviter":{"id":"a05c6d01efd54beba716b1acdb606524","account":"15201532357","nickname":"靳小美","name":"靳美佳","photo":"/images/original/1453881979592xh41jor.jpg"},"dateinvited":"2016-04-25T05:01:28.102Z","RSCInfo":{"name":"齐美佳","IDNo":"411223198309230623","phone":"13512075689","companyName":"佳美奇瑞宁陵直营店","supportEPOS":false,"EPOSNo":"","products":["56a5b94a3e19193a6cc33a29","56d6968bc78fdecd15fb11a3"],"companyAddress":{"province":"5649bd6c8eba3c20360afa0a","town":"5666f0a1f6b0560c11733835","details":"离车城200米左右，离宁陵县主街道300米左右","county":"5649bd6e8eba3c20360b01b0","city":"5649bd6c8eba3c20360afabe"}},"address":{"province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab0","county":"5649bd6e8eba3c20360b0135","town":"5666f082f6b0560c1173314b"},"isUserInfoFullFilled":true,"sex":true,"typeVerified":["5","6"],"type":"6","nameInitialType":1,"nameInitial":"K","namePinyin":"kankangaimeigai","datecreated":"2016-03-02T11:43:03.431Z"},{"_id":"56d2ce66295199af731c5733","registerAgent":"IOS-v2.0","id":"83e31f5f98","account":"18790674259","password":"$2a$10$MKXH0OeOInFqN95EzLLjRe8A8.cnvn2ypAvStVAN1OIA5aOH/enKW","__v":0,"appLoginId":"add0cc9d94","webLoginId":"f7a2b8f05b","name":"Ning","score":100,"dateinvited":"2016-04-19T02:47:47.124Z","inviter":{"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"},"RSCInfo":{"products":[]},"address":{"province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab0","county":"5649bd6e8eba3c20360b0137","town":"5666f082f6b0560c11733169"},"isUserInfoFullFilled":true,"sex":true,"typeVerified":[],"type":"5","nameInitialType":1,"nameInitial":"N","namePinyin":"ning","datecreated":"2016-02-28T10:39:34.609Z"},{"_id":"56aefac16f4049910e25361d","registerAgent":"web","id":"6e39f752cf","account":"15000000001","password":"$2a$10$auXcjluE90zyYaN7R/gYAuMvrdJFCDmM7hfLHcvtJgxGFBWZL4Bhm","__v":0,"webLoginId":"32ad53a7a9","name":"假的用户2","score":100,"inviter":{"id":"809f4ee124ec4f1598e9a740d5d3927d","account":"15110102070","nickname":"水怪丙","name":"鲁琲","photo":"/images/original/14485278398464iy66r.jpg"},"dateinvited":"2016-02-01T06:58:57.438Z","RSCInfo":{"products":[]},"address":{"province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab0","county":"5649bd6e8eba3c20360b0135","town":"5666f081f6b0560c11733149"},"isUserInfoFullFilled":true,"sex":false,"typeVerified":[],"type":"5","nameInitialType":1,"nameInitial":"J","namePinyin":"jiadiyonghu2","datecreated":"2016-02-01T06:27:13.493Z"},{"_id":"56aef81e71c116a30db94e10","registerAgent":"web","id":"3c3181c197","account":"15000000000","password":"$2a$10$L7qjfSoCmMTqc1.5v9oZwurJGHhY3kraBdhcELJe72IRq8gPfragW","__v":0,"webLoginId":"e45daaf5b2","name":"Fake","score":100,"dateinvited":"2016-04-19T02:47:47.124Z","inviter":{"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"},"RSCInfo":{"products":[]},"address":{"province":"5649bd6c8eba3c20360afa0a","city":"5649bd6c8eba3c20360afab0","county":"5649bd6e8eba3c20360b0135","town":"5666f081f6b0560c11733149"},"isUserInfoFullFilled":true,"sex":false,"typeVerified":[],"type":"2","nameInitialType":1,"nameInitial":"F","namePinyin":"fake","datecreated":"2016-02-01T06:15:58.166Z"}]
         * pages : 19
         * page : 1
         */

        public int count;
        public int pages;
        public int page;
        /**
         * _id : 57147da88c55dcf23405f386
         * registerAgent : web
         * id : b5097d74d1
         * account : 18331058523
         * password : $2a$10$i0mop9qORy3kfkzSrsDjsO9ZSheMvLBHgI7UBg3CgA0Yf.b3fUJ9q
         * __v : 0
         * webLoginId : b7512f997c
         * nickname : 啊啦啦
         * dateinvited : 2016-04-19T02:47:47.124Z
         * inviter : {"id":"d68322a9c3f5400a8448a5a94a7db3f9","account":"13512721874","nickname":"凯凯王","name":"凯凯王","photo":"/images/original/1459423214795iwvwvcxr.jpg"}
         * RSCInfo : {"products":[]}
         * isUserInfoFullFilled : false
         * sex : false
         * typeVerified : []
         * type : 1
         * nameInitialType : 2
         * nameInitial : #
         * namePinyin : #
         * datecreated : 2016-04-18T06:24:40.190Z
         */

        public List<ItemsBean> items;

        public static class ItemsBean  implements Serializable{
            public String _id;
            public String registerAgent;
            public String id;
            public String account;
            public String password;
            public int __v;
            public String webLoginId;
            public String nickname;
            public String name;
            public String dateinvited;
            /**
             * id : d68322a9c3f5400a8448a5a94a7db3f9
             * account : 13512721874
             * nickname : 凯凯王
             * name : 凯凯王
             * photo : /images/original/1459423214795iwvwvcxr.jpg
             */

            public InviterBean inviter;
            public RSCInfoBean RSCInfo;
            public boolean isUserInfoFullFilled;
            public boolean sex;
            public String type;
            public int nameInitialType;
            public String nameInitial;
            public String namePinyin;
            public String datecreated;
            public List<String> typeVerified;

            public static class InviterBean implements Serializable{
                public String id;
                public String account;
                public String nickname;
                public String name;
                public String photo;
            }

            public static class RSCInfoBean implements Serializable{
                public List<String> products;
            }
        }
    }

}
