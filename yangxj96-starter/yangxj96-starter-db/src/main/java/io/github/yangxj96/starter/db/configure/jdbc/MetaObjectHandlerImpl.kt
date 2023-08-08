package io.github.yangxj96.starter.db.configure.jdbc;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.yangxj96.bean.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;


/**
 * 元对象字段填充控制器
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
        this.setFieldValByName(CREATED_BY, 0L, metaObject);
        this.setFieldValByName(CREATED_TIME, LocalDateTime.now(), metaObject);
        this.setFieldValByName(UPDATED_BY, getCurrentUserId(), metaObject);
        this.setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("[自动配置-MyBatisPlus] update 填充字段开始");
        this.setFieldValByName(UPDATED_BY, getCurrentUserId(), metaObject);
        this.setFieldValByName(UPDATED_TIME, LocalDateTime.now(), metaObject);
    }

    /**
     * 获取当前用户ID
     *
     * @return 当前用户ID, 如果没有获取到则为0
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return ((User) authentication.getPrincipal()).getId();
        }
        return 0L;
    }

}
