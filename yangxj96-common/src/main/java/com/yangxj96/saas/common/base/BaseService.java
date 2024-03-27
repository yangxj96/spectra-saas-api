package com.yangxj96.saas.common.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * RESTFul 接口公用service层
 *
 * @param <O> 子类对应的实体
 *            </O>
 */
public interface BaseService<O extends BaseEntity> extends IService<O> {

    O create(O datum);

    Boolean delete(String id);

    O modify(O datum);

}
