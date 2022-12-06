package io.github.yangxj96.common.base;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * RESTFul 接口公用service层
 *
 * @author 杨新杰
 * @date 2021/9/13 21:20
 */
public interface BasicService<O extends BasicEntity> extends IService<O> {

	O create(O datum);

	boolean delete(String id);

	O modify(O datum);

}
