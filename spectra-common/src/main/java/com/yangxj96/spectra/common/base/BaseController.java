package com.yangxj96.spectra.common.base;

import com.yangxj96.spectra.common.respond.R;
import org.springframework.validation.annotation.Validated;

import lombok.extern.slf4j.Slf4j;

/**
 * RESTFul 接口公用Controller层
 *
 * @param <O> 入参对象,可以为数据库po对象或者是自定义的dto对象
 * @param <S> 相关的service层,需要继承BasicService
 *            </S></O>
 */
@Slf4j
@Validated
public abstract class BaseController<O extends BaseEntity, S extends BaseService<O>> {

    protected S bindService;

    protected BaseController(S bindService) {
        this.bindService = bindService;
    }

    /**
     * 基础创建数据接口
     *
     * @param params 数据实体
     * @return 创建后的数据实体
     */
    public O create(O params) {
        log.atDebug().log("数据新增,数据实体:{}", params);
        return bindService.create(params);
    }

    /**
     * 基础删除接口
     *
     * @param id 数据id
     */
    public R<Object> delete(String id) {
        log.atDebug().log("数据删除,数据ID:{}", id);
        if (bindService.delete(Long.parseLong(id))) {
            return R.success();
        } else {
            return R.failure();
        }
    }

    /**
     * 基础修改接口
     *
     * @param params 数据实体
     * @return 修改成功返回修改后的数据, 否则返回null
     */
    public O modify(O params) {
        log.atDebug().log("数据修改,数据实体:{}", params);
        return bindService.modify(params);
    }

}