package com.app.xxnr.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by 何鹏 on 2016/5/6.
 */
public class OrderDetailResult extends BaseResult {


    /**
     * _id : 572add68bcaec5721cbd2b14
     * buyerName : 靳美佳
     * buyerPhone : 15201532357
     * buyerId : a05c6d01efd54beba716b1acdb606524
     * price : 2
     * deposit : 0.8
     * payType : 1
     * deliverStatus : 1
     * id : d9279a9985
     * paymentId : fa8a8fea17
     * consigneeName : 靳小美
     * consigneePhone : 15201532357
     * duePrice : 0.8
     * __v : 0
     * pendingApprove : false
     * depositPaid : false
     * RSCInfo : {"RSC":"5649bd6f8eba3c20360b0779","RSCAddress":"河南商丘梁园张阁镇蔬果超市南侧50米（张阁镇公交车站下来脸朝东）","companyName":"佳美农资啥都卖旗舰店","RSCPhone":"15201532358"}
     * deliveryType : 1
     * subOrders : [{"id":"f4e991cd56","price":0.8,"type":"deposit","_id":"572add68bcaec5721cbd2b17","payStatus":1,"paidPrice":0,"paidTimes":0,"closedTimes":0,"payments":[{"id":"fa8a8fea17","slice":1,"price":0.8,"suborderId":"f4e991cd56","payType":1,"_id":"572add68bcaec5721cbd2b15","thirdPartyRecorded":false,"isClosed":false,"payStatus":1,"dateCreated":"2016-05-05T05:43:04.268Z"}],"datePaid":null},{"id":"f308bb0ba3","price":1.2,"type":"balance","_id":"572add68bcaec5721cbd2b16","payStatus":1,"paidPrice":0,"paidTimes":0,"closedTimes":0,"payments":[],"datePaid":null}]
     * isClosed : false
     * payStatus : 1
     * SKUs : [{"ref":"56e0dab1247937ef66f48961","productId":"6926d251a1","price":0.2,"deposit":0.08,"productName":"为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦","name":"为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦 - 1.8L 自动（GDL 6DCT） - 全球首发豪华智能无敌霹雳宇宙队人手一台用来给蝙蝠侠大战超人其实我是钢铁侠发明的炫酷车型 - 超级无敌美丽七彩斑斓炫酷色","thumbnail":"/images/thumbnail/6C7D8F66/1460008755108wzcqsemi.jpg?category=6C7D8F66&thumb=true","count":10,"category":"汽车","_id":"572add68bcaec5721cbd2b18","additions":[],"attributes":[{"ref":"56e0da39247937ef66f48946","value":"1.8L 自动（GDL 6DCT）","name":"变速箱","_id":"572843bb0fab86d170da1488"},{"ref":"56f8cc20f885e1cd328ac3f0","value":"全球首发豪华智能无敌霹雳宇宙队人手一台用来给蝙蝠侠大战超人其实我是钢铁侠发明的炫酷车型","name":"车型配置","_id":"572843bb0fab86d170da1487"},{"ref":"56f2478c9fd23ea67271482c","value":"超级无敌美丽七彩斑斓炫酷色","name":"颜色","_id":"572843bb0fab86d170da1486"}],"deliverStatus":1}]
     * products : []
     * dateCreated : 2016-05-05T05:43:04.268Z
     * orderType : 1
     * order : {"totalPrice":2,"deposit":0.8,"dateCreated":"2016-05-05T05:43:04.268Z","orderStatus":{"type":1,"value":"待付款"},"payment":{"id":"fa8a8fea17","slice":1,"price":0.8,"suborderId":"f4e991cd56","payType":1,"_id":"572add68bcaec5721cbd2b15","thirdPartyRecorded":false,"isClosed":false,"payStatus":1,"dateCreated":"2016-05-05T05:43:04.268Z"}}
     */

    public DatasBean datas;

    public static class DatasBean {
        public String _id;
        public String buyerName;
        public String buyerPhone;
        public String buyerId;
        public double price;
        public double deposit;
        public int payType;
        public int deliverStatus;
        public String id;
        public String paymentId;
        public String consigneeName;
        public String consigneePhone;
        public String consigneeAddress;
        public double duePrice;
        public int __v;
        public boolean pendingApprove;
        public boolean depositPaid;
        /**
         * RSC : 5649bd6f8eba3c20360b0779
         * RSCAddress : 河南商丘梁园张阁镇蔬果超市南侧50米（张阁镇公交车站下来脸朝东）
         * companyName : 佳美农资啥都卖旗舰店
         * RSCPhone : 15201532358
         */

        public RSCInfoBean RSCInfo;
        public int deliveryType;
        public boolean isClosed;
        public int payStatus;
        public String dateCreated;
        public int orderType;
        /**
         * totalPrice : 2
         * deposit : 0.8
         * dateCreated : 2016-05-05T05:43:04.268Z
         * orderStatus : {"type":1,"value":"待付款"}
         * payment : {"id":"fa8a8fea17","slice":1,"price":0.8,"suborderId":"f4e991cd56","payType":1,"_id":"572add68bcaec5721cbd2b15","thirdPartyRecorded":false,"isClosed":false,"payStatus":1,"dateCreated":"2016-05-05T05:43:04.268Z"}
         */

