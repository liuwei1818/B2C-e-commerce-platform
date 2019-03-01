package com.taotao.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {
    
    @Test
    public void testSorlCloudAdDocument() throws Exception {
        //创建cloudSolrServer对象,构造方法中指定zookeeper地址列表
        CloudSolrServer cloudSolrServer = new CloudSolrServer(
                "192.168.25.131:2181,192.168.25.131:2182,192.168.25.131:2183");
        //需要设置默认的collection
        cloudSolrServer.setDefaultCollection("collection2");
        //创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.addField("id", "test001");
        document.addField("item_title", "测试商品名称");
        document.addField("item_price", 100);
        //把文档写入索引库
        cloudSolrServer.add(document);
        //提交
        cloudSolrServer.commit();
    }
}
