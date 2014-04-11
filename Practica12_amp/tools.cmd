cls
cd %~dp0
E:
cd src
java -cp ..\tools\jflex\JFlex.jar JFlex.Main -d lexico lexico\lexico.flex
pause
cd..
cd tools\byaccj
yacc -J -v -Jpackage=sintactico -Jsemantic=Object ..\..\src\sintactico\sinj.y
move Parser.java ..\..\src\sintactico
move y.output ..\..\src\sintactico
pause