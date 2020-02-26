# ktimber

KTimber is Kotlin logging library based on [JakeWharton's Timber library] that brings some extra functionality:
  - A built in file logger that formats the logs and writes them to a HTML file
  - The posibility to easily share the logs file

You can also:
  - Use the built in Trees for Release and Debug modes
  - View and delete log files directly

### Usage

Init the logger before using it (ideally at the app level):

If you want to save logs to file:
```sh
$ KTimber.startWithFileLogger(context)
```
else 
```sh
$ KTimber.start(context, minimumLoggingLevel)
```

To log an event use any of these functions:
- logInfo()
- logVerbose()
- logDebug()
- logWarn()
- logError()
- logAssert()

To share/open/delete the logs file:
  - shareLogsFile(activity: AppCompatActivity, emailAddress: String)
  - deleteLogsFile(context: Context)
  - openLogsFile(context: Context)
  
Or you can use the extensions:
  - AppCompatActivity.shareLogsFile(emailAddress: String)
  - AppCompatActivity.deleteLogsFile()
  - AppCompatActivity.openLogsFile()

License
----

  Apache Version 2.0



   [JakeWharton's Timber library]: <https://github.com/JakeWharton/timber>
