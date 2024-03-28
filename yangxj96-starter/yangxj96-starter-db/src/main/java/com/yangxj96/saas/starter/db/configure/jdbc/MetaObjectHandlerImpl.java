package com.yangxj96.saas.starter.db.configure.jdbc;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 元对象字段填充控制器
 */
@Slf4j
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    /**
     * 创建人
     */
    private static final String CREATED_USER = "createdUser";
    /**
     * 创建时间
     */
    private static final String CREATED_TIME = "createdTime";
    /**
     * 更新人
     */
    private static final String UPDATED_USER = "updatedUser";
    /**
     * 更新时间
     */
    private static final String UPDATED_TIME = "updatedTime";


    @Override
    public void insertFill(MetaObject metaObject) {
        log.atDebug().log("[MyBatisPlus] 新增填充字段");
        var id = 0L;
        try {
            StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            log.atError().log("获取ID出错,默认ID为0");
        }

        if (getFieldValByName(CREATED_USER, metaObject) == null) {
            setFieldValByName(CREATED_USER, id, metaObject);
        }
        if (getFieldValByName(CREATED_TIME, metaObject) == null) {
            setFieldValByName(CREATED_TIME, LocalDateTime.now(), metaObject);
        }
        if (getFieldValByName(UPDATED_USER, metaObject) == null) {
            setFieldValByName(UPDATED_USER, id, metaObject);
        }
        if (getFieldValByName(UPDATED_TIME, metaObject) == null) {
            setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.atDebug().log("[MyBatisPlus] 修改填充字段");
        var id = 0L;
        try {
            StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            log.atError().log("获取ID出错,默认ID为0");
        }
        setFieldValByName(UPDATED_USER, id, metaObject);
        setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
    }

}