        public OrderBean order;
        /**
         * id : f4e991cd56
         * price : 0.8
         * type : deposit
         * _id : 572add68bcaec5721cbd2b17
         * payStatus : 1
         * paidPrice : 0
         * paidTimes : 0
         * closedTimes : 0
         * payments : [{"id":"fa8a8fea17","slice":1,"price":0.8,"suborderId":"f4e991cd56","payType":1,"_id":"572add68bcaec5721cbd2b15","thirdPartyRecorded":false,"isClosed":false,"payStatus":1,"dateCreated":"2016-05-05T05:43:04.268Z"}]
         * datePaid : null
         */

        public List<SubOrdersBean> subOrders;
        /**
         * ref : 56e0dab1247937ef66f48961
         * productId : 6926d251a1
         * price : 0.2
         * deposit : 0.08
         * productName : 为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦
         * name : 为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦 - 1.8L 自动（GDL 6DCT） - 全球首发豪华智能无敌霹雳宇宙队人手一台用来给蝙蝠侠大战超人其实我是钢铁侠发明的炫酷车型 - 超级无敌美丽七彩斑斓炫酷色
         * thumbnail : /images/thumbnail/6C7D8F66/1460008755108wzcqsemi.jpg?category=6C7D8F66&thumb=true
         * count : 10
         * category : 汽车
         * _id : 572add68bcaec5721cbd2b18
         * additions : []
         * attributes : [{"ref":"56e0da39247937ef66f48946","value":"1.8L 自动（GDL 6DCT）","name":"变速箱","_id":"572843bb0fab86d170da1488"},{"ref":"56f8cc20f885e1cd328ac3f0","value":"全球首发豪华智能无敌霹雳宇宙队人手一台用来给蝙蝠侠大战超人其实我是钢铁侠发明的炫酷车型","name":"车型配置","_id":"572843bb0fab86d170da1487"},{"ref":"56f2478c9fd23ea67271482c","value":"超级无敌美丽七彩斑斓炫酷色","name":"颜色","_id":"572843bb0fab86d170da1486"}]
         * deliverStatus : 1
         */

        public List<SKUsBean> SKUs;
        public List<ProductBean> products;

        public static class ProductBean {
            public String _id;
            public String category;
            public String name;
            public double deposit;
            public String id;
            public String thumbnail;
            public double price;
            public int count;
            public String deliverStatus;

        }


        public static class RSCInfoBean {
            public String RSC;
            public String RSCAddress;
            public String companyName;
            public String RSCPhone;
        }

        public static class OrderBean {
            public double totalPrice;
            public double deposit;
            public String dateCreated;
            public boolean pendingDeliverToRSC;

            /**
             * type : 1
             * value : 待付款
             */

            public OrderStatusBean orderStatus;
            /**
             * id : fa8a8fea17
             * slice : 1
             * price : 0.8
             * suborderId : f4e991cd56
             * payType : 1
             * _id : 572add68bcaec5721cbd2b15
             * thirdPartyRecorded : false
             * isClosed : false
             * payStatus : 1
             * dateCreated : 2016-05-05T05:43:04.268Z
             */

            public PaymentBean payment;

            public static class OrderStatusBean {
                public int type;
                public String value;
            }

            public static class PaymentBean {
                public String id;
                public int slice;
                public double price;
                public String suborderId;
                public int payType;
                public String _id;
                public boolean thirdPartyRecorded;
                public boolean isClosed;
                public int payStatus;
                public String dateCreated;
            }
        }

        public static class SubOrdersBean implements Serializable{
            public String id;
            public double price;
            public String type;
            public String _id;
            public int payStatus;
            public double paidPrice;
            public long paidTimes;
            public long closedTimes;
            public Object datePaid;
            /**
             * id : fa8a8fea17
             * slice : 1
             * price : 0.8
             * suborderId : f4e991cd56
             * payType : 1
             * _id : 572add68bcaec5721cbd2b15
             * thirdPartyRecorded : false
             * isClosed : false
             * payStatus : 1
             * dateCreated : 2016-05-05T05:43:04.268Z
             */

            public List<PaymentsBean> payments;

            public static class PaymentsBean implements Serializable{
                public String id;
                public int slice;
                public double price;
                public String suborderId;
                public int payType;
                public String _id;
                public boolean thirdPartyRecorded;
                public boolean isClosed;
                public int payStatus;
                public String dateCreated;
                public String datePaid;
                public String RSCCompanyName;
            }
        }

        public static class SKUsBean {
            public String ref;
            public String productId;
            public double price;
            public double deposit;
            public String productName;
            public String name;
            public String thumbnail;
            public int count;
            public String category;
            public String _id;
            public int deliverStatus;
            public List<Additions> additions; //附加选项
            /**
             * ref : 56e0da39247937ef66f48946
             * value : 1.8L 自动（GDL 6DCT）
             * name : 变速箱
             * _id : 572843bb0fab86d170da1488
             */

            public List<AttributesBean> attributes;

            public static class AttributesBean {
                public String ref;
                public String value;
                public String name;
                public String _id;
            }


            public static class Additions implements Serializable {
                public double price;//附加选项价格
                public String name;// 附加选项名称
                public String ref;//附加选项_id
                public String _id;//附加选项_id
            }
        }
    }
}
