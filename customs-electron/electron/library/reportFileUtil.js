// 采用 promise 形式的AP
'use strict';

const {app} = require('ee-core');
const os = require("os");
const path = require("path");
const fs = require("fs");
const xmlreader = require("xmlreader");
const _ = require("lodash");
const Storage = require('ee-core').Storage;

let lowdbOptions = {
    driver: 'lowdb'
}
const customsDB = Storage.JsonDB.connection('customs', lowdbOptions);
customsDB.customsDBKey = {
      bgdFileDir: 'bgdFileDir',
      startSet: 'startSet',
};


module.exports = {

    install(eeApp){
        let isBgdFileDir = customsDB.getItem(customsDB.customsDBKey.bgdFileDir);
        if (!isBgdFileDir) {
            customsDB.setItem(customsDB.customsDBKey.bgdFileDir,"")
        }

        let isStartSet = customsDB.getItem(customsDB.customsDBKey.startSet);
        if (!isStartSet) {
            customsDB.setItem(customsDB.customsDBKey.startSet, true)
        }
    },

    /**
     * 获取 本机报关单报文目录
     * @returns {string}
     */
    getBgdFileDir(){
    let data = customsDB.getItem(customsDB.customsDBKey.bgdFileDir);
    if (_.isEmpty(data)) {
        data = ""
    }
    return data;
},

/**
 * 获取 本机是否自启动
 * @returns {string}
 */
getStartBySys() {
    let data = customsDB.getItem(customsDB.customsDBKey.startSet);
    return data;
},

/**
 * 自动获取报关单报文目录
 * @returns {{msg: string, flag: boolean, fileDir: string}}
 */
 autoGetReportDir() {
    let returnData = {
        flag: false,
        msg: '',
        fileDir: '',
    }
    let homedir = os.homedir();
    let xmlPath = path.join(homedir, "/AppData/Local/SIC/UserSetting/", "DFWJobSettings.xml");
    let xmlStr = fs.readFileSync(xmlPath, "utf8");
    if (!xmlStr) {
        returnData.msg = "获取失败，未查找到配置文件，请确认是否已安装单一窗口客户端！";
    }
    xmlreader.read(xmlStr, function (err, res) {
        if (err) {
            returnData.msg = "获取失败，读取配置文件出错，请联系管理员！";
            return err;
        }

        if (res && res.Settings && res.Settings.FormSettings && res.Settings.FormSettings.FormSetting) {
            for (var i = 0; i < res.Settings.FormSettings.FormSetting.count(); i++) {
                const key = res.Settings.FormSettings.FormSetting.at(i).attributes().Key;
                if (key == "DECSUB") {
                    const SubFormSetting = res.Settings.FormSettings.FormSetting.at(i).SubFormSetting;
                    if (SubFormSetting) {
                        returnData.fileDir = SubFormSetting.attributes().InBox;
                        break;
                    }
                }
            }
        }
        if (!returnData.fileDir) {
            returnData.msg = "获取失败，请在单一窗口中确认是否已勾选报关单订阅！";
        } else {
            returnData.flag = true;
        }
    });
    return returnData;
},

/**
 * 修改报关单报文目录
 * @param fileDir
 * @returns {*}
 */
 updateBgdFileDir(fileDir) {
    let data = customsDB.setItem(customsDB.customsDBKey.bgdFileDir, fileDir);
    return data;
},

/**
 * 修改 自启动设置
 * @param flag
 * @returns {*}
 */
 updateStartSet(flag) {
    let data = customsDB.setItem(customsDB.customsDBKey.startSet, flag);
    return data;
},
}

