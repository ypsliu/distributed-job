#!/bin/sh
nohup java -jar -DAPP_HOME=/rc/djob-scheduler -Denv=bbx \
/rc/djob-scheduler/distributed-job.jar >/dev/null 2>&1 &