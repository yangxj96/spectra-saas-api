package io.github.yangxj96.starter.remote.fallback;

import feign.Logger;
import feign.Retryer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "it100000.remote")
public class RemoteProperties {

	/** 连接超时时间 **/
	private Integer connectTimeOut = 1000;

	/** 读取超时时间 **/
	private Integer readTimeOut = 1000;

	/** 写出超时时间 **/
	private Integer writeTimeout = 1000;

	/** feign日志输出等级 **/
	private Logger.Level level = Logger.Level.FULL;

	/** feign 重试配置 **/
	private feign.Retryer retryer = new Retryer.Default();



}
