package io.github.yangxj96.common.base;


import io.github.yangxj96.common.respond.R;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * RESTFul 接口公用Controller层
 *
 * @param <O> 入参对象,可以为数据库po对象或者是自定义的dto对象
 * @param <S> 相关的service层,需要继承BasicService
 */
@Validated
public abstract class BasicController<O extends BasicEntity, S extends BasicService<O>> {

    protected final S bindService;

    protected BasicController(S bindService) {
        this.bindService = bindService;
    }

    /**
     * 基础创建数据接口
     *
     * @param obj 数据实体
     * @return 创建后的数据实体
     */
    @PostMapping
    public O create(@Validated O obj) {
        return bindService.create(obj);
    }

    /**
     * 基础删除接口
     *
     * @param id 数据id
     */
    @DeleteMapping("/{id}")
    public void delete(@NotBlank(message = "需要删除的资源id不能为空") @PathVariable String id) {
        if (bindService.delete(id)) {
            R.success();
        } else {
            R.failure();
        }
    }

    /**
     * 基础修改接口
     *
     * @param obj 数据实体
     * @return 修改成功返回修改后的数据, 否则返回null
     */
    @PutMapping
    public O modify(@Validated O obj) {
        return bindService.modify(obj);
    }

}

