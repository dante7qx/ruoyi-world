#! /bin/bash

curProject="project"
curProjectCN="睿阳Java开发框架"
projectName=$1
projectCN="XX管理系统"
modifys=()

createNewProject() {
    if [[ -z $projectName ]]; then
        echo "Usage: bash new_project.sh <你的项目名（英文）>"
        exit 1
    else 
        ## 修改pom.xml
        poms=`find ./ -type f -name "pom.xml"`
        for pom in $poms; do 
            sed -i '' "s/<artifactId>${curProject}<\/artifactId>/<artifactId>${projectName}<\/artifactId>/g" $pom
            sed -i '' "s/${curProject}-/${projectName}-/g" $pom
        done
        
        ## 修改睿阳Java开发框架
        cnfiles=`grep ${curProjectCN} -rl ./ | grep -v new_project.sh | grep -v new_project_mac.sh | grep -v git`
        for cnfile in $cnfiles; do
            sed -i '' "s/${curProjectCN}/${projectCN}/g" $cnfile
        done

        ## 重命名目录
        projectDirs=`find ./ -type d -name "${curProject}-*"`
        for pdir in $projectDirs; do
            echo "mv ${pdir} ${projectName}${pdir#*${curProject}}"
            mv ${pdir} ${projectName}${pdir#*${curProject}}
        done
        
        ## 删除git相关
        rm -rf .git/
        rm -rf .gitignore
        
        echo "1. 修改pom.xml"
        echo $poms
        echo "2. 修改${curProjectCN}"
        echo $cnfiles
        echo "3. 重命名目录"
        echo $projectDirs

    fi
}

createNewProject
