// 采用 promise 形式的AP
'use strict';

const {app} = require('electron');
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
};

function getBgdFileDir()
{
    let data = customsDB.db
        .get(customsDB.customsDBKey.bgdFileDir)
        .value();

    if (_.isEmpty(data)) {
        data = ""
    }
    return data;
}

function autoGetReportDir()
{
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
}

function updateBgdFileDir(fileDir)
{
    const data = customsDB.db.set(customsDB.customsDBKey.bgdFileDir, fileDir).write();
    return data;
}


exports.reportFileUtil = {
    getBgdFileDir: getBgdFileDir,
    autoGetReportDir: autoGetReportDir,
    updateBgdFileDir: updateBgdFileDir
}
