#!/bin/bash

VERSION=1.1

rm -rf checkout
mkdir -p checkout
cd checkout
git clone git@github.com:relops/snowflake.git
cd snowflake
git checkout $VERSION
mvn clean javadoc:jar source:jar install

URL="https://oss.sonatype.org/service/local/staging/deploy/maven2/"
REPO="sonatype-nexus-staging"

mvn gpg:sign-and-deploy-file -Dgpg.passphrase=xxxxxxx -Durl=$URL -DrepositoryId=$REPO -DpomFile=pom.xml -Dfile=target/snowflake-$VERSION.jar
mvn gpg:sign-and-deploy-file -Dgpg.passphrase=xxxxxxx -Durl=$URL -DrepositoryId=$REPO -DpomFile=pom.xml -Dfile=target/snowflake-$VERSION-sources.jar -Dclassifier=sources
mvn gpg:sign-and-deploy-file -Dgpg.passphrase=xxxxxxx -Durl=$URL -DrepositoryId=$REPO -DpomFile=pom.xml -Dfile=target/snowflake-$VERSION-javadoc.jar -Dclassifier=javadoc
