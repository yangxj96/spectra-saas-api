package com.yangxj96.spectra.starter.common.autoconfigure;

import com.yangxj96.spectra.starter.common.endpoint.DependEndpoint;
import org.springframework.context.annotation.Import;


/**
 * 端点自动配置
 */
@Import(DependEndpoint.class)
public class EndpointAutoConfiguration{

}