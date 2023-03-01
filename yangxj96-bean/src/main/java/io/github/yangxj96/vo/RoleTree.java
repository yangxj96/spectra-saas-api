/*****************************
 * Copyright (c) 2021 - 2023
 * author:yangxj96
 * email :yangxj96@gmail.com
 * date  :2023-03-02 00:12:38
 * Copyright (c) 2021 - 2023
 ****************************/

package io.github.yangxj96.vo;

import io.github.yangxj96.bean.user.Role;

import java.util.List;

/**
 * 用户角色树结构vo
 *
 * @author yangxj96
 * @version 1.0
 * @date 2023/3/2 0:12
 */
public class RoleTree extends Role {


    /** 子节点 **/
    private List<Role> children;

}
