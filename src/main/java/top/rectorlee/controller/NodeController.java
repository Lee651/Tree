package top.rectorlee.controller;

import cn.hutool.core.lang.tree.Tree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.rectorlee.entity.Node;
import top.rectorlee.service.NodeService;
import top.rectorlee.utils.RestResult;

import java.util.List;

/**
 * @description:
 * @author: Lee
 * @date: 2024-05-25 16:31:35
 * @version: 1.0
 */
@RestController
@Slf4j(topic = "nodeController")
@RequestMapping("/tree")
public class NodeController {
    @Autowired
    private NodeService service;

    /**
     * 构建行政树结构
     */
    @RequestMapping("/full")
    public RestResult<List<Tree<String>>> fullTree() {
        return service.fullTree();
    }

    /**
     * 查询所有省、自治区
     */
    @RequestMapping("/province")
    public RestResult<List<Node>> getAllProvince() {
        return service.getAllProvince();
    }

    /**
     * 根据上级id查找下级:
     *     省级: 1
     *     市级: 2
     *     区/县级: 3
     *     镇/街道办: 4
     */
    @RequestMapping("/sub/{pId}/{level}")
    public RestResult<List<Node>> getSubByPid(@PathVariable("pId") String pId, @PathVariable("level") String level) {
        if (Integer.parseInt(level) >= 5) {
            return RestResult.error("层级数不能超过4");
        }
        return service.getSubByPid(pId, level);
    }

    /**
     * 根据指定的行政编码返回树形结构: 双循环构建树结构
     */
    @RequestMapping("/build")
    public RestResult<List<Node>> buildTree(@RequestBody List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return RestResult.success("id集合为空");
        }
        return service.buildTree(idList);
    }

    /**
     * 根据指定的行政编码返回树形结构: 使用hutool工具类构建树结构
     */
    @RequestMapping("/build1")
    public RestResult<List<Tree<String>>> buildTree1(@RequestBody List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return RestResult.success("id集合为空");
        }
        return service.buildTree1(idList);
    }
}
