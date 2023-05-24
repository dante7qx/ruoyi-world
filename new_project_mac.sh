#! /bin/bash

curProject="project"
curProjectCN="睿阳RSP开发框架"
projectName="xx-sys"
projectCN="XX管理系统"

createNewProject() {
    ## 修改pom.xml
    echo "1. 修改pom.xml"
    poms=`find ./ -type f -name "pom.xml"`
    for pom in $poms; do 
    	echo $pom
        sed -i '' "s/<artifactId>${curProject}<\/artifactId>/<artifactId>${projectName}<\/artifactId>/g" $pom
        sed -i '' "s/${curProject}-/${projectName}-/g" $pom
    done
    
    ## 修改睿阳RSP开发框架
    echo "2. 修改${curProjectCN}"
    cnfiles=`grep ${curProjectCN} -rl ./ | grep -v new_project.sh | grep -v new_project_mac.sh | grep -v git`
    for cnfile in $cnfiles; do
        echo $cnfile
        sed -i '' "s/${curProjectCN}/${projectCN}/g" $cnfile
    done
    
    sed -i '' "s/XX项目/${projectCN}/g" ./project-admin/src/main/java/com/risun/RisunApplication.java
    echo "修改常量 Common 下 Constants.java"
    sed -i '' "s/project-/${projectName}-/g" ./project-common/src/main/java/com/risun/common/constant/Constants.java
    sed -i '' "s/project/${projectName}/g" ./project-common/src/main/java/com/risun/common/constant/CacheConstants.java
    sed -i '' "s/-/_/g" ./project-common/src/main/java/com/risun/common/constant/CacheConstants.java
    sed -i '' "s/192.168.1.30/127.0.0.1/g" ./project-admin/src/main/resources/application.yml
    sed -i '' "s/: files/: <文件上传目录>/g" ./project-admin/src/main/resources/application.yml
    sed -i '' "s/192.168.1.30:3306/<数据库url>/g" ./project-admin/src/main/resources/application-mysql.yml
    sed -i '' "s/risun_project/<数据库>/g" ./project-admin/src/main/resources/application-mysql.yml
    sed -i '' "s/root/<数据库账号>/g" ./project-admin/src/main/resources/application-mysql.yml
    sed -i '' "s/iamdante/<数据库密码>/g" ./project-admin/src/main/resources/application-mysql.yml
    sed -i '' "s/risun-rsp/${projectName}/g" ./sonar-project.properties
    sed -i '' "s/project-/${projectName}-/g" ./sonar-project.properties

    ## 重命名目录
    echo "3. 重命名目录"
    projectDirs=`find ./ -type d -name "${curProject}-*"`
    for pdir in $projectDirs; do
        echo "mv ${pdir} ${projectName}${pdir#*${curProject}}"
        mv ${pdir} ${projectName}${pdir#*${curProject}}
    done
    
    
    
    
    ## 删除git相关
    rm -rf .git/
    ## rm -rf .gitignore
}

read -r -p "您确定要执行此操作吗? [Y/n] " input

case $input in
    [yY][eE][sS]|[yY])
		createNewProject
		;;

    [nN][oO]|[nN])
		echo "不执行"
		exit 1
       	;;

    *)
		echo "Invalid input..."
		exit 1
		;;
esac