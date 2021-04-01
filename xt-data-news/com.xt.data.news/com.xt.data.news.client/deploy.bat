cd ./
mvn install

mvn deploy:deploy-file -DgroupId=com.xt -DartifactId=com.xt.message.center.client.core -Dversion=0.0.1 -Dfile=com.xt.message.center.client.core/target/com.xt.message.center.client.core-0.0.1.jar -Dpackaging=jar -Durl=http://192.168.2.5:8081/repository/xt-hosted/ -DrepositoryId=xt-hosted
mvn deploy:deploy-file -DgroupId=com.xt -DartifactId=com.xt.message.center.client.http -Dversion=0.0.1 -Dfile=com.xt.message.center.client.http/target/com.xt.message.center.client.http-0.0.1.jar -Dpackaging=jar -Durl=http://192.168.2.5:8081/repository/xt-hosted/ -DrepositoryId=xt-hosted
mvn deploy:deploy-file -DgroupId=com.xt -DartifactId=com.xt.message.center.client.mq -Dversion=0.0.1 -Dfile=com.xt.message.center.client.mq/target/com.xt.message.center.client.mq-0.0.1.jar -Dpackaging=jar -Durl=http://192.168.2.5:8081/repository/xt-hosted/ -DrepositoryId=xt-hosted
