#!/usr/bin/env sh
set -e

: "${DB_URL:?DB_URL is required}"
: "${DB_USER:?DB_USER is required}"
: "${DB_PASS:?DB_PASS is required}"

mkdir -p /usr/local/tomcat/conf/Catalina/localhost

cat > /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml <<EOF
<Context>
    <Resource
        name="jdbc/InventoryDB"
        auth="Container"
        type="javax.sql.DataSource"
        maxTotal="20"
        maxIdle="10"
        maxWaitMillis="-1"
        username="${DB_USER}"
        password="${DB_PASS}"
        driverClassName="com.mysql.cj.jdbc.Driver"
        url="${DB_URL}" />
</Context>
EOF

exec catalina.sh run
