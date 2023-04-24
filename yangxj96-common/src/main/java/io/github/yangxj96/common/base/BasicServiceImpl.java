package io.github.yangxj96.common.base;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.yangxj96.common.exception.PlaceholderException;

/**
 * RESTFul 接口公用service层
 *
 * @param <M> 子类对应的mapper
 * @param <O> 子类对应的实体
 */
public class BasicServiceImpl<M extends BasicMapper<O>, O extends BasicEntity> extends ServiceImpl<M, O> implements BasicService<O> {

    protected final M bindMapper;

    protected BasicServiceImpl(M bindMapper) {
        this.bindMapper = bindMapper;
    }


    public O create(O datum) {
        if (bindMapper.insert(datum) == 1) {
            return bindMapper.selectById(datum.getId());
        }
        return datum;
    }

    public boolean delete(String id) {
        O datum = this.getById(Long.valueOf(id));
        if (null == datum) {
            throw new PlaceholderException();
        }
        return this.removeById(datum.getId());
    }

    public O modify(O datum) {
        if (null == this.getById(datum.getId())) {
            throw new PlaceholderException();
        }
        if (this.updateById(datum)) {
            return this.getById(datum.getId());
        }
        return datum;
    }

}
