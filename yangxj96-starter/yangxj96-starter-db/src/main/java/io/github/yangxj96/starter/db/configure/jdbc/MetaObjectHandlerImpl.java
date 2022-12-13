package io.github.yangxj96.starter.db.configure.jdbc;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;


/**
 * 元对象字段填充控制器
 *
 * @author yangxj96
 */
@Slf4j
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    // @formatter:off
	/** 创建人 **/
	private static final String CREATED_BY   = "createdBy";
	/** 创建时间 **/
	private static final String CREATED_TIME = "createdTime";
	/** 更新人 **/
	private static final String UPDATED_BY   = "updatedBy";
	/** 更新时间 **/
	private static final String UPDATED_TIME = "updatedTime";
	// @formatter:on

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("[自动配置-MyBatisPlus] insert 填充字段开始");
		this.setFieldValByName(CREATED_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(CREATED_BY, 0L, metaObject);
		this.setFieldValByName(UPDATED_BY, 0L, metaObject);
		this.setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("[自动配置-MyBatisPlus] update 填充字段开始");
		this.setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
    }

}
