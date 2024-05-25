package top.rectorlee.mapper;

import top.rectorlee.entity.Node;

import java.util.List;

public interface NodeMapper {
    List<Node> selectAllNode();

    List<Node> getAllProvince();

    List<Node> getSubByPid(String pId, String level);

    Node buildTree(String id);
}
