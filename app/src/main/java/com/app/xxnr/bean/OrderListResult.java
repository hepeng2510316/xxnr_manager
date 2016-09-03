package com.app.xxnr.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by 何鹏 on 2016/5/5.
 */
public class OrderListResult extends BaseResult {


    /**
     * count : 2031
     * items : [{"id":"2169dad83f","paymentId":"bd0040f118","price":0.12,"deposit":0.01,"consigneeAddress":"河南郑州中原林山寨街道测试地址1","consigneeName":"靳美佳","consigneePhone":"15201532357","buyerName":"靳美佳","buyerPhone":"15201532357","payType":1,"products":[],"SKUs":[{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":1,"category":"化肥","_id":"572addace25b31161c32bae3","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"572addace25b31161c32bae4"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":1}],"duePrice":0.01,"deliveryType":2,"payStatus":1,"deliverStatus":1,"isClosed":false,"typeValue":1,"dateCreated":"2016-05-05T05:44:12.680Z","order":{"totalPrice":"0.12","deposit":"0.01","dateCreated":"2016-05-05T05:44:12.680Z","orderStatus":{"type":1,"value":"待付款"}}},{"id":"d9279a9985","paymentId":"fa8a8fea17","price":2,"deposit":0.8,"consigneeName":"靳小美","consigneePhone":"15201532357","buyerName":"靳美佳","buyerPhone":"15201532357","payType":1,"products":[],"SKUs":[{"ref":"56e0dab1247937ef66f48961","productId":"6926d251a1","price":0.2,"deposit":0.08,"productName":"为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦","name":"为了测试多品牌选取上一个奇瑞的车可以同时看商品名称和属性超级长的时候的样子也是够够的受不了啦 - 1.8L 自动（GDL 6DCT） - 全球首发豪华智能无敌霹雳宇宙队人手一台用来给蝙蝠侠大战超人其实我是钢铁侠发明的炫酷车型 - 超级无敌美丽七彩斑斓炫酷色","thumbnail":"/images/thumbnail/6C7D8F66/1460008755108wzcqsemi.jpg?category=6C7D8F66&thumb=true","count":10,"category":"汽车","_id":"572add68bcaec5721cbd2b18","additions":[],"attributes":[{"ref":"56e0da39247937ef66f48946","value":"1.8L 自动（GDL 6DCT）","name":"变速箱","_id":"572843bb0fab86d170da1488"},{"ref":"56f8cc20f885e1cd328ac3f0","value":"全球首发豪华智能无敌霹雳宇宙队人手一台用来给蝙蝠侠大战超人其实我是钢铁侠发明的炫酷车型","name":"车型配置","_id":"572843bb0fab86d170da1487"},{"ref":"56f2478c9fd23ea67271482c","value":"超级无敌美丽七彩斑斓炫酷色","name":"颜色","_id":"572843bb0fab86d170da1486"}],"deliverStatus":1}],"duePrice":0.8,"deliveryType":1,"payStatus":1,"deliverStatus":1,"RSCInfo":{"RSC":"5649bd6f8eba3c20360b0779","RSCAddress":"河南商丘梁园张阁镇蔬果超市南侧50米（张阁镇公交车站下来脸朝东）","companyName":"佳美农资啥都卖旗舰店","RSCPhone":"15201532358"},"isClosed":false,"typeValue":1,"dateCreated":"2016-05-05T05:43:04.268Z","order":{"totalPrice":"2.00","deposit":"0.80","dateCreated":"2016-05-05T05:43:04.268Z","orderStatus":{"type":1,"value":"待付款"}}},{"id":"97aeda2e7d","paymentId":"aa20dd0584","price":0.12,"deposit":0.01,"consigneeName":"鲁琲","consigneePhone":"15110102070","buyerName":"鲁琲","buyerPhone":"15110102070","payType":1,"products":[],"SKUs":[{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":1,"category":"化肥","_id":"572abc90489d1d1436cb419f","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"572abc90489d1d1436cb41a0"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":5,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateRSCReceived":"2016-05-05T03:24:09.384Z","dateSet":"2016-05-05T03:24:09.384Z","dateDelivered":"2016-05-05T03:24:47.179Z","dateConfirmed":"2016-05-05T03:24:47.206Z"}],"duePrice":0,"deliveryType":1,"payStatus":2,"deliverStatus":5,"RSCInfo":{"RSC":"5649bd6f8eba3c20360b0765","RSCAddress":"河南开封禹王台三里堡街道水中","companyName":"新农江淮化肥鲁排卖身店","RSCPhone":"15110102070"},"isClosed":false,"typeValue":4,"dateCreated":"2016-05-05T03:22:56.006Z","datePaid":"2016-05-05T03:24:23.461Z","datePendingDeliver":"2016-05-05T03:24:23.462Z","dateCompleted":"2016-05-05T03:24:47.206Z","order":{"totalPrice":"0.12","deposit":"0.01","dateCreated":"2016-05-05T03:22:56.006Z","orderStatus":{"type":6,"value":"已完成"}}},{"id":"5be0ee7f4d","paymentId":"4a409d8a81","price":67000,"deposit":3000,"consigneeName":"鲁琲","consigneePhone":"15110102070","buyerName":"鲁琲","buyerPhone":"15110102070","payType":3,"products":[],"SKUs":[{"ref":"56949b947bf69a6d5908cfa0","productId":"4597374de4","price":67000,"deposit":3000,"productName":"江淮汽车 - 第二代瑞风S3 - 2015款","name":"江淮汽车 - 第二代瑞风S3 - 2015款 - 1.5L 自动（CVT） - 舒适型 - 拉菲红","thumbnail":"/images/thumbnail/6C7D8F66/1460516474888oiw45cdi.jpg?category=6C7D8F66&thumb=true","count":1,"category":"汽车","_id":"572a123014bf2aa42fbde9e0","additions":[],"attributes":[{"_id":"56a20af0a20605f152d32d10","ref":"56a2079bce740ec452f65918","name":"变速箱","value":"1.5L 自动（CVT）"},{"_id":"56a20af0a20605f152d32d0f","ref":"568d66f809747bd064e1ef1a","name":"车型配置","value":"舒适型"},{"_id":"56a20af0a20605f152d32d0e","ref":"568d66f809747bd064e1ef16","name":"颜色","value":"拉菲红"}],"deliverStatus":1}],"duePrice":3000,"deliveryType":1,"payStatus":1,"deliverStatus":1,"RSCInfo":{"RSC":"5649bd6f8eba3c20360b0765","RSCAddress":"河南开封禹王台三里堡街道水中","companyName":"新农江淮化肥鲁排卖身店","RSCPhone":"15110102070"},"isClosed":false,"typeValue":1,"dateCreated":"2016-05-04T15:16:00.361Z","order":{"totalPrice":"67000.00","deposit":"3000.00","dateCreated":"2016-05-04T15:16:00.361Z","orderStatus":{"type":7,"value":"付款待审核"}}},{"id":"f3e5ac755b","paymentId":"dd04216a94","price":99999.12,"deposit":3000.01,"consigneeName":"鲁琲","consigneePhone":"15110102070","buyerName":"鲁琲","buyerPhone":"15110102070","payType":1,"products":[],"SKUs":[{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":1,"category":"化肥","_id":"572a0effe45816682ff02055","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"572a0effe45816682ff02056"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":4,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateRSCReceived":"2016-05-05T01:45:40.661Z","dateSet":"2016-05-05T01:45:40.661Z"},{"ref":"56a20ce8a20605f152d32e1e","productId":"4597374de4","price":99999,"deposit":3000,"productName":"江淮汽车 - 第二代瑞风S3 - 2015款","name":"江淮汽车 - 第二代瑞风S3 - 2015款 - 2.0T 自动（6DCT） - 豪华智能型 - 拉菲红","thumbnail":"/images/thumbnail/6C7D8F66/1460516474888oiw45cdi.jpg?category=6C7D8F66&thumb=true","count":1,"category":"汽车","_id":"572a0effe45816682ff02054","additions":[],"attributes":[{"name":"变速箱","value":"2.0T 自动（6DCT）","ref":"56a2079bce740ec452f65921","_id":"56a20ce8a20605f152d32e24"},{"name":"车型配置","value":"豪华智能型","ref":"568e5122488f103b435c2c0f","_id":"56a20ce8a20605f152d32e23"},{"ref":"568d66f809747bd064e1ef16","name":"颜色","value":"拉菲红","_id":"56a20ce8a20605f152d32e22"}],"deliverStatus":5,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateDelivered":"2016-05-05T05:24:21.800Z","dateSet":"2016-05-05T05:47:28.959Z","dateConfirmed":"2016-05-05T05:47:28.959Z"}],"duePrice":0,"deliveryType":1,"payStatus":2,"deliverStatus":3,"RSCInfo":{"RSC":"5649bd6f8eba3c20360b0779","RSCAddress":"河南商丘梁园张阁镇蔬果超市南侧50米（张阁镇公交车站下来脸朝东）","companyName":"佳美农资啥都卖旗舰店","RSCPhone":"15201532358"},"isClosed":false,"typeValue":3,"dateCreated":"2016-05-04T15:02:23.236Z","datePaid":"2016-05-05T05:24:05.232Z","datePendingDeliver":"2016-05-05T05:24:05.232Z","order":{"totalPrice":"99999.12","deposit":"3000.01","dateCreated":"2016-05-04T15:02:23.236Z","orderStatus":{"type":5,"value":"待自提"}}},{"id":"3addc5ef76","paymentId":"75ebf79540","price":0.12,"deposit":0.01,"consigneeName":"Qqqqqqqqqqqqq","consigneePhone":"18790674259","buyerName":"Qqqqqqqqqqqqq","buyerPhone":"18790674259","payType":1,"products":[],"SKUs":[{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":1,"category":"化肥","_id":"5729ca6471ef5c481186d459","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"5729ca6471ef5c481186d45a"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":4,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateRSCReceived":"2016-05-05T06:13:13.189Z","dateSet":"2016-05-05T06:13:13.189Z","dateDelivered":"2016-05-05T05:16:05.395Z"}],"duePrice":0,"deliveryType":1,"payStatus":2,"deliverStatus":4,"RSCInfo":{"RSC":"5649bd6f8eba3c20360b0765","RSCAddress":"河南开封禹王台三里堡街道水中","companyName":"新农江淮化肥鲁排卖身店","RSCPhone":"15110102070"},"isClosed":false,"typeValue":3,"dateCreated":"2016-05-04T10:09:40.169Z","datePaid":"2016-05-04T10:10:21.579Z","datePendingDeliver":"2016-05-05T06:13:13.190Z","order":{"totalPrice":"0.12","deposit":"0.01","dateCreated":"2016-05-04T10:09:40.169Z","orderStatus":{"type":5,"value":"待自提"}}},{"id":"acff256625","paymentId":"17b9cd557f","price":0.12,"deposit":0.01,"consigneeAddress":"河南郑州中原秦岭路街道痛苦哦哦考虑咯哈咯素质和肌肉照公婆","consigneeName":"🐶","consigneePhone":"15846464646","buyerName":"Qqqqqqqqqqqqq","buyerPhone":"18790674259","payType":1,"products":[],"SKUs":[{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":1,"category":"化肥","_id":"5729c893b8ca2d7506ea7899","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"5729c893b8ca2d7506ea789a"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":4,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateConfirmed":"2016-05-04T10:07:35.319Z","dateSet":"2016-05-05T05:45:19.304Z","dateDelivered":"2016-05-05T05:44:54.007Z","dateRSCReceived":"2016-05-05T05:45:19.304Z"}],"duePrice":0,"deliveryType":2,"payStatus":2,"deliverStatus":4,"isClosed":false,"typeValue":2,"dateCreated":"2016-05-04T10:01:55.585Z","datePaid":"2016-05-05T05:45:07.048Z","datePendingDeliver":"2016-05-05T05:44:54.007Z","order":{"totalPrice":"0.12","deposit":"0.01","dateCreated":"2016-05-04T10:01:55.585Z","orderStatus":{"type":3,"value":"待发货"}}},{"id":"e95e11dea1","paymentId":"39e742e83f","price":0.6,"deposit":0.05,"consigneeName":"哈哈","consigneePhone":"18211101020","buyerName":"哈哈","buyerPhone":"18211101020","payType":1,"products":[],"SKUs":[{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":5,"category":"化肥","_id":"5729b9cc02620a5b176c2ab3","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"5729b9cc02620a5b176c2ab4"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":4,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateRSCReceived":"2016-05-04T09:04:00.134Z","dateSet":"2016-05-04T09:04:00.134Z"}],"duePrice":0,"deliveryType":1,"payStatus":2,"deliverStatus":4,"RSCInfo":{"RSC":"5649bd6f8eba3c20360b0765","RSCAddress":"河南开封禹王台三里堡街道水中","companyName":"新农江淮化肥鲁排卖身店","RSCPhone":"15110102070"},"isClosed":false,"typeValue":3,"dateCreated":"2016-05-04T08:58:52.708Z","datePaid":"2016-05-04T09:04:51.738Z","datePendingDeliver":"2016-05-04T09:04:51.738Z","order":{"totalPrice":"0.60","deposit":"0.05","dateCreated":"2016-05-04T08:58:52.708Z","orderStatus":{"type":5,"value":"待自提"}}},{"id":"9045e88f2f","paymentId":"5ba586e467","price":974000,"deposit":6300,"consigneeName":"陈奕迅","consigneePhone":"13512721874","buyerName":"凯凯王","buyerPhone":"13512721874","payType":3,"products":[],"SKUs":[{"ref":"56eba63b2420ca6442c21285","productId":"b4d0d08a89","price":280000,"deposit":100,"productName":"江淮 - 瑞风S5 - 没有市场价没有描述没有商品详情","name":"江淮 - 瑞风S5 - 2.0T 自动（6DCT） - 豪华智能型 - 玫瑰红","thumbnail":"/images/thumbnail/6C7D8F66/1458284071559h3vunmi.jpg?category=6C7D8F66&thumb=true","count":3,"category":"汽车","_id":"5729af8302620a5b176c2a9b","additions":[],"attributes":[{"_id":"56eba63b2420ca6442c21288","value":"2.0T 自动（6DCT）","name":"变速箱","ref":"56a2079bce740ec452f65921"},{"_id":"56eba63b2420ca6442c21287","value":"豪华智能型","name":"车型配置","ref":"568e5122488f103b435c2c0f"},{"name":"颜色","value":"玫瑰红","ref":"56a2079bce740ec452f65913","_id":"5729af8302620a5b176c2a9c"}],"deliverStatus":4,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateRSCReceived":"2016-05-04T08:21:18.209Z","dateSet":"2016-05-04T08:21:18.209Z"},{"ref":"56949b947bf69a6d5908cfa0","productId":"4597374de4","price":67000,"deposit":3000,"productName":"江淮汽车 - 第二代瑞风S3 - 2015款","name":"江淮汽车 - 第二代瑞风S3 - 2015款 - 1.5L 自动（CVT） - 舒适型 - 拉菲红","thumbnail":"/images/thumbnail/6C7D8F66/1460516474888oiw45cdi.jpg?category=6C7D8F66&thumb=true","count":2,"category":"汽车","_id":"5729af8302620a5b176c2a9a","additions":[],"attributes":[{"_id":"56a20af0a20605f152d32d10","ref":"56a2079bce740ec452f65918","name":"变速箱","value":"1.5L 自动（CVT）"},{"_id":"56a20af0a20605f152d32d0f","ref":"568d66f809747bd064e1ef1a","name":"车型配置","value":"舒适型"},{"_id":"56a20af0a20605f152d32d0e","ref":"568d66f809747bd064e1ef16","name":"颜色","value":"拉菲红"}],"deliverStatus":4,"backendUser":"56546caf4ac2dff43638e128","backendUserAccount":"admin","dateRSCReceived":"2016-05-04T08:25:04.672Z","dateSet":"2016-05-04T08:25:04.672Z"}],"duePrice":0,"deliveryType":1,"payStatus":2,"deliverStatus":4,"RSCInfo":{"RSC":"5649bd6f8eba3c20360b0779","RSCAddress":"河南商丘梁园张阁镇蔬果超市南侧50米（张阁镇公交车站下来脸朝东）","companyName":"佳美农资啥都卖旗舰店","RSCPhone":"15201532358"},"isClosed":false,"typeValue":3,"dateCreated":"2016-05-04T08:14:59.396Z","datePaid":"2016-05-04T08:20:48.152Z","datePendingDeliver":"2016-05-04T08:21:18.209Z","order":{"totalPrice":"974000.00","deposit":"6300.00","dateCreated":"2016-05-04T08:14:59.396Z","orderStatus":{"type":5,"value":"待自提"}}},{"id":"9b8576f3b9","paymentId":"5efd2c8e13","price":0.12,"deposit":0.01,"consigneeAddress":"河南洛阳瀍河回族五股路街道听您婆婆额QQ你修路自信 ins 魔术贵哦婆婆会咯过痛头痛哭童年记录我哦结局","consigneeName":"哈比","consigneePhone":"18855552222","buyerName":"Qqqqqqqqqqqqq","buyerPhone":"18790674259","payType":3,"products":[],"SKUs":[{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":1,"category":"化肥","_id":"572981f2b185bd6e29e69a33","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"572981f2b185bd6e29e69a34"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":1}],"duePrice":0.01,"deliveryType":2,"payStatus":1,"deliverStatus":1,"isClosed":false,"typeValue":1,"dateCreated":"2016-05-04T05:00:34.280Z","order":{"totalPrice":"0.12","deposit":"0.01","dateCreated":"2016-05-04T05:00:34.280Z","orderStatus":{"type":7,"value":"付款待审核"}}}]
     * pages : 204
     * page : 1
     */

