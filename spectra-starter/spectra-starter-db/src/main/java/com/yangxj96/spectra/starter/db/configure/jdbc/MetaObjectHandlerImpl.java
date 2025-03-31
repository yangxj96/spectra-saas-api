package com.yangxj96.spectra.starter.db.configure.jdbc;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.jetbrains.annotations.NotNull;

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


    /**
     * 插入填充
     *
     * @param metaObject 元数据对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.atDebug().log("[MyBatisPlus] 新增填充字段");
        if (getFieldValByName(CREATED_USER, metaObject) == null) {
            setFieldValByName(CREATED_USER, getCurrentUserId(), metaObject);
        }
        if (getFieldValByName(CREATED_TIME, metaObject) == null) {
            setFieldValByName(CREATED_TIME, LocalDateTime.now(), metaObject);
        }
        if (getFieldValByName(UPDATED_USER, metaObject) == null) {
            setFieldValByName(UPDATED_USER, getCurrentUserId(), metaObject);
        }
        if (getFieldValByName(UPDATED_TIME, metaObject) == null) {
            setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 更新填充
     *
     * @param metaObject 元数据对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.atDebug().log("[MyBatisPlus] 修改填充字段");
        setFieldValByName(UPDATED_USER, getCurrentUserId(), metaObject);
        setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
    }

    /**
     * 获取当前用户ID,如果获取失败,则返回ID为0
     *
     * @return 用户ID
     */
    private @NotNull Long getCurrentUserId() {
        try {
            return StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            log.atError().log("获取ID出错,默认ID为0");
            return 0L;
        }
    }

}
