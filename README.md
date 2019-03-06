# Docker-springboot-test
在docker中运行springboot项目,并连接mysql redis测试
---
完成本操作的前提:<br>
 >(1): [Docker Desktop for Windows](https://hub.docker.com/editions/community/docker-ce-desktop-windows)(官网下载较慢)<br>
 (2): 在docker中获取并[运行mysql](https://github.com/pjqdyd/Docker-springboot-test/blob/master/guideTxt/docker-mysql)和[运行redis](https://github.com/pjqdyd/Docker-springboot-test/blob/master/guideTxt/docker-redis) (最新mysql 80版本,redis 5.0.3)<br>
 (3): docker下载好java:8的镜像(命令`docker pull java:8`)<br>
 (4): 连接容器中的mysql,并建一个数据库名为test, 用于连接测试 (如果想了解如何在启动mysql容器的同时执行sql建表请访问[我的简书](https://www.jianshu.com/p/1e4e465acb84))<br>
    
1. 使用maven创建一个springboot的项目,并引入数据库相关操作的依赖,

2. 在[application.yml](https://github.com/pjqdyd/Docker-springboot-test/blob/master/src/main/resources/application.yml)中配置好数据源的连接: <br>
      mysql连接的ip是在docker中mysql容器的ip, 查看方式运行命令: 
      `docker inspect [mysql容器名]`
     redis的ip配置也是同理<br><br>
```
    (注意 |与本操作无关可忽略): 虽然我在启动mysql和redis容器的同时,配置了映射我的容器端口到宿主机端口, 并在宿主机是可以通过
    127.0.0.1:映射端口  连接到数据库的.  如果在springboot项目的application.yml中配置的路径是127.0.0.1:映射端口,那么在
    宿主机启动项目是可以连接到容器数据库的, 但将项目打包放进docker容器中启动是连接不到数据库的, 因为在spring boot的docker容
    器中的127.0.0.1是指向本身的,无法连接到其他容器, (官网说启动使用--link可以连接其他容器,但我试的不行,(容器编排先不考虑)),
    因为docker容器启动的默认连接方式是桥接, 所以我启动的容器都默认以 bridge桥接的方式连接,查看网络: `docker network ls` 
    查看桥接网络: `docker inspect bridge`, 可以看到我们启动的容器都在桥接网络中)<br>
```
3. 在项目目录下运行 `mvn clean package -DskipTests` 将项目打包成jar包,-DskipTests表示不运行测试.

4. 创建Dockerfile文件,用于构建镜像,内容如下[Dockerfile](https://github.com/pjqdyd/Docker-springboot-test/blob/master/docker/Dockerfile)

5. 将打好的项目docker-test-0.0.1-SNAPSHOT.jar 包放在与Dockerfile文件同一目录,
   并在该同一目录运行<br> `docker build -t spring-boot-docker .` 构建镜像 ,注意最后" . "不能少, 点表示Dockerfile文件在当前目录,

6. 查看镜像:`docker images` 可以看到我们构建好的镜像spring-boot-docker

7. 启动项目镜像:`docker run -d --name springboot -p 8081:8080 spring-boot-docker`

8. 查看容器进程:`docker ps`  查看spring项目的运行日志: `docker logs springboot`

9. 打开浏览器访问 localhost:8081/index.html 就可以进行测试连接了...

11. 停止项目容器: `docker stop springboot` 删除项目容器: `docker rm springboot` 删除镜像: `docker rmi spring-boot-docker`
    
