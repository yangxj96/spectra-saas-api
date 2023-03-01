package io.github.yangxj96.server.system.service;

import io.github.yangxj96.bean.system.Configure;
import io.github.yangxj96.common.base.BasicService;

import java.util.List;


public interface ConfigureService extends BasicService<Configure> {


    /**
     * 查询配置
     *
     * @return 符合条件的配置
     */
    List<Configure> select();

}
