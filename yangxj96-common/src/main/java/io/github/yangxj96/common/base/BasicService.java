/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-01-07 00:08:21
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.common.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * RESTFul 接口公用service层
 *
 * @param <O> 子类对应的实体
 * @author yangxj96
 * @version 1.0
 * @date 2023-01-07 00:14
 */
public interface BasicService<O extends BasicEntity> extends IService<O> {

    O create(O datum);

    boolean delete(String id);

    O modify(O datum);

}
