'use strict';

const {foperator} = require("./folder.operation");
const fs = require('fs')
const path = require('path')
const schedule = require('node-schedule')
const moment = require('moment');
const {rabbitmq} = require("./rabbitmq.js");
const reportFileUtil = require("./reportFileUtil");
/**
 * 报文附件上传模块
 */
module.exports = {
    install(eeApp) {
        showLog("开始执行报文附件上传模块");
        let taskOne;
        let folder;
        let account =reportFileUtil.getAccount();
        const localData = reportFileUtil.getBgdFileDir();
        if (!localData){
            let localGetData = reportFileUtil.autoGetReportDir();
            showLog("localGetData"+ localGetData);
            if (localGetData&&localGetData.flag){
                showLog("localGetData" + localGetData.fileDir);
                folder = reportFileUtil.updateBgdFileDir(localGetData.fileDir);
            }else{
                showLog("未能自动识别报文目录，"+localGetData.msg);
            }
        }else{
            folder = localData;
        }
        taskStart();
        let count=0;
        function taskStart() {
            showLog("启动定时器任务进行报文上传mq操作");
            schedule.scheduleJob('0 * * * * ?', function () {  //执行定时器任务   https://www.jb51.net/article/257867.htm
                // eeApp.logger.info('start'+ count++);
                showLog('定时器执行次数：'+ count++);
                if (account&&account.userName){
                    uploadAction(folder, account);
                }else{
                    account = reportFileUtil.getAccount();
                }
            });
        }

        function uploadAction(fromDir, account) {
            let fileDirectory = fromDir;
            if (fs.existsSync(fileDirectory)) {
                let paths = foperator.getPaths(fileDirectory);
                let pathLen = paths.length;
                if (!pathLen) {
                    // eeApp.logger.info('执行定时任务，所选的本地文件夹暂时没有文件。');
                    showLog("报文目录下无文件,跳出等待下一次轮询");
                    return;
                }
                //foperator.copy(fileDirectory, fileDirectory + '_备份_' + pathLen + '_' + moment().format('YYYY_MM_DD_hh_mm_ss'));
                // eeApp.logger.info(' 备份 ' + pathLen + ' 文件成功。');
                let dirLen = ~~fileDirectory.length;
                var bak_dir=getBakFir(fileDirectory);
                for (var i = 0; i < pathLen; i++) {
                    let srcPath = paths[i];
                    let fileName = srcPath.substr(dirLen);
                    if (srcPath) {
                        //TODO 此处要添加对文件类型的判断  哪些文件被允许解析
                        //读取文件内容,发送到rabbitmq
                        var fileContent = fs.readFileSync(srcPath,"utf-8");//注意一定要用readFileSync同步方法   如果用异步方法 写法要大改
                        showLog("读取文件内容成功，文件内容:"+fileContent);
                            //把文件内容和文件名拼接成json字符串，用于推送
                        var messageObj={"fileName":fileName,"fileContent":fileContent,"userName": account.userName, "realName":account.realName,"orgId":account.orgId,"orgName":account.orgName};
                        var message=JSON.stringify(messageObj);
                            //往rabbitmq中推送数据
                        var mqParams={
                            username: 'panhao',
                            password: 'dzka1234',
                            body:message,
                            headers:'',
                        }
                        showLog("开始推送文件数据至rabbitmq:"+getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                        rabbitmq.sendQueueMsg(mqParams,function (s){
                            showLog("执行结果:"+s);
                        });
                        //再备份
                        var today=getCurrentDate("yyyy-MM-dd");
                        foperator.copyFile(srcPath,bak_dir+today,fileName);
                        showLog("备份文件成功，路径:"+srcPath);
                        //最后删除
                        foperator.deleteFile(srcPath);
                        // eeApp.logger.info(' delete ' + srcPath + ' 成功。');
                        showLog("删除文件成功，路径:"+srcPath);
                    }
                }
            }
        }
        //展示日志
        function showLog(mes){
            eeApp.logger.info('*********************'+mes+'**********************');
        }
        //目录名称转换成备份目录名称
        function getBakFir(fileDirectory){
            if(fileDirectory&&fileDirectory.length>0){
                var fileDirectory_bak='';
                fileDirectory=fileDirectory.substring(0,fileDirectory.length-1);//去除末尾斜杠\
                var dirs=fileDirectory.split(/\\/);
                if(dirs.length>0){
                    for (var i=0;i<dirs.length;i++){
                        fileDirectory_bak+=i==dirs.length-1?dirs[i]+"_bak\\":dirs[i]+"\\";
                    }
                }
                return fileDirectory_bak;
            }
            return fileDirectory;
        }
        //获取日期或时间
        function getCurrentDate(format) {
            var d=new Date();
            var year=d.getFullYear();
            var month=d.getMonth()+1;
            var date=d.getDate();
            var hour=d.getHours();
            var minite=d.getMinutes();
            var second=d.getSeconds();
            if(month<10)month='0'+month;
            if(date<10)date='0'+date;
            if(hour<10)hour='0'+hour;
            if(minite<10)minite='0'+minite;
            if(second<10)second='0'+second;
            if(!format)format='yyyy-MM-dd';
            return format.replace('yyyy',year).replace('MM',month).replace('dd',date).replace('HH',hour).replace('mm',minite).replace('ss',second);
        };

    }
}


