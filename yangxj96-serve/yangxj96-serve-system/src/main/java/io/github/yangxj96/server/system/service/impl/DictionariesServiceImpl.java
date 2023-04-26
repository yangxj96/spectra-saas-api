package io.github.yangxj96.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yangxj96.bean.system.Dictionaries;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.system.mapper.DictionariesMapper;
import io.github.yangxj96.server.system.service.DictionariesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典service层的实现
 */
@Service
public class DictionariesServiceImpl extends BasicServiceImpl<DictionariesMapper, Dictionaries> implements DictionariesService {

    protected DictionariesServiceImpl(DictionariesMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Dictionaries> select(Dictionaries params) {
        LambdaQueryWrapper<Dictionaries> wrapper = new LambdaQueryWrapper<>();
        wrapper.setEntity(params);
        return this.list(wrapper);
    }
}

