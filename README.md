# Fibered Date Server - Using Project Loom

A simple, super scalable date server implemented using Java fibers ([Project Loom](https://wiki.openjdk.java.net/display/loom/Main))

# Setup
Set the **JAVA_HOME** variable to point to one of the Project Loom [builds](http://jdk.java.net/loom/).


# Run

```./mvnw install```

This command will start a date server on the port **59059**

# Test
```nc localhost 59059```
