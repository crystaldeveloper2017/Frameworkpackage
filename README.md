### SKPSecurityGateV2
----------------------------------------------------------------------
Steps to Setup Development Environment
-----------------------------------
-----------------------------------

git clone --recurse-submodules https://github.com/crystaldeveloper2017/SKPSecurityGateV2

Change the Credentials from the `Config.yaml` File.

```
mysqlusername : ""
password: ""
host: "localhost"
port: "3306"
mySqlPath: "1"
schemaName: skpsecuritygate
projectName: skpsecuritygate maven
thread_sleep: 0
isAuditEnabled: "true"
copyAttachmentsToBuffer: "false"
persistentPath: "/home/ubuntu/skpsecuritygate_attachments/"
queryLogEnabled: "false"
sendEmail: "false"
```
and run it.. Once it runs, Try to open

http://localhost:8080

