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
 */
@RestController
@RequestMapping(value = "/configure", headers = "remote=true")
public class ConfigureController extends BasicController<Configure, ConfigureService> {

    protected ConfigureController(ConfigureService bindService) {
        super(bindService);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('SYS_CONFIGURE_INSERT')")
    public Configure create(@Validated Configure obj) {
        return super.create(obj);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SYS_CONFIGURE_DELETE')")
    public void delete(@NotBlank(message = "需要删除的资源id不能为空") @PathVariable String id) {
        super.delete(id);
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('SYS_CONFIGURE_MODIFY')")
    public Configure modify(@Validated Configure obj) {
        return super.modify(obj);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SYS_CONFIGURE_SELECT')")
    public List<Configure> select(){
        return bindService.select();
    }

}
