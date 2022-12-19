'use strict';

const reportFileUtil = require("./reportFileUtil");
const autoLaunchManager = require('../library/autoLaunch');

/**
 * 开机自启动模块设置
 */
module.exports = {
    install(eeApp) {
        eeApp.logger.info("开机自启动模块设置");
        const localData = reportFileUtil.getStartBySys();
        if (!localData){
            reportFileUtil.updateStartSet(true);
            autoLaunchManager.enable();
        }else{
            if (localData){
                autoLaunchManager.enable();
            }else{
                autoLaunchManager.disable();
            }
        }
    }
}


