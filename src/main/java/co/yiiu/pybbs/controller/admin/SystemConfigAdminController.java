package co.yiiu.pybbs.controller.admin;

import co.yiiu.pybbs.model.SystemConfig;
import co.yiiu.pybbs.service.SystemConfigService;
import co.yiiu.pybbs.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tomoya.
 * Copyright (c) 2018, All Rights Reserved.
 * https://yiiu.co
 */
@Controller
@RequestMapping("/admin/system")
public class SystemConfigAdminController extends BaseAdminController {

  @Autowired
  private SystemConfigService systemConfigService;

  @RequiresPermissions("system:edit")
  @GetMapping("/edit")
  public String edit(Model model) {
    model.addAttribute("systems", systemConfigService.selectAll());
    return "admin/system/edit";
  }

  @RequiresPermissions("system:edit")
  @PostMapping("/edit")
  @ResponseBody
  public Result edit(String[] key, String[] value) {
    if (key.length != value.length) return error("数据异常");
    for (int i = 0; i < key.length; i++) {
      SystemConfig systemConfig = new SystemConfig();
      systemConfig.setKey(key[i]);
      systemConfig.setValue(value[i]);
      systemConfigService.update(systemConfig);
    }
    return success();
  }
}
