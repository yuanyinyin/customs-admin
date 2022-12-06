const fs = require('fs')
const path = require('path')
var join = path.join

//递归创建目录 同步方法
function mkdirsSync(dirname) {
  if (fs.existsSync(dirname)) {
    return true;
  } else {
    if (mkdirsSync(path.dirname(dirname))) {
      console.log("[mkdir syc]: " + dirname);
      fs.mkdirSync(dirname);
      return true;
    }
  }
}

function _copy(src, dist) {
  var paths = fs.readdirSync(src)
  paths.forEach(function (p) {
    var _src = src + '\\' + p;
    var _dist = dist + '\\' + p;
    var stat = fs.statSync(_src)
    if (stat.isFile()) {// 判断是文件还是目录
      fs.writeFileSync(_dist, fs.readFileSync(_src));
    } else if (stat.isDirectory()) {
      copyDir(_src, _dist)// 当是目录是，递归复制
    }
  })
}
//单个文件复制文件
function _copyFile(srcFilePath, dist,fileName) {
    var _src = srcFilePath;
    var _dist = dist+"\\"+fileName;
    var stat = fs.statSync(_src)
    if (stat.isFile()) {// 判断是文件还是目录
      fs.writeFileSync(_dist, fs.readFileSync(_src));
    }
}

/*
 * 复制（包括目录、子目录，及其中的文件)
 * @param src {String} 要复制的目录
 * @param dist {String} 复制到目标目录
 */
function copyDir(src, dist) {
  var b = fs.existsSync(dist)
  if (!b) {
    // console.log("mk dist = ", dist)
    mkdirsSync(dist);//创建目录
  }
  // console.log("_copy start")
  _copy(src, dist);
}
//单个文件复制文件
function copyFile(srcFilePath, dist,fileName) {
  var b = fs.existsSync(dist)
  if (!b) {
    // console.log("mk dist = ", dist)
    mkdirsSync(dist);//创建目录
  }
  // console.log("_copy start")
  _copyFile(srcFilePath, dist,fileName);
}


var _createConstruction = function (skinType) {
  let srcWebcontent = path.join(__dirname, '../src/webcontent')
  let srcMaingw = path.join(__dirname, '../src/gw/main')
  let srcMaingd = path.join(__dirname, '../src/gd/main')
  let srcMainMicro = path.join(__dirname, '../src/micro/main')

  let srcNavGw = path.join(__dirname, '../src/gw/nav')
  let srcNavGd = path.join(__dirname, '../src/gd/nav')
  let srcNavMicro = path.join(__dirname, '../src/micro/nav')

  let distWebcontent = path.join(__dirname, '../dist/webcontent')
  let distMain = path.join(__dirname, '../dist/webcontent/main')
  let distNav = path.join(__dirname, '../dist/webcontent/components/nav')

  copyDir(srcWebcontent, distWebcontent)
  console.log("copy webcontent finished")

  if ('gw' == skinType) {
    copyDir(srcMaingw, distMain)
    copyDir(srcNavGw, distNav)
    console.log("copy webcontent/main gw finished")
    console.log("copy webcontent/components/nav gw finished")
  }

  if ('gd' == skinType) {
    copyDir(srcMaingd, distMain)
    copyDir(srcNavGd, distNav)
    console.log("copy webcontent/main gd finished")
    console.log("copy webcontent/components/nav gd finished")
  }

  if ('micro' == skinType) {
    copyDir(srcMainMicro, distMain)
    copyDir(srcNavMicro, distNav)
    console.log("copy webcontent/main micro finished")
    console.log("copy webcontent/components/nav micro finished")
  }
}

// 删除目录(包括目录、子目录，及其中的文件)
var _delDir = function (path) {
  let files = [];
  if (fs.existsSync(path)) {
    files = fs.readdirSync(path);
    files.forEach((file, index) => {
      let curPath = path + "\\" + file;
      if (fs.statSync(curPath).isDirectory()) {
        _delDir(curPath);
        //递归删除文件夹
      } else {
        fs.unlinkSync(curPath);
        //删除文件
        console.info('delete file' + curPath + 'success done.')
      }
    });
    fs.rmdirSync(path);
  }
}

// 删除文件
var _delFile = function (path) {
  if (fs.existsSync(path)) {
    var stat = fs.statSync(path);
    if (stat.isFile()) {// 判断是文件还是目录
      fs.unlinkSync(path);
    }
  }
}


/**
 * 获取传入路径的所有路径
 * @param { String } orginalPath 路径
 */
function _getPaths(orginalPath) {
  let jsonFiles = [];
  function findJsonFile(path) {
    let files = fs.readdirSync(path);
    files.forEach(function (item, index) {
      let fPath = join(path, item);
      let stat = fs.statSync(fPath);
      if (stat.isDirectory() === true) {
        findJsonFile(fPath);
      }
      if (stat.isFile() === true) {
        jsonFiles.push(fPath);
      }
    })
  }
  findJsonFile(orginalPath);
  return jsonFiles
}



function findSync(startPath) {
  let result = [];
  function finder(path) {
    let files = fs.readdirSync(path, 'utf-8');
    files.forEach((val, index) => {
      console.info('val')
      console.info(val)
      let fPath = join(path, val)
      let stats = fs.statSync(fPath, 'utf-8');
      if (stats.isDirectory()) {
        finder(fPath)
      }
      if (stats.isFile()) {
        result.push(fPath)
      }
    })
  }
  finder(startPath);
  return result;
}

exports.foperator = {
  copy: copyDir,
  copyFile:copyFile,
  delete: _delDir,
  deleteFile:_delFile,
  getPaths: _getPaths,
  findSync: findSync,
  create: _createConstruction
}
