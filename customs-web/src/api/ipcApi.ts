const ipcApiRoute = {
  test: 'controller.client.test',
  messageShow: 'controller.client.messageShow',
  messageShowConfirm: 'controller.client.messageShowConfirm',
  selectFolder: 'controller.client.selectFolder',
  openDirectory: 'controller.client.openDirectory',
  loadViewContent: 'controller.client.loadViewContent',
  removeViewContent: 'controller.client.removeViewContent',
  createWindow: 'controller.client.createWindow',
  sendNotification: 'controller.client.sendNotification',
  initPowerMonitor: 'controller.client.initPowerMonitor',
  getScreen: 'controller.client.getScreen',
  openSoftware: 'controller.client.openSoftware', 
  autoLaunch: 'controller.client.autoLaunch',
  setTheme: 'controller.client.setTheme',
  getTheme: 'controller.client.getTheme',
  checkForUpdater: 'controller.client.checkForUpdater',
  downloadApp: 'controller.client.downloadApp',
  dbOperation: 'controller.client.dbOperation',
  sqlitedbOperation: 'controller.client.sqlitedbOperation',
  uploadFile: 'controller.client.uploadFile',
  checkHttpServer: 'controller.client.checkHttpServer',
  doHttpRequest: 'controller.client.doHttpRequest',
  doSocketRequest: 'controller.client.doSocketRequest',
  ipcInvokeMsg: 'controller.client.ipcInvokeMsg',
  ipcSendSyncMsg: 'controller.client.ipcSendSyncMsg',
  ipcSendMsg: 'controller.client.ipcSendMsg',
  hello: 'controller.client.hello',
}



export {
  ipcApiRoute,
}