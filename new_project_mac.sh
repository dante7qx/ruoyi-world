#! /bin/bash

curProject="project"
curProjectCN="睿阳Java开发框架"
projectName="xx-sys"
projectCN="XX管理系统"
modifys=()

createNewProject() {
    ## 修改pom.xml
    echo "1. 修改pom.xml"
    poms=`find ./ -type f -name "pom.xml"`
    for pom in $poms; do 
    	echo $pom
        sed -i '' "s/<artifactId>${curProject}<\/artifactId>/<artifactId>${projectName}<\/artifactId>/g" $pom
        sed -i '' "s/${curProject}-/${projectName}-/g" $pom
    done
    
    ## 修改睿阳Java开发框架
    echo "2. 修改${curProjectCN}"
    cnfiles=`grep ${curProjectCN} -rl ./ | grep -v new_project.sh | grep -v new_project_mac.sh | grep -v git`
    for cnfile in $cnfiles; do
        echo $cnfile
        sed -i '' "s/${curProjectCN}/${projectCN}/g" $cnfile
    done

    ## 重命名目录
    echo "3. 重命名目录"
    projectDirs=`find ./ -type d -name "${curProject}-*"`
    for pdir in $projectDirs; do
        echo "mv ${pdir} ${projectName}${pdir#*${curProject}}"
        mv ${pdir} ${projectName}${pdir#*${curProject}}
    done
    
    ## 删除git相关
    rm -rf .git/
    rm -rf .gitignore
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