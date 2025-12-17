#!/usr/bin/env sh
set -e

: "${DB_URL:?DB_URL is required}"
: "${DB_USER:?DB_USER is required}"
: "${DB_PASS:?DB_PASS is required}"

# XML-escape values (handles &, <, >, ", ')
xml_escape() {
  printf '%s' "$1" | sed \
    -e 's/&/\&amp;/g' \
    -e 's/</\&lt;/g' \
    -e 's/>/\&gt;/g' \
    -e 's/"/\&quot;/g' \
    -e "s/'/\&apos;/g"
}

DB_URL_ESCAPED="$(xml_escape "$DB_URL")"
DB_USER_ESCAPED="$(xml_escape "$DB_USER")"
DB_PASS_ESCAPED="$(xml_escape "$DB_PASS")"

mkdir -p /usr/local/tomcat/conf/Catalina/localhost

cat > /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml <<EOF
<Context>
  <Resource
      name="jdbc/InventoryDB"
      auth="Container"
      type="javax.sql.DataSource"
      factory="org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory"
      maxTotal="20"
      maxIdle="10"
      maxWaitMillis="-1"
      username="${DB_USER_ESCAPED}"
      password="${DB_PASS_ESCAPED}"
      driverClassName="com.mysql.cj.jdbc.Driver"
      url="${DB_URL_ESCAPED}" />
</Context>
EOF

exec catalina.sh run
