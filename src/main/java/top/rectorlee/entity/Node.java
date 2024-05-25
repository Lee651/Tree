package top.rectorlee.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: Lee
 * @date: 2024-05-25 16:23:56
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Node implements Serializable {
    /**
     * 当前节点编码
     */
    private String id;

    /**
     * 当前节点行政组名称
     */
    private String name;

    /**
     * 上级节点编码
     */
    private String pId;

    /**
     * 子节点
     */
    private List<Node> childList;
}
