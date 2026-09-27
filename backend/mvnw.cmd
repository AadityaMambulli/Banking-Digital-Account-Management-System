@echo off
@REM Maven Wrapper startup batch script

set ERROR_CODE=0

@REM set HOME directory of the user
if "%HOME%" == "" (
  set "HOME=%USERPROFILE%"
)

@REM set MAVEN_PROJECTBASEDIR
set "MAVEN_PROJECTBASEDIR=%~dp0"

@REM set MAVEN_WRAPPER_JAR
set "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"

@REM Execute Maven with multiModuleProjectDirectory system property (quoted)
java "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%." -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
if ERRORLEVEL 1 set ERROR_CODE=1

exit /B %ERROR_CODE%
