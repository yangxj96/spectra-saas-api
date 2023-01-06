/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:39
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangxj96.bean.system.Dictionaries;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.system.mapper.DictionariesMapper;
import io.github.yangxj96.server.system.service.DictionariesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典service层的实现
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
@Service
public class DictionariesServiceImpl extends BasicServiceImpl<DictionariesMapper, Dictionaries> implements DictionariesService {

    protected DictionariesServiceImpl(DictionariesMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public List<Dictionaries> select(Dictionaries params) {
        LambdaQueryWrapper<Dictionaries> wrapper = new LambdaQueryWrapper<>();
        wrapper.setEntity(params);
        return this.list(wrapper);
    }
}
