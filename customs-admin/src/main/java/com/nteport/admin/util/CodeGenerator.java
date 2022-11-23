package com.nteport.admin.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://172.16.22.79:3306/nteport_rm?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                        "nteport_rm",
                        "dzka1234")
                .globalConfig(builder -> builder.author("Yang, Boatao")
                        //开启swagger模式
                        .enableSwagger()
                        //覆盖已生成文件
                        //.fileOverride()
                        .outputDir("e://workspace//nteport_oa//oa_admin//src//main//java//"))
                //设置父包名
                .packageConfig(builder -> builder.parent("com.nteport.admin")
                        //设置父包模块名
                        //.moduleName("system")
                        //设置mapperXml生成路径
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "e://workspace//nteport_oa//oa_admin//src//main//resources//mapper")))
                //设置表名
                .strategyConfig(builder -> builder.addInclude("t_menu_item_permission")
                        //设置过滤表前缀
                        //.addTablePrefix("t_", "c_"))
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                //执行
                .execute();
    }

}