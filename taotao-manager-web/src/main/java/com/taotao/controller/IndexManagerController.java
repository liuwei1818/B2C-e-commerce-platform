package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;

@Controller
public class IndexManagerController {
    @Autowired
    private SearchItemService searchItemService;
    
    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importIndex() {
        TaotaoResult taotaoResult = searchItemService.importItemToIndex();
        return taotaoResult;
    }
}
