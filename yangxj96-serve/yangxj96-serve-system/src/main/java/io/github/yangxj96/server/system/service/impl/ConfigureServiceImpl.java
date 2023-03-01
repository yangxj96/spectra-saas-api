package io.github.yangxj96.server.system.service.impl;

import io.github.yangxj96.bean.system.Configure;
import io.github.yangxj96.common.base.BasicServiceImpl;
import io.github.yangxj96.server.system.mapper.ConfigureMapper;
import io.github.yangxj96.server.system.service.ConfigureService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConfigureServiceImpl extends BasicServiceImpl<ConfigureMapper, Configure> implements ConfigureService {

    protected ConfigureServiceImpl(ConfigureMapper bindMapper) {
        super(bindMapper);
    }

    @Override
    public List<Configure> select() {
        return this.list();
    }
}
