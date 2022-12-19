'use strict';

const _ = require('lodash');
const path = require('path');
const fs = require('fs');
const os = require('os');
const is = require('electron-is');
const { exec } = require('child_process');
const Controller = require('ee-core').Controller;
const Utils = require('ee-core').Utils;
const electronApp = require('electron').app;
const {dialog, webContents, shell, BrowserWindow, BrowserView,
  Notification, powerMonitor, screen, nativeTheme} = require('electron');
const autoLaunchManager = require('../library/autoLaunch');
const reportFileUtil = require('../library/reportFileUtil');
const dayjs = require('dayjs');
const xmlreader = require('xmlreader');

let myTimer = null;
let browserViewObj = null;
let notificationObj = null;

/**
 * 示例控制器
 * @class
 */
class ClientController extends Controller {

  constructor(ctx) {
    super(ctx);
  }

  /**
   * 新增或修改报关单报文目录
   * @param args
   * @returns {Promise<*>}
   */
  async updateBgdFileDir(args) {
    const {service} = this;
    const data = reportFileUtil.updateBgdFileDir(args.bgd_path);
    return data;
  }

  async updateStartSet(args) {
    const data = reportFileUtil.updateStartSet(args.startSet);
    return data;
  }

  /**
   * 获取报关单报文目录
   * @returns {Promise<string>}
   */
  async getBgdFileDir() {
    const {service} = this;
    const data = reportFileUtil.getBgdFileDir();
    return data;
  }

  /**
   * 获取 客户端自启动状态
   * @returns {Promise<string>}
   */
  async getStartSetState() {
    const data = reportFileUtil.getStartBySys();
    return data;
  }

  async saveAllSet(args) {
    const data = reportFileUtil.updateBgdFileDir(args.bgd_path);
    reportFileUtil.updateStartSet(args.startSet);
    if (args.startSet) {
      autoLaunchManager.enable();

    } else {
      autoLaunchManager.disable();
    }
    return data;
  }

