@echo off
mvn install:install-file -Dfile="artemis-http-client-1.1.11.RELEASE.jar" -DgroupId="com.hikvision.ga" -DartifactId="artemis-http-client" -Dversion="1.1.11" -Dpackaging=jar && ^
mvn install:install-file -Dfile="artemis-http-client-1.1.11.RELEASE-sources.jar" -DgroupId="com.hikvision.ga" -DartifactId="artemis-http-client" -Dversion="1.1.11" -Dpackaging=jar -Dclassifier=sources && ^
mvn install:install-file -Dfile="artemis-http-client-1.1.11.RELEASE-javadoc.jar" -DgroupId="com.hikvision.ga" -DartifactId="artemis-http-client" -Dversion="1.1.11" -Dpackaging=jar -Dclassifier=javadoc