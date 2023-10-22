package com.spirit;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.spirit.common.exception.job.TaskException;
import com.spirit.quartz.service.ISysJobService;
import com.spirit.system.service.ISysConfigService;
import com.spirit.system.service.ISysDictTypeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 项目启动时，初始化操作
 * 
 * @author dante
 *
 */
@Slf4j
@Component
public class SpiritRuntimeInitConfig implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    @Autowired
    private ISysJobService sysJobService;

    /**
     * 项目启动时，初始化操作
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("初始化参数到缓存 ...");
        sysConfigService.loadingConfigCache();
        log.info("初始化字典到缓存 ...");
        sysDictTypeService.loadingDictCache();
        log.info("初始化系统定时任务到缓存 ...");
        try {
			sysJobService.loadingSysJobCache();
		} catch (SchedulerException | TaskException e) {
			log.error("初始化系统定时任务到缓存错误", e);
		}
    }

}
