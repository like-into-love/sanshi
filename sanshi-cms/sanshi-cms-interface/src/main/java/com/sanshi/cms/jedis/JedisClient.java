package com.sanshi.cms.jedis;

public interface JedisClient {
	/**
	 * 存值
	 * 
	 * @param key
	 * @param value
	 * @return 如果成功返回OK
	 */
	String set(String key, String value);

	/**
	 * 取值
	 * 
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 检查给定 key 是否存在
	 * 
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 用于设置 key 的过期时间。key 过期后将不再可用
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	Long expire(String key, int seconds);

	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间
	 * 
	 * @param key
	 * @return当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key
	 *          的剩余生存时间
	 */
	Long ttl(String key);

	/**
	 * Incr 命令将 key 中储存的数字值增一
	 * 
	 * @param key
	 *            如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作
	 * @return
	 */
	Long incr(String key);

	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。如果域 field
	 * 已经存在于哈希表中，旧值将被覆盖
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	Long hset(String key, String field, String value);

	/**
	 * 
	 * @param key
	 * @param field
	 * @return Hget 命令用于返回哈希表中指定字段的值。
	 */
	String hget(String key, String field);

	/**
	 * Hdel 命令用于删除哈希表 key 中的一个或多个指定字段，不存在的字段将被忽略
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	Long hdel(String key, String... field);
}
