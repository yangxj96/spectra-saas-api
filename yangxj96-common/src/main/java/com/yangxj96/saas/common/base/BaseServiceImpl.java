package com.yangxj96.saas.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangxj96.saas.common.exception.PlaceholderException;
import org.springframework.transaction.annotation.Transactional;

/**
 * RESTFul 接口公用service层
 *
 * @param <M> 子类对应的mapper
 * @param <O> 子类对应的实体
 *            </O></M>
 */
public class BaseServiceImpl<M extends BaseMapper<O>, O extends BaseEntity>
        extends ServiceImpl<M, O> implements BaseService<O> {

    protected M bindMapper;

    protected BaseServiceImpl(M bindMapper) {
        this.bindMapper = bindMapper;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public O create(O datum) {
        if (bindMapper.insert(datum) == 1) {
            return bindMapper.selectById(datum.getId());
        } else {
            return datum;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean delete(Long id) {
        var datum = getById(id);
        if (datum == null) {
            throw new PlaceholderException();
        }
        return this.removeById(datum.getId());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public O modify(O datum) {
        if (null == getById(datum.getId())) {
            throw new PlaceholderException();
        }
        if (updateById(datum)) {
            return getById(datum.getId());
        } else {
            return datum;
        }
    }

}
