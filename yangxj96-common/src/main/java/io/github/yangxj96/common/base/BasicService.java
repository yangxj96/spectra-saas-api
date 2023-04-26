package io.github.yangxj96.common.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * RESTFul 接口公用service层
 *
 * @param <O> 子类对应的实体
 */
public interface BasicService<O extends BasicEntity> extends IService<O> {

    O create(O datum);

    boolean delete(String id);

    O modify(O datum);

}
