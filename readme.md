# 科学计算器

## 问题描述

此科学计算器具有以下功能：

1. 科学基础运算功能;
2. 带括号的运算;
3. 存储功能;
4. 适当的异常处理.
5. 部分高级函数.
6. 运算过程即时显示.

## 代码描述

实验代码使用Java写成.

### Class

```
Calculator.class
```

Main class.

```
Calculate.class
```

主要计算类，在ArithHelper的辅助下将字符串转换为表达式并输出计算结果

```
ArthHelper.class
```

辅助计算，得到更精确的计算值

## 运行环境

JAVA 8 及以上

## 运行方式

​	点击 MyCalculator.jar 并运行.

​	使用计算器.

​	点击中部操作按钮栏中的按钮实现存储、回删和清除，存储空间中有内容时显示框左下角出现“M”图标.

​    点击 运算按键左边`+/-`按钮显示第一页运算符和数字按钮，点击左边 `sin`按钮显示第二页运算符按钮.