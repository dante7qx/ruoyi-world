package com.risun.web.controller.tool;

import java.util.Map;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.http.HttpUtil;


/**
 * 代码示例
 * 
 * @author dante
 */
@RestController
@RequestMapping("/tool/codeexample")
@CrossOrigin(origins = "http://http://36.137.3.207:9000/", maxAge = 3600)
public class CodeExampleController extends BaseController {
	
	private static final Map<String, Object> CACHE_MAP = Maps.newConcurrentMap();
	
	/**
	 * 模拟查询数据，获取详情信息
	 * 
	 * @param key
	 * @return
	 */
	@GetMapping("/key/{key}")
	public AjaxResult loadData(@PathVariable String key) {
		return AjaxResult.success(CACHE_MAP.get(key));
	}

	/**
	 * 模拟表单提交
	 * 
	 * @return
	 */
	@PostMapping("/submit")
	public AjaxResult submit(@RequestBody Map<String, Object> formData) {
		CACHE_MAP.put(formData.get("key").toString(), formData);
		return AjaxResult.success();
	}
	
	/**
	 *  清空数据
	 */
	@PostMapping("/clear/{key}")
	public AjaxResult clear(@PathVariable String key) {
		return AjaxResult.success(CACHE_MAP.remove(key));
	}
	
	/**
	 * 根据url获取数据
	 * 
	 * @param url
	 * @return
	 */
	@PostMapping("/fetch")
	public AjaxResult fetchDataByUrl(@RequestBody Map<String, Object> params) {
		String url = (String) params.get("url");
		String res = HttpUtil.get(url);
		return AjaxResult.success(JSON.parse(res));
	}
}
