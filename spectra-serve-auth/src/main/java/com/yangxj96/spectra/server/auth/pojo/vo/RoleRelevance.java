package com.yangxj96.spectra.server.auth.pojo.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 角色关联权限
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRelevance {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 权限ID列表
     */
    @NotNull(message = "权限ID列表不能为空")
    private List<Long> authorityIds;

}