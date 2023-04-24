package io.github.yangxj96.server.system.service;

import io.github.yangxj96.bean.system.Dictionaries;
import io.github.yangxj96.common.base.BasicService;

import java.util.List;

/**
 * 数据字典service层
 */
public interface DictionariesService extends BasicService<Dictionaries> {


    /**
     * 查询字典
     *
     * @param params 查询条件
     * @return 符合条件的数据
     */
    List<Dictionaries> select(Dictionaries params);
}

