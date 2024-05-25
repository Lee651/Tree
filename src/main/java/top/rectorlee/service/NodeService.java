package top.rectorlee.service;

import cn.hutool.core.lang.tree.Tree;
import top.rectorlee.entity.Node;
import top.rectorlee.utils.RestResult;

import java.util.List;

public interface NodeService {
    RestResult<List<Tree<String>>> fullTree();

    RestResult<List<Node>> getAllProvince();

    RestResult<List<Node>> getSubByPid(String pId, String level);

    RestResult<List<Node>> buildTree(List<String> idList);

    RestResult<List<Tree<String>>> buildTree1(List<String> idList);
}
