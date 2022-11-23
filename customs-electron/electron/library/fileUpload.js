'use strict';

const {foperator} = require("./folder.operation");
const fs = require('fs')
const path = require('path')
const schedule = require('node-schedule')
const moment = require('moment');
/**
 * 应用唤醒模块
 */
module.exports = {
    install(eeApp) {
        eeApp.logger.info('[task] load  module');
        let taskOne
        let folder = "D:\\data\\receipt\\222\\"
        taskStart();
        let count=0
        function taskStart() {
            schedule.scheduleJob('0 0/1 * * * ?', function () {
                eeApp.logger.info('start'+ count++);
                uploadAction(folder);
            });
            /*taskOne = schedule.scheduleJob(folder, () => {
                uploadAction(folder);
                /!*if (client.getConnectionStatus() == 'connected') {
                    uploadAction(args.folder)
                } else {
                    client.reconnect()
                    uploadAction(args.folder)
                }*!/
            })*/
        }

        function uploadAction(fromDir) {

            let fileDirectory = fromDir
            if (fs.existsSync(fileDirectory)) {

                let paths = foperator.getPaths(fileDirectory)

                let pathLen = paths.length

                if (!pathLen) {
                    eeApp.logger.info('执行定时任务，所选的本地文件夹暂时没有文件。');
                    return
                }

                //foperator.copy(fileDirectory, fileDirectory + '_备份_' + pathLen + '_' + moment().format('YYYY_MM_DD_hh_mm_ss'));
                eeApp.logger.info(' 备份 ' + pathLen + ' 文件成功。');

                let dirLen = ~~fileDirectory.length

                for (var i = 0; i < pathLen; i++) {
                    let srcPath = paths[i]
                    let destPathStr = srcPath.substr(dirLen)
                    let destPath = destPathStr.replace(/\\/g, '/')

                    let dirPath = path.dirname(destPath)
                    let filename = path.basename(destPath)

                    if (dirPath) {
                        eeApp.logger.info(' delete ' + srcPath + ' 成功。');
                        // 判断文件夹是否存在
                        /*client.cwd(dirPath)
                            .then(function (cwdDir) {
                                client.put(srcPath, destPath)
                                    .then(function () {
                                        mainWindow.webContents.send('record-info', 'success', ' upload ' + srcPath)
                                    })
                                    .then(function () {
                                        fs.unlinkSync(srcPath)
                                    })
                                    .then(function () {
                                        mainWindow.webContents.send('record-info', 'default', ' delete ' + srcPath)
                                    })
                                    .catch(function (err) {
                                        mainWindow.webContents.send('record-info', 'danger', srcPath + '上传FTP服务器失败。')
                                    })
                            }).catch(function (err) {
                            mainWindow.webContents.send('record-info', 'warning',
                                '无法上传[' + filename + ']文件<br/>FTP服务器没有[' + dirPath + ']文件夹<br>或检查网络'
                            )
                        })*/
                    }
                }
            }
        }

    }
}