    public DatasBean datas;

    public static class DatasBean {
        public int count;
        public int pages;
        public int page;
        /**
         * id : 2169dad83f
         * paymentId : bd0040f118
         * price : 0.12
         * deposit : 0.01
         * consigneeAddress : 河南郑州中原林山寨街道测试地址1
         * consigneeName : 靳美佳
         * consigneePhone : 15201532357
         * buyerName : 靳美佳
         * buyerPhone : 15201532357
         * payType : 1
         * products : []
         * SKUs : [{"ref":"56ab29edd492691a0661175d","productId":"8d8ecde7dd","price":0.12,"deposit":0.01,"productName":"牛街聚宝源羊肉代购（分阶段化肥）","name":"牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨","thumbnail":"/images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true","count":1,"category":"化肥","_id":"572addace25b31161c32bae3","additions":[],"attributes":[{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"572addace25b31161c32bae4"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}],"deliverStatus":1}]
         * duePrice : 0.01
         * deliveryType : 2
         * payStatus : 1
         * deliverStatus : 1
         * isClosed : false
         * typeValue : 1
         * dateCreated : 2016-05-05T05:44:12.680Z
         * order : {"totalPrice":"0.12","deposit":"0.01","dateCreated":"2016-05-05T05:44:12.680Z","orderStatus":{"type":1,"value":"待付款"}}
         */

