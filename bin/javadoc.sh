#/bin/bash
# 请在项目根目录下执行脚本
rm -rf './javadoc'
javadoc -sourcepath './src/main/java' -d './javadoc' -charset 'utf-8' -private -author -version -subpackages 'cn.fantasticmao.ycy'
