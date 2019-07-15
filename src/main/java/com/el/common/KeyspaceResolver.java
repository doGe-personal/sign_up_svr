package com.el.common;

/**
 * 用于解决键值名字空间问题（一般用于 NoSQL 的键值）
 *
 * @author danfeng.diao
 * @since 18/
 */
public interface KeyspaceResolver {

    /**
     * @return 会话数据空间
     */
    String sessionSpace();

    /**
     * @return 临时数据空间（放置如短时间的token等）
     */
    String nonceSpace();

    /**
     * @return 缓存数据空间
     */
    String cacheSpace();

    /**
     * @return 消息队列空间
     */
    String queueSpace();

}
