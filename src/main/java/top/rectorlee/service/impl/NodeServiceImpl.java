package top.rectorlee.service.impl;

import cn.hutool.core.lang.tree.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.rectorlee.entity.Node;
import top.rectorlee.mapper.NodeMapper;
import top.rectorlee.service.NodeService;
import top.rectorlee.utils.RestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Lee
 * @date: 2024-05-25 16:31:00
 * @version: 1.0
 */
@Service
@Slf4j(topic = "nodeService")
public class NodeServiceImpl implements NodeService {
    @Autowired
    private NodeMapper mapper;

    @Override
    public RestResult<List<Tree<String>>> fullTree() {
        long start = System.currentTimeMillis();
        List<Tree<String>> treeList = new ArrayList<>();

        List<Node> nodeList = mapper.selectAllNode();
        if (CollectionUtils.isNotEmpty(nodeList)) {
            List<TreeNode<String>> list = nodeList.stream()
                    .map(NodeServiceImpl::getTreeNode)
                    .collect(Collectors.toList());
            // 利用hutool工具类构建树结构
            treeList = TreeUtil.build(list, "0");
        }

        long end = System.currentTimeMillis();
        log.info("耗时为: {}", (end - start));
        return RestResult.success(treeList);
    }

    private static TreeNode<String> getTreeNode(Node node) {
        TreeNode<String> treeNode = new TreeNode<>();
        treeNode.setId(node.getId());
        treeNode.setParentId(node.getPId());
        treeNode.setName(node.getName());
        return treeNode;
    }

    @Override
    public RestResult<List<Node>> getAllProvince() {
        List<Node> list = mapper.getAllProvince();
        return RestResult.success(list);
    }

    @Override
    public RestResult<List<Node>> getSubByPid(String pId, String level) {
        log.info("上级id为: {}, 属于第{}层级", pId, level);
        List<Node> subList = mapper.getSubByPid(pId, level);
        return RestResult.success(subList);
    }

    @Override
    public RestResult<List<Node>> buildTree(List<String> idList) {
        long start = System.currentTimeMillis();

        log.info("id集合为: {}", idList);
        // 每一个参数对应的节点、父节点, 一直到省级节点集合
        List<Node> list = getEveryLeveNode(new CopyOnWriteArrayList<>(), idList);
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(l -> {
                list.parallelStream().forEach(k -> {
                    if (l.getId().equals(k.getPId())) {
                        if (CollectionUtils.isEmpty(l.getChildList())) {
                            l.setChildList(new ArrayList<>());
                        }
                        l.getChildList().add(k);
                    }
                });
            });

            list.removeIf(l -> !"0".equals(l.getPId()));
        }
        log.info("list: {}", list);
        long end = System.currentTimeMillis();
        log.info("耗时为: {}", (end - start));
        return RestResult.success(list);
    }

    @Override
    public RestResult<List<Tree<String>>> buildTree1(List<String> idList) {
        long start = System.currentTimeMillis();

        log.info("id集合为: {}", idList);
        // 每一个参数对应的节点、父节点, 一直到省级节点集合
        List<Node> list = getEveryLeveNode(new CopyOnWriteArrayList<>(), idList);
        if (CollectionUtils.isNotEmpty(list)) {
            List<TreeNode<String>> treeNodeList = list.parallelStream()
                    .map(NodeServiceImpl::getTreeNode)
                    .collect(Collectors.toList());
            // 利用hutool工具类构建树结构
            List<Tree<String>> treeList = TreeUtil.build(treeNodeList, "0");

            long end = System.currentTimeMillis();
            log.info("耗时为: {}", (end - start));
            return RestResult.success(treeList);
        }

        return RestResult.success(null);
    }

    /**
     * 获取每一层的节点信息, 到省级节点为止
     */
    private List<Node> getEveryLeveNode(List<Node> list, List<String> idList) {
        List<Node> nodeList = new CopyOnWriteArrayList<>();
        idList.parallelStream().forEach(i -> {
            nodeList.add(mapper.buildTree(i));
        });
        if (CollectionUtils.isNotEmpty(nodeList)) {
            // 每次添加到list前先去重, 防止有重复的层级结构
            List<Node> nodes = nodeList.parallelStream().distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(nodes)) {
                list.addAll(nodes);

                List<String> pIdList = nodes.parallelStream()
                        .filter(n -> !"0".equals(n.getPId()))
                        .map(Node::getPId).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(pIdList)) {
                    getEveryLeveNode(list, pIdList);
                }
            }
        }
        return list;
    }
}
