#!/usr/bin/env bash

USERNAME="nizami@inbox.lv"
PASS="NBBkKopYTJteONrd"

ENCRYPTED=`echo -ne "$USERNAME:$PASS" | base64`

curl -v 'http://localhost:8080/api/account/' \
 -H "Authorization: Basic $ENCRYPTED"
#
# -H 'Accept: application/json, text/plain, */*' \
# -H 'Connection: keep-alive' --compressed
