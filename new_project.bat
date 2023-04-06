$curProject = "project"
$curProjectCN = "睿阳RSP开发框架"
$projectName = "xx-sys"
$projectCN = "XX管理系统"

function createNewProject {
    Write-Host "1. 修改pom.xml"
    $poms = Get-ChildItem -Path ./ -Recurse -File -Filter "pom.xml"
    foreach ($pom in $poms) {
        Write-Host $pom.FullName
        (Get-Content $pom.FullName) | ForEach-Object { $_ -replace "<artifactId>$curProject</artifactId>", "<artifactId>$projectName</artifactId>" } | Set-Content $pom.FullName
        (Get-Content $pom.FullName) | ForEach-Object { $_ -replace "${curProject}-", "${projectName}-" } | Set-Content $pom.FullName
    }

    Write-Host "2. 修改$curProjectCN"
    $cnfiles = Get-ChildItem -Path ./ -Recurse -File | Where-Object { $_.FullName -notmatch "new_project.sh|new_project_mac.sh|git" -and $_ | Select-String -Pattern $curProjectCN }
    foreach ($cnfile in $cnfiles) {
        Write-Host $cnfile.FullName
        (Get-Content $cnfile.FullName) | ForEach-Object { $_ -replace $curProjectCN, $projectCN } | Set-Content $cnfile.FullName
    }

    (Get-Content ./project-admin/src/main/java/com/risun/RisunApplication.java) | ForEach-Object { $_ -replace "XX项目", $projectCN } | Set-Content ./project-admin/src/main/java/com/risun/RisunApplication.java
    Write-Host "修改常量 Common 下 Constants.java"
    (Get-Content ./project-common/src/main/java/com/risun/common/constant/Constants.java) | ForEach-Object { $_ -replace "project-", "${projectName}-" } | Set-Content ./project-common/src/main/java/com/risun/common/constant/Constants.java
    (Get-Content ./project-common/src/main/java/com/risun/common/constant/CacheConstants.java) | ForEach-Object { $_ -replace "project", $projectName } | Set-Content ./project-common/src/main/java/com/risun/common/constant/CacheConstants.java
    (Get-Content ./project-common/src/main/java/com/risun/common/constant/CacheConstants.java) | ForEach-Object { $_ -replace "-", "_" } | Set-Content ./project-common/src/main/java/com/risun/common/constant/CacheConstants.java
    (Get-Content ./project-admin/src/main/resources/application.yml) | ForEach-Object { $_ -replace "192.168.1.30", "127.0.0.1" } | Set-Content ./project-admin/src/main/resources/application.yml
    (Get-Content ./project-admin/src/main/resources/application.yml) | ForEach-Object { $_ -replace ": files", "<文件上传目录>" } | Set-Content ./project-admin/src/main/resources/application.yml
    (Get-Content ./project-admin/src/main/resources/application-druid.yml) | ForEach-Object { $_ -replace "192.168.1.30:3306", "<数据库url>" } | Set-Content ./project-admin/src/main/resources/application-druid.yml
	(Get-Content ./project-admin/src/main/resources/application-druid.yml) | ForEach-Object { $_ -replace "risun_project", "<数据库>" } | Set-Content ./project-admin/src/main/resources/application-druid.yml
	(Get-Content ./project-admin/src/main/resources/application-druid.yml) | ForEach-Object { $_ -replace "root", "<数据库账号>" } | Set-Content ./project-admin/src/main/resources/application-druid.yml
	(Get-Content ./project-admin/src/main/resources/application-druid.yml) | ForEach-Object { $_ -replace "iamdante", "<数据库密码>" } | Set-Content ./project-admin/src/main/resources/application-druid.yml
	
	echo "3. 重命名目录"
	
	$projectDirs = Get-ChildItem -Path .\ -Directory | Where-Object { $_.Name -like "${curProject}-*" }
	
	foreach ($pdir in $projectDirs) {
	    $newName = $projectName + $pdir.Name.Substring($curProject.Length)
	    Write-Host "Rename-Item -Path $($pdir.FullName) -NewName $newName"
	    Rename-Item -Path $pdir.FullName -NewName $newName
	}
	
	Remove-Item -Path .\.git -Force -Recurse
}

$input = Read-Host -Prompt "您确定要执行此操作吗? [Y/n] "

switch ($input) {
    "yes" -or "Yes" -or "YES" -or "y" -or "Y" {
        createNewProject
        break
    }

    "no" -or "No" -or "NO" -or "n" -or "N" {
        Write-Host "不执行"
        exit 1
        break
    }

    default {
        Write-Host "Invalid input..."
        exit 1
        break
    }
}