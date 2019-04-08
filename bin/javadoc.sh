#/bin/bash
# 请在 ycy-intellij-plugin 项目的根目录下执行脚本

JAVADOC_HOME='./javadoc'

if [ -d $JAVADOC_HOME ]; then
    rm -rf $JAVADOC_HOME
    echo "rm -rf $JAVADOC_HOME"
fi

javadoc -sourcepath './src/main/java' -d './javadoc' -charset 'utf-8' -private -author -version -subpackages 'cn.fantasticmao.ycy'
