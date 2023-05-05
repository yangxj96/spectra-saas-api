package io.github.yangxj96.server.system.controller;

import cn.hutool.core.util.IdUtil;
import io.github.yangxj96.bean.system.Dictionaries;
import io.github.yangxj96.common.base.BasicController;
import io.github.yangxj96.common.base.ValidationGroups;
import io.github.yangxj96.server.system.service.DictionariesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * 字典相关操作
 */
@RestController
@RequestMapping("/dictionaries")
public class DictionariesController extends BasicController<Dictionaries, DictionariesService> {

    protected DictionariesController(DictionariesService bindService) {
        super(bindService);
    }

    /**
     * 新增字典项
     *
     * @param obj 数据实体
     * @return 新增后的字典项
     */
    @PreAuthorize("hasAuthority('DICT_INSERT') or hasRole('ROLE_ADMIN')")
    @PostMapping
    @Override
    public Dictionaries create(@Validated(ValidationGroups.Insert.class) Dictionaries obj) {
        obj.setCode(IdUtil.fastSimpleUUID().toUpperCase(Locale.CHINA));
        return bindService.create(obj);
    }


    @PreAuthorize("hasAuthority('DICT_DELETE') or hasRole('ROLE_SYSADMIN')")
    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable String id) {
        super.delete(id);
    }

    @PreAuthorize("hasAuthority('DICT_UPDATE') or hasRole('ROLE_SYSADMIN')")
    @PutMapping
    @Override
    public Dictionaries modify(@Validated(ValidationGroups.Update.class) Dictionaries obj) {
        return super.modify(obj);
    }


     @PreAuthorize("hasAuthority('DICT_SELECT') or hasRole('ROLE_SYSADMIN')")
    @GetMapping
    public List<Dictionaries> select(Dictionaries params) {
        return bindService.select(params);
    }

}
