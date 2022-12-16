'use strict';

const Service = require('ee-core').Service;
const Storage = require('ee-core').Storage;
const _ = require('lodash');

/**
 * 数据存储
 * @class
 */
class StorageService extends Service {

  constructor (ctx) {
    super(ctx);

    // lowdb数据库
    this.systemDB = Storage.JsonDB.connection('system');
    let lowdbOptions = {
      driver: 'lowdb'
    }
    this.customsDB = Storage.JsonDB.connection('customs', lowdbOptions);
    this.systemDBKey = {
      cache: 'cache'
    };
    this.customsDBKey = {
      bgdFileDir:'bgdFileDir',
    };
  }

  /*
   * 增 Test data
   */
  async updateBgdReportFileData(fileDir) {
    const data =  this.customsDB.db.set(this.customsDBKey.bgdFileDir, fileDir).write();
    return data;
  }

  /*
   * 查 Test data
   */
  async getBgdReportFileData() {
    let data = this.customsDB.db
    .get(this.customsDBKey.bgdFileDir)
    .value();

    if (_.isEmpty(data)) {
      data = ""
    }
    return data;
  }

  /*
   * all Test data
   */
  async getAllTestData() {
    const key = this.demoDBKey.test_data;
    if (!this.demoDB.db.has(key).value()) {
      this.demoDB.db.set(key, []).write();
    }
    let data = this.demoDB.db
    .get(key)
    .value();

    if (_.isEmpty(data)) {
      data = []
    }

    return data;
  }

  /*
   * 检查并创建表 (sqlite)
   */
  async checkAndCreateTableSqlite(tableName = '') {
    if (_.isEmpty(tableName)) {
      throw new Error(`table name is required`);
    }
    // 检查表是否存在
    const userTable = this.demoSqliteDB.db.prepare('SELECT * FROM sqlite_master WHERE type=? AND name = ?');
    const result = userTable.get('table', tableName);
    //console.log('result:', result);
    if (result) {
      return;
    }

    // 创建表
    const create_table_user =
    `CREATE TABLE ${tableName}
     (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name CHAR(50) NOT NULL,
        age INT
     );`
    this.demoSqliteDB.db.exec(create_table_user);

  }

  /*
   * 增 Test data (sqlite)
   */
  async addTestDataSqlite(data) {
    //console.log("add data:", data);

    let table = 'user';
    await this.checkAndCreateTableSqlite(table);

    const insert = this.demoSqliteDB.db.prepare(`INSERT INTO ${table} (name, age) VALUES (@name, @age)`);
    insert.run(data);

    return true;
  }

  /*
   * 删 Test data (sqlite)
   */
  async delTestDataSqlite(name = '') {
    //console.log("delete name:", name);

    let table = 'user';
    await this.checkAndCreateTableSqlite(table);

    const delUser = this.demoSqliteDB.db.prepare(`DELETE FROM ${table} WHERE name = ?`);
    delUser.run(name);

    return true;
  }

  /*
   * 改 Test data (sqlite)
   */
  async updateTestDataSqlite(name= '', age = 0) {
    //console.log("update :", {name, age});

    let table = 'user';
    await this.checkAndCreateTableSqlite(table);

    const updateUser = this.demoSqliteDB.db.prepare(`UPDATE ${table} SET age = ? WHERE name = ?`);
    updateUser.run(age, name);

    return true;
  }

  /*
   * 查 Test data (sqlite)
   */
  async getTestDataSqlite(age = 0) {
    //console.log("select :", {age});

    let table = 'user';
    await this.checkAndCreateTableSqlite(table);

    const selectUser = this.demoSqliteDB.db.prepare(`SELECT * FROM ${table} WHERE age = @age`);
    const users = selectUser.all({age: age});
    //console.log("select users:", users);
    return users;
  }

  /*
   * all Test data (sqlite)
   */
  async getAllTestDataSqlite() {
    //console.log("select all user");

    let table = 'user';
    await this.checkAndCreateTableSqlite(table);

    const selectAllUser = this.demoSqliteDB.db.prepare(`SELECT * FROM ${table} `);
    const allUser =  selectAllUser.all();
    //console.log("select allUser:", allUser);
    return allUser;
  }
}

StorageService.toString = () => '[class StorageService]';
module.exports = StorageService;
