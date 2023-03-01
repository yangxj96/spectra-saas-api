package io.github.yangxj96.server.system.controller;

import io.github.yangxj96.bean.system.Configure;
import io.github.yangxj96.common.base.BasicController;
import io.github.yangxj96.server.system.service.ConfigureService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置相关接口
 *
 * @author yangxj96
 * @version 1.0.0
 * @date 2023-2-22 14:19:43
 */
@RestController
@RequestMapping(value = "/configure", headers = "remote=true")
public class ConfigureController extends BasicController<Configure, ConfigureService> {

    protected ConfigureController(ConfigureService bindService) {
        super(bindService);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SYSADMIN')")
    public Configure create(@Validated Configure obj) {
        return super.create(obj);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYSADMIN')")
    public void delete(@NotBlank(message = "需要删除的资源id不能为空") @PathVariable String id) {
        super.delete(id);
    }

    @Override
    @PutMapping
    @PreAuthorize("hasRole('ROLE_SYSADMIN')")
    public Configure modify(@Validated Configure obj) {
        return super.modify(obj);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SYSADMIN')")
    public List<Configure> select(){
        return bindService.select();
    }

}
