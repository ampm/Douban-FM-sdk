Douban FM for Android in TDD
==================
#API

自己httpscoop抓包整理的api在此
[DoubanAPI](https://github.com/ampm/DoubanFM-android-TDD/blob/master/DoubanAPI.md)

自己用到的模拟登录工具：chrome extension：Advanced Rest Client
#1折腾第一步

my-android-project based on Macen-android-plugin and Stand and Robolectric

我的Android开发实践<http://www.zuozuixihuandeziji.com/2013/06/26/android-skeleton/>

构建 测试 打包 参见:ANDROID-RELEASE ARCHETYPE
<http://stand.spree.de/wiki_details_maven_archetypes>
#折腾第二步
##准备工作
1. 安装android maven依赖到本地：
    <https://github.com/mosabua/maven-android-sdk-deployer>
2. 使用stand新建项目 

    <http://stand.spree.de/wiki_details_maven_archetypes>

Mention:

* issues：<https://github.com/akquinet/android-archetypes/issues/30>
        更改pom中的 `<injar>android-classes</injar>`为`<injar>classes</injar>`
* settings.xml
        
            <activeByDefault>false</activeByDefault>

3. IDE中的SDK依赖：
    
        <platform.version>4.1.1.4</platform.version>
4. 如有需要请修改android-maven-plugin platform->android-SDK版本，默认2.3.3<10>

                <plugin>
                        <groupId>com.jayway.maven.plugins.android.generation2</groupId>
                        <artifactId>android-maven-plugin</artifactId>
                        <version>3.5.0</version>
                        <inherited>true</inherited>
                        <configuration>
                            <androidManifestFile>${project.basedir}/AndroidManifest.xml</androidManifestFile>
                            <assetsDirectory>${project.basedir}/assets</assetsDirectory>
                            <resourceDirectory>${project.basedir}/res</resourceDirectory>
                            <nativeLibrariesDirectory>${project.basedir}/src/main/native</nativeLibrariesDirectory>
                            <sdk>
                                <platform>16</platform>
                            </sdk>
                            <undeployBeforeDeploy>true</undeployBeforeDeploy>
                        </configuration>
                    </plugin>

5. project中新建UnitTest目录：src/test,如有需要可以创建自己的archetype
##开发
1. 添加robolectric

    <dependency>
            <groupId>org.robolectric</groupId>
            <artifactId>robolectric</artifactId>
            <version>${robolectric.version}</version>
            <scope>test</scope>
    </dependency>

2. 写UT测试
3. 执行测试
    
        mvn clean test

4. 测试通过、重构

###BP
需求完全按照豆瓣官方TV版，这样相当于已经做好BP了
###开发过程
由前端到后端，比如

1. 测界面元素的初始状态。
2. 更改界面状态
    - 更改状态需要依赖api请求返回结果，那就接着写api：req<->resp 的测试
    - 测试req
    - 测试resp

> 20131027：总结
总之需要用到什么就先写测试

目前为止，节奏还是比较混乱:现有的开发经验和习惯，会不由自主的去停不下来写一大堆

当出现此状态，马上停下来，不然没法享受干一件踏实意见的安全感。

明日目标，整理代码，理清思路，压住旧习惯。

>20131028

这是个需求十分明确的case，但现实多半是变化多端的。把coolshell上关于TDD的思考又拎出来看看<http://coolshell.cn/tag/tdd>

我用TDD的目的：把TDD当做锻炼模块化代码整洁的一种手段，实际中。。。

如果是客户或者公司的项目，特别是那种缺乏测试混乱的“大”项目，着实让人不自信。

但是写自己的小玩具，没有太多压力，所以对自己代码也比较熟知，自信是不缺少的。
>TDD从来没说什么都要测
看wm同学的刚开始的coch，又一想就是要学会找测试条件，比如 有人说“如果我们需要测试一个像 int square(int x) 这样的开根函数，我们需要40亿个测试（每个数都要测试）。”。。。

我用tdd的目的

1. 把事情分解
2. 定位问题
3. 不适合tdd的就不用
4. 设计、架构永远第一  实现前心里有谱
5. 另一篇激烈的讨论
6. TDD是一种新的体验
7. 我不迷信和脑残粉。我只汲取感觉让自己爽的东西

http://www.quora.com/J-B-Rainsberger/answers/Test-Driven-Development

http://programmers.stackexchange.com/questions/41409/why-does-tdd-work

>20131029

逐渐习惯先测试，mock未实现的依赖，pass，重构，实现mock这样的循环过程

慢慢的能控制住写代码的冲动，一个粒度 一个粒度的完成

初期，借住intellij的测试覆盖度报告，可以看出哪些没测，哪些行没测到。以后对那些该测也越来越明确了。

>20131030

AuthGateway ut完毕，考虑功能测试
老赵的一篇倍儿纠结的文章，好有同感<http://blog.zhaojie.me/2009/10/testability-driven-development-1.html>


>20131104

login基础API UT完毕，开始在ui层利用login api 并做单元测试


#重构

结合Intellij 自带的refactor工具

http://sourcemaking.com/refactoring

《Clean code》

《Refactor》



#开发日志
##Tools：

- Log简化:ref:http://blog.chengyunfeng.com/?p=534

        添加submodule:https://github.com/ANDLABS-Git/AndlabsAndroidUtils.git
        submodules 使用ref:http://www.kafeitu.me/git/2012/03/27/git-submodule.html
        issue:混淆后的log执行所在的class不能直接看：
        workaround：http://blog.chengyunfeng.com/?p=545
- View DI

        butterknife://github.com/JakeWharton/butterknife.git
        IDEA 插件：http://plugins.jetbrains.com/plugin/7369
        Intellij-android-codegenerator:https://github.com/kurganec/intellij-android-codegenerator/

- ImageView


