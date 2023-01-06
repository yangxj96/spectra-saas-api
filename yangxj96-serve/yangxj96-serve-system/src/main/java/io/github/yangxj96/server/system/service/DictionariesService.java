/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.system.service;

import io.github.yangxj96.bean.system.Dictionaries;
import io.github.yangxj96.common.base.BasicService;

import java.util.List;

/**
 * 数据字典service层
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
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