        public List<ItemsBean> items;

        public static class ItemsBean implements Serializable{
            public String id;
            public String paymentId;
            public double price;
            public double deposit;
            public String consigneeAddress;
            public String consigneeName;
            public String consigneePhone;
            public String buyerName;
            public String buyerPhone;
            public int payType;
            public double duePrice;
            public int deliveryType;
            public int payStatus;
            public int deliverStatus;
            public boolean isClosed;
            public int typeValue;
            public String dateCreated;
            /**
             * totalPrice : 0.12
             * deposit : 0.01
             * dateCreated : 2016-05-05T05:44:12.680Z
             * orderStatus : {"type":1,"value":"待付款"}
             */

            public OrderBean order;
            public List<ProductBean> products;

            /**
             * ref : 56ab29edd492691a0661175d
             * productId : 8d8ecde7dd
             * price : 0.12
             * deposit : 0.01
             * productName : 牛街聚宝源羊肉代购（分阶段化肥）
             * name : 牛街聚宝源羊肉代购（分阶段化肥） - 纯羊肉不掺水 - 25kg*40袋 / 吨
             * thumbnail : /images/thumbnail/531680A5/1460008593045bfzb0529.jpg?category=531680A5&thumb=true
             * count : 1
             * category : 化肥
             * _id : 572addace25b31161c32bae3
             * additions : []
             * attributes : [{"name":"养分配比","value":"纯羊肉不掺水","ref":"56ab29cdd492691a06611758","_id":"572addace25b31161c32bae4"},{"_id":"56ab29edd492691a0661175f","value":"25kg*40袋 / 吨","name":"规格","ref":"56a2079bce740ec452f658fc"}]
             * deliverStatus : 1
             */
            public static class ProductBean implements Serializable{
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

            public List<SKUsBean> SKUs;

            public static class OrderBean implements Serializable{
                public String totalPrice;
                public String deposit;
                public String dateCreated;
                public boolean pendingDeliverToRSC;

                /**
                 * type : 1
                 * value : 待付款
                 */

                public OrderStatusBean orderStatus;

                public static class OrderStatusBean implements Serializable{
                    public int type;
                    public String value;
                }
            }

            public static class SKUsBean implements Serializable{
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
                /**
                 * name : 养分配比
                 * value : 纯羊肉不掺水
                 * ref : 56ab29cdd492691a06611758
                 * _id : 572addace25b31161c32bae4
                 */
                public List<Additions> additions; //附加选项
                public List<AttributesBean> attributes;

                public static class AttributesBean implements Serializable{
                    public String name;
                    public String value;
                    public String ref;
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
}