  /**
   * 自动获取报关单报文目录
   * @param args
   * @returns {Promise<*>}
   */
  async autoGetReportDir() {
    const returnData = {
      flag: false,
      msg: '',
      fileDir: '',
    }
    const homedir = os.homedir();
    const xmlPath = path.join(homedir, "/AppData/Local/SIC/UserSetting/", "DFWJobSettings.xml");
    const xmlStr = fs.readFileSync(xmlPath, "utf8");
    if(!xmlStr){
      returnData.msg="获取失败，未查找到配置文件，请确认是否已安装单一窗口客户端！";
    }
    /*const xmlStr ="<?xml version=\"1.0\"?>\n" +
        "<Settings xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
        "  <FormSettings>\n" +
        "    <FormSetting Key=\"OTHERS\" SystemId=\"None\" DisplayRow=\"-5\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"OTHERS\" OutBox=\"C:\\ImpPath\\Others\\OutBox\" InBox=\"C:\\ImpPath\\Others\\InBox\" SentBox=\"C:\\ImpPath\\Others\\SentBox\" FailBox=\"C:\\ImpPath\\Others\\FailBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"DEC\" SystemId=\"DXPDSWDEC0000002|DXPDSWDECEDOC002\" DisplayRow=\"1\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"DECCUS001\" OutBox=\"C:\\ImpPath\\Deccus001\\OutBox\" InBox=\"C:\\ImpPath\\Deccus001\\InBox\" SentBox=\"C:\\ImpPath\\Deccus001\\SentBox\" FailBox=\"C:\\ImpPath\\Deccus001\\FailBox\" TempBox=\"C:\\ImpPath\\Deccus001\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "      <SubFormSetting FormId=\"DECCUS201\" OutBox=\"C:\\ImpPath\\Deccus201\\OutBox\" InBox=\"C:\\ImpPath\\Deccus201\\InBox\" SentBox=\"C:\\ImpPath\\Deccus201\\SentBox\" FailBox=\"C:\\ImpPath\\Deccus201\\FailBox\" TempBox=\"C:\\ImpPath\\Deccus201\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "      <SubFormSetting FormId=\"DECCUS301\" OutBox=\"C:\\ImpPath\\Deccus301\\OutBox\" InBox=\"C:\\ImpPath\\Deccus301\\InBox\" SentBox=\"C:\\ImpPath\\Deccus301\\SentBox\" FailBox=\"C:\\ImpPath\\Deccus301\\FailBox\" TempBox=\"C:\\ImpPath\\Deccus301\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "      <SubFormSetting FormId=\"DECCUS401\" OutBox=\"C:\\ImpPath\\Deccus401\\OutBox\" InBox=\"C:\\ImpPath\\Deccus401\\InBox\" SentBox=\"C:\\ImpPath\\Deccus401\\SentBox\" FailBox=\"C:\\ImpPath\\Deccus401\\FailBox\" TempBox=\"C:\\ImpPath\\Deccus401\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "      <SubFormSetting FormId=\"DECCIQ001\" OutBox=\"C:\\ImpPath\\Decciq001\\OutBox\" InBox=\"C:\\ImpPath\\Decciq001\\InBox\" SentBox=\"C:\\ImpPath\\Decciq001\\SentBox\" FailBox=\"C:\\ImpPath\\Decciq001\\FailBox\" TempBox=\"C:\\ImpPath\\Decciq001\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "      <SubFormSetting FormId=\"DECCIQ101\" OutBox=\"C:\\ImpPath\\Decciq101\\OutBox\" InBox=\"C:\\ImpPath\\Decciq101\\InBox\" SentBox=\"C:\\ImpPath\\Decciq101\\SentBox\" FailBox=\"C:\\ImpPath\\Decciq101\\FailBox\" TempBox=\"C:\\ImpPath\\Decciq101\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "      <SubFormSetting FormId=\"DECCIQ201\" OutBox=\"C:\\ImpPath\\Decciq201\\OutBox\" InBox=\"C:\\ImpPath\\Decciq201\\InBox\" SentBox=\"C:\\ImpPath\\Decciq201\\SentBox\" FailBox=\"C:\\ImpPath\\Decciq201\\FailBox\" TempBox=\"C:\\ImpPath\\Decciq201\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"EDOC\" SystemId=\"DXPDSWDECEDOC002\" DisplayRow=\"3\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"DECEDOC001\" OutBox=\"C:\\ImpPath\\Decedoc001\\OutBox\" InBox=\"C:\\ImpPath\\Decedoc001\\InBox\" SentBox=\"C:\\ImpPath\\Decedoc001\\SentBox\" FailBox=\"C:\\ImpPath\\Decedoc001\\FailBox\" TempBox=\"C:\\ImpPath\\Decedoc001\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"MFT\" SystemId=\"DXPDSWMFT0000001\" DisplayRow=\"5\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"MFT\" OutBox=\"C:\\ImpPath\\Mft\\OutBox\" InBox=\"C:\\ImpPath\\Mft\\InBox\" SentBox=\"C:\\ImpPath\\Mft\\SentBox\" FailBox=\"C:\\ImpPath\\Mft\\FailBox\" TempBox=\"C:\\ImpPath\\Mft\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"RMFT\" SystemId=\"DXPDSWRMFT000001\" DisplayRow=\"7\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"RMFT\" OutBox=\"C:\\ImpPath\\Rmft\\OutBox\" InBox=\"C:\\ImpPath\\Rmft\\InBox\" SentBox=\"C:\\ImpPath\\Rmft\\SentBox\" FailBox=\"C:\\ImpPath\\Rmft\\FailBox\" TempBox=\"C:\\ImpPath\\Rmft\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"TMFT\" SystemId=\"DXPDSWTMFT000001\" DisplayRow=\"9\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"TMFT\" OutBox=\"C:\\ImpPath\\Tmft\\OutBox\" InBox=\"C:\\ImpPath\\Tmft\\InBox\" SentBox=\"C:\\ImpPath\\Tmft\\SentBox\" FailBox=\"C:\\ImpPath\\Tmft\\FailBox\" TempBox=\"C:\\ImpPath\\Tmft\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"CDS\" SystemId=\"DXPDSWCDS0000001\" DisplayRow=\"11\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"CDS\" OutBox=\"C:\\ImpPath\\Cds\\OutBox\" InBox=\"C:\\ImpPath\\Cds\\InBox\" SentBox=\"C:\\ImpPath\\Cds\\SentBox\" FailBox=\"C:\\ImpPath\\Cds\\FailBox\" TempBox=\"C:\\ImpPath\\Cds\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"CDSAL\" SystemId=\"DXPDSWCDSAL00001\" DisplayRow=\"13\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"CDSAL\" OutBox=\"C:\\ImpPath\\Cdsal\\OutBox\" InBox=\"C:\\ImpPath\\Cdsal\\InBox\" SentBox=\"C:\\ImpPath\\Cdsal\\SentBox\" FailBox=\"C:\\ImpPath\\Cdsal\\FailBox\" TempBox=\"C:\\ImpPath\\Cdsal\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"TCDS\" SystemId=\"DXPDSWTCDS000001\" DisplayRow=\"15\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"TCDS\" OutBox=\"C:\\ImpPath\\Tcds\\OutBox\" InBox=\"C:\\ImpPath\\Tcds\\InBox\" SentBox=\"C:\\ImpPath\\Tcds\\SentBox\" FailBox=\"C:\\ImpPath\\Tcds\\FailBox\" TempBox=\"C:\\ImpPath\\Tcds\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"NEMS\" SystemId=\"DXPDSWNEMS000001\" DisplayRow=\"17\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"NEMS\" OutBox=\"C:\\ImpPath\\Nems\\OutBox\" InBox=\"C:\\ImpPath\\Nems\\InBox\" SentBox=\"C:\\ImpPath\\Nems\\SentBox\" FailBox=\"C:\\ImpPath\\Nems\\FailBox\" TempBox=\"C:\\ImpPath\\Nems\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"ACMP\" SystemId=\"DXPDSWACMP000001\" DisplayRow=\"19\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"ACMP\" OutBox=\"C:\\ImpPath\\Acmp\\OutBox\" InBox=\"C:\\ImpPath\\Acmp\\InBox\" SentBox=\"C:\\ImpPath\\Acmp\\SentBox\" FailBox=\"C:\\ImpPath\\Acmp\\FailBox\" TempBox=\"C:\\ImpPath\\Acmp\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"AIL\" SystemId=\"DXPDSWLICENCE001\" DisplayRow=\"21\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"AIL\" OutBox=\"C:\\ImpPath\\Ail\\OutBox\" InBox=\"C:\\ImpPath\\Ail\\InBox\" SentBox=\"C:\\ImpPath\\Ail\\SentBox\" FailBox=\"C:\\ImpPath\\Ail\\FailBox\" TempBox=\"C:\\ImpPath\\Ail\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"EXB\" SystemId=\"DXPDSWEXB0000001\" DisplayRow=\"23\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"EXB\" OutBox=\"C:\\ImpPath\\Exb\\OutBox\" InBox=\"C:\\ImpPath\\Exb\\InBox\" SentBox=\"C:\\ImpPath\\Exb\\SentBox\" FailBox=\"C:\\ImpPath\\Exb\\FailBox\" TempBox=\"C:\\ImpPath\\Exb\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"NJCF\" SystemId=\"DXPDSWNJCF000001\" DisplayRow=\"25\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"NJCF\" OutBox=\"C:\\ImpPath\\Njcf\\OutBox\" InBox=\"C:\\ImpPath\\Njcf\\InBox\" SentBox=\"C:\\ImpPath\\Njcf\\SentBox\" FailBox=\"C:\\ImpPath\\Njcf\\FailBox\" TempBox=\"C:\\ImpPath\\Njcf\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"SAS\" SystemId=\"DXPDSWSAS0000001\" DisplayRow=\"27\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"SAS\" OutBox=\"C:\\ImpPath\\Sas\\OutBox\" InBox=\"C:\\ImpPath\\Sas\\InBox\" SentBox=\"C:\\ImpPath\\Sas\\SentBox\" FailBox=\"C:\\ImpPath\\Sas\\FailBox\" TempBox=\"C:\\ImpPath\\Sas\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"NPTS\" SystemId=\"DXPDSWNPTS000001\" DisplayRow=\"29\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"NPTS\" OutBox=\"C:\\ImpPath\\Npts\\OutBox\" InBox=\"C:\\ImpPath\\Npts\\InBox\" SentBox=\"C:\\ImpPath\\Npts\\SentBox\" FailBox=\"C:\\ImpPath\\Npts\\FailBox\" TempBox=\"C:\\ImpPath\\Npts\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "    <FormSetting Key=\"DECSUB\" SystemId=\"DXPDSWDECSUB0001\" DisplayRow=\"31\" RetNameAffixFlag=\"0\">\n" +
        "      <SubFormSetting FormId=\"DECSUB\" OutBox=\"C:\\ImpPath\\Decsub\\OutBox\" InBox=\"C:\\ImpPath\\Decsub\\InBox\" SentBox=\"C:\\ImpPath\\Decsub\\SentBox\" FailBox=\"C:\\ImpPath\\Decsub\\FailBox\" TempBox=\"C:\\ImpPath\\Decsub\\TempBox\" MaxDegreeOfParallelism=\"1\" SendBeforeEnable=\"false\" IsPdfCheck=\"false\" />\n" +
        "    </FormSetting>\n" +
        "  </FormSettings>\n" +
        "  <UserPin>LZkdOyDzyKkggjYZb27iTA==</UserPin>\n" +
        "  <EncryptorSetting EncryptorType=\"15\">\n" +
        "    <EncryptorConfigFile />\n" +
        "    <DeviceLibs>XASJ_SDF,DJ_SDF,SGD_Dll,swsds</DeviceLibs>\n" +
        "    <Device>hsm</Device>\n" +
        "    <CertIndex>0</CertIndex>\n" +
        "    <EportCertFile />\n" +
        "    <EportCardID />\n" +
        "    <CreateCfgFile>10</CreateCfgFile>\n" +
        "    <IpAddr />\n" +
        "    <Port>0</Port>\n" +
        "  </EncryptorSetting>\n" +
        "  <Notification UseEmailNotification=\"false\" UseHttpNotification=\"false\">\n" +
        "    <EmailNotification>\n" +
        "      <SmtpPort>25</SmtpPort>\n" +
        "      <EnableSsl>false</EnableSsl>\n" +
        "    </EmailNotification>\n" +
        "  </Notification>\n" +
        "  <TimeInterval>15</TimeInterval>\n" +
        "  <RetentionDays>7</RetentionDays>\n" +
        "  <LogPath>%LOCALAPPDATA%/SIC/Log/</LogPath>\n" +
        "  <JobsAutoStart>DXP</JobsAutoStart>\n" +
        "  <StpReceipt2File>true</StpReceipt2File>\n" +
        "</Settings>";*/
    //const xmlStr = "";
    xmlreader.read(xmlStr, function (err, res) {
      if (err){
        returnData.msg = "获取失败，读取配置文件出错，请联系管理员！";
        return err;
      }

      if (res&& res.Settings&& res.Settings.FormSettings&& res.Settings.FormSettings.FormSetting){
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
      if (!returnData.fileDir){
        returnData.msg="获取失败，请在单一窗口中确认是否已勾选报关单订阅！";
      }else{
        returnData.flag = true;
      }
    });
    return returnData;
  }


  restartConfirm() {
    const res = dialog.showMessageBoxSync({
      type: 'info',
      title: '重新启动',
      message: '是否重启启动，以使配置更改生效',
      detail: '是否重启启动，以使配置更改生效',
      cancelId: 1, // 用于取消对话框的按钮的索引
      defaultId: 0, // 设置默认选中的按钮
      buttons: ['确认', '取消'], // 按钮及索引
    })
    if(res === 0) {
      electronApp.relaunch();
      electronApp.exit();
    }
    return res;
  }


  /**
   * json数据库操作
   */
  async dbOperation(args) {
    const { service } = this;
    const paramsObj = args;
    //console.log('eeeee paramsObj:', paramsObj);
    const data = {
      action: paramsObj.action,
      result: null,
      all_list: []
    };

    switch (paramsObj.action) {
      case 'add' :
        data.result = await service.storage.addTestData(paramsObj.info);;
        break;
      case 'del' :
        data.result = await service.storage.delTestData(paramsObj.delete_key);;
        break;
      case 'update' :
        data.result = await service.storage.updateTestData(paramsObj.update_key, paramsObj.update_value);
        break;
      case 'get' :
        data.result = await service.storage.getTestData(paramsObj.search_key);
        break;
    }

    data.all_list = await service.storage.getAllTestData();

    return data;
  }


  /**
   * sqlite数据库操作
   */
  async sqlitedbOperation(args) {
    const { service } = this;
    const paramsObj = args;
    //console.log('eeeee paramsObj:', paramsObj);
    const data = {
      action: paramsObj.action,
      result: null,
      all_list: []
    };

    switch (paramsObj.action) {
      case 'add' :
        data.result = await service.storage.addTestDataSqlite(paramsObj.info);;
        break;
      case 'del' :
        data.result = await service.storage.delTestDataSqlite(paramsObj.delete_name);;
        break;
      case 'update' :
        data.result = await service.storage.updateTestDataSqlite(paramsObj.update_name, paramsObj.update_age);
        break;
      case 'get' :
        data.result = await service.storage.getTestDataSqlite(paramsObj.search_age);
        break;
    }

    data.all_list = await service.storage.getAllTestDataSqlite();

    return data;
  }

  /**
   * 消息提示对话框
   */
  messageShow () {
    dialog.showMessageBoxSync({
      type: 'info', // "none", "info", "error", "question" 或者 "warning"
      title: '自定义标题-message',
      message: '自定义消息内容',
      detail: '其它的额外信息'
    })

    return '打开了消息框';
  }

  /**
   * 消息提示与确认对话框
   */
  messageShowConfirm () {
    const res = dialog.showMessageBoxSync({
      type: 'info',
      title: '自定义标题-message',
      message: '自定义消息内容',
      detail: '其它的额外信息',
      cancelId: 1, // 用于取消对话框的按钮的索引
      defaultId: 0, // 设置默认选中的按钮
      buttons: ['确认', '取消'], // 按钮及索引
    })
    let data = (res === 0) ? '点击确认按钮' : '点击取消按钮';

    return data;
  }

  /**
   * 选择目录
   */
  selectFolder () {
    const filePaths = dialog.showOpenDialogSync({
      properties: ['openDirectory', 'createDirectory']
    });

    if (_.isEmpty(filePaths)) {
      return null
    }

    return filePaths[0];
  }

  /**
   * 打开目录
   */
  openDirectory (args) {
    if (!args.id) {
      return false;
    }
    const dir = electronApp.getPath(args.id);
    shell.openPath(dir);
    return true;
  }

  /**
   * 加载视图内容
   */
  loadViewContent (args) {
    let content = null;
    if (args.type == 'html') {
      content = path.join('file://', electronApp.getAppPath(), args.content)
    } else {
      content = args.content;
    }

    browserViewObj = new BrowserView();
    this.app.electron.mainWindow.setBrowserView(browserViewObj)
    browserViewObj.setBounds({
      x: 300,
      y: 170,
      width: 650,
      height: 400
    });
    browserViewObj.webContents.loadURL(content);
    return true
  }

  /**
   * 移除视图内容
   */
  removeViewContent () {
    this.app.electron.mainWindow.removeBrowserView(browserViewObj);
    return true
  }

  /**
   * 打开新窗口
   */
  createWindow (args) {
    let content = null;
    if (args.type == 'html') {
      content = path.join('file://', electronApp.getAppPath(), args.content)
    } else {
      content = args.content;
    }

    let winObj = new BrowserWindow({
      x: 10,
      y: 10,
      width: 980,
      height: 650
    })
    winObj.loadURL(content);

    return winObj.id
  }

  /**
   * 加载扩展程序
   */
  // async loadExtension (args) {
  //   const crxFile = args[0];
  //   if (_.isEmpty(crxFile)) {
  //     return false;
  //   }
  //   const extensionId = path.basename(crxFile, '.crx');
  //   const chromeExtensionDir = chromeExtension.getDirectory();
  //   const extensionDir = path.join(chromeExtensionDir, extensionId);

  //   console.log("[api] [example] [loadExtension] extension id:", extensionId);
  //   unzip(crxFile, extensionDir).then(() => {
  //     console.log("[api] [example] [loadExtension] unzip success!");
  //     chromeExtension.load(extensionId);
  //   });

  //   return true;
  // }

  /**
   * 创建系统通知
   */
  sendNotification (arg, event) {
    const channel = 'controller.client.sendNotification';
    if (!Notification.isSupported()) {
      return '当前系统不支持通知';
    }

    let options = {};
    if (!_.isEmpty(arg.title)) {
      options.title = arg.title;
    }
    if (!_.isEmpty(arg.subtitle)) {
      options.subtitle = arg.subtitle;
    }
    if (!_.isEmpty(arg.body)) {
      options.body = arg.body;
    }
    if (!_.isEmpty(arg.silent)) {
      options.silent = arg.silent;
    }

    notificationObj = new Notification(options);

    if (arg.clickEvent) {
      notificationObj.on('click', (e) => {
        let data = {
          type: 'click',
          msg: '您点击了通知消息'
        }
        event.reply(`${channel}`, data)
      });
    }

    if (arg.closeEvent) {
      notificationObj.on('close', (e) => {
        let data = {
          type: 'close',
          msg: '您关闭了通知消息'
        }
        event.reply(`${channel}`, data)
      });
    }

    notificationObj.show();

    return true
  }

  /**
   * 电源监控
   */
  initPowerMonitor (arg, event) {
    const channel = 'controller.client.initPowerMonitor';
    powerMonitor.on('on-ac', (e) => {
      let data = {
        type: 'on-ac',
        msg: '接入了电源'
      }
      event.reply(`${channel}`, data)
    });

    powerMonitor.on('on-battery', (e) => {
      let data = {
        type: 'on-battery',
        msg: '使用电池中'
      }
      event.reply(`${channel}`, data)
    });

    powerMonitor.on('lock-screen', (e) => {
      let data = {
        type: 'lock-screen',
        msg: '锁屏了'
      }
      event.reply(`${channel}`, data)
    });

    powerMonitor.on('unlock-screen', (e) => {
      let data = {
        type: 'unlock-screen',
        msg: '解锁了'
      }
      event.reply(`${channel}`, data)
    });

    return true
  }

  /**
   * 获取屏幕信息
   */
  getScreen (arg) {
    let data = [];
    let res = {};
    if (arg == 0) {
      let res = screen.getCursorScreenPoint();
      data = [
        {
          title: '横坐标',
          desc: res.x
        },
        {
          title: '纵坐标',
          desc: res.y
        },
      ]

      return data;
    }
    if (arg == 1) {
      res = screen.getPrimaryDisplay();
    }
    if (arg == 2) {
      let resArr = screen.getAllDisplays();
      // 数组，只取一个吧
      res = resArr[0];
    }
    // console.log('[electron] [ipc] [example] [getScreen] res:', res);
    data = [
      {
        title: '分辨率',
        desc: res.bounds.width + ' x ' + res.bounds.height
      },
      {
        title: '单色显示器',
        desc: res.monochrome ? '是' : '否'
      },
      {
        title: '色深',
        desc: res. colorDepth
      },
      {
        title: '色域',
        desc: res.colorSpace
      },
      {
        title: 'scaleFactor',
        desc: res.scaleFactor
      },
      {
        title: '加速器',
        desc: res.accelerometerSupport
      },
      {
        title: '触控',
        desc: res.touchSupport == 'unknown' ? '不支持' : '支持'
      },
    ]

    return data;
  }

  /**
   * 调用其它程序（exe、bash等可执行程序）
   */
  openSoftware (softName) {
    if (!softName) {
      return false;
    }

    let softwarePath = path.join(Utils.getExtraResourcesDir(), softName);
    this.app.logger.info('[openSoftware] softwarePath:', softwarePath);

    // 检查程序是否存在
    if (!fs.existsSync(softwarePath)) {
      return false;
    }
    // 命令行字符串 并 执行
    let cmdStr = 'start ' + softwarePath;
    exec(cmdStr);

    return true;
  }

  /**
   * 开机启动-开启
   */
  autoLaunch (type) {
    console.log('type:', type);
    let res = {
      type: type,
      status: null
    };
    if (type == 'check') {
      res.status = autoLaunchManager.isEnabled();
    } else if (type == 'open') {
      autoLaunchManager.enable();
      res.status = true;
    } else if (type == 'close') {
      autoLaunchManager.disable();
      res.status = false;
    }

    return res
  }

  /**
   * 获取系统主题
   */
  getTheme () {
    let theme = 'system';
    if (nativeTheme.shouldUseHighContrastColors) {
      theme = 'light';
    } else if (nativeTheme.shouldUseInvertedColorScheme) {
      theme = 'dark';
    }

    return theme;
  }

  /**
   * 设置系统主题
   */
  setTheme (args) {

    // TODO 好像没有什么明显效果
    nativeTheme.themeSource = args;

    return args;
  }


  /**
   * 检查是否有新版本
   */
  checkForUpdater () {
    const config = this.app.config.autoUpdate;
    if ( (is.windows() && config.windows) || (is.macOS() && config.macOS) || (is.linux() && config.linux) ) {
      const autoUpdater = require('../library/autoUpdater');
      autoUpdater.checkUpdate();
    }

    return;
  }

  /**
   * 下载新版本
   */
  downloadApp () {
    const config = this.app.config.autoUpdate;
    if ( (is.windows() && config.windows) || (is.macOS() && config.macOS) || (is.linux() && config.linux) ) {
      const autoUpdater = require('../library/autoUpdater');
      autoUpdater.download();
    }
    return;
  }

  /**
   * 上传文件
   */
  async uploadFile() {
    // const self = this;
    // const { ctx, service } = this;
    // let tmpDir = Utils.getLogDir();
    // const file = ctx.request.files[0];

    // try {
    //   let tmpFile = fs.readFileSync(file.filepath)
    //   fs.writeFileSync(path.join(tmpDir, file.filename), tmpFile)
    // } finally {
    //   await fs.unlink(file.filepath, function(){});
    // }
    // const fileStream = fs.createReadStream(path.join(tmpDir, file.filename))
    // const uploadRes = await service.example.uploadFileToSMMS(fileStream);

    // return uploadRes;
  }

  /**
   * 检测http服务是否开启
   */
  async checkHttpServer () {
    const httpServerConfig = this.app.config.httpServer;
    const url = httpServerConfig.protocol + httpServerConfig.host + ':' + httpServerConfig.port;

    const data = {
      enable: httpServerConfig.enable,
      server: url
    }
    return data;
  }

  /**
   * 一个http请求访问此方法
   */
  async doHttpRequest () {
    // http方法
    const method = this.app.request.method;
    // http get 参数
    let params = this.app.request.query;
    params = (params instanceof Object) ? params : JSON.parse(JSON.stringify(params));
    // http post 参数
    const body = this.app.request.body;

    const httpInfo = {
      method,
      params,
      body
    }
    console.log('httpInfo:', httpInfo);

    if (!body.id) {
      return false;
    }
    const dir = electronApp.getPath(body.id);
    shell.openPath(dir);

    return true;
  }

  /**
   * 一个socket io请求访问此方法
   */
  async doSocketRequest (args) {
    if (!args.id) {
      return false;
    }
    const dir = electronApp.getPath(args.id);
    shell.openPath(dir);

    return true;
  }

  /**
   * 异步消息类型
   * @param args 前端传的参数
   * @param event - IpcMainInvokeEvent 文档：https://www.electronjs.org/zh/docs/latest/api/structures/ipc-main-invoke-event
   */
   async ipcInvokeMsg (args, event) {
    let timeNow = dayjs().format('YYYY-MM-DD HH:mm:ss');
    const data = args + ' - ' + timeNow;

    return data;
  }

  /**
   * 同步消息类型
   * @param args 前端传的参数
   * @param event - IpcMainEvent 文档：https://www.electronjs.org/docs/latest/api/structures/ipc-main-event
   */
  async ipcSendSyncMsg (args) {
    let timeNow = dayjs().format('YYYY-MM-DD HH:mm:ss');
    const data = args + ' - ' + timeNow;

    return data;
  }

  /**
   * 双向异步通信
   * @param args 前端传的参数
   * @param event - IpcMainEvent 文档：https://www.electronjs.org/docs/latest/api/structures/ipc-main-event
   */
  ipcSendMsg (args, event) {
    // 前端ipc频道 channel
    const channel = 'controller.client.ipcSendMsg';

    if (args.type == 'start') {
      // 每隔1秒，向前端页面发送消息
      // 用定时器模拟
      myTimer = setInterval(function(e, c, msg) {
        let timeNow = Date.now();
        let data = msg + ':' + timeNow;
        e.reply(`${c}`, data)
      }, 1000, event, channel, args.content)

      return '开始了'
    } else if (args.type == 'end') {
      clearInterval(myTimer);
      return '停止了'
    } else {
      return 'ohther'
    }
  }

  /**
   * 测试接口
   */
  hello (args) {
    console.log('hello ', args);
  }
}

ClientController.toString = () => '[class ClientController]';
module.exports = ClientController;
