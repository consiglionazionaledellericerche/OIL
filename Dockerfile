FROM jboss/wildfly:10.1.0.Final

COPY ./docker/src/standalone/configuration/standalone-full.xml /opt/jboss/wildfly/standalone/configuration/
COPY ./docker/src/modules/system/layers/base/sun/jdk/main/module.xml /opt/jboss/wildfly/modules/system/layers/base/sun/jdk/main/
COPY ./docker/src/modules/org/postgresql/postgresql/main/postgresql-9.2-1004.jdbc41.jar /opt/jboss/wildfly/modules/org/postgresql/postgresql/main/
COPY ./docker/src/modules/org/postgresql/postgresql/main/module.xml /opt/jboss/wildfly/modules/org/postgresql/postgresql/main/

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--server-config=standalone-full.xml"]

COPY helpdesk/target/*.ear /opt/jboss/wildfly/standalone/deployments/hd.ear
