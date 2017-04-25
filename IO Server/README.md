# Simple SCADA IO Server Prototype

## Build Instructions
Building this project only requires a golang compiler. Run the following command:

```
export PATH=$PATH:/usr/local/go/bin
export GOPATH=$HOME/gopath
export PATH=$PATH:$GOPATH/bin
go get -u -v gitlab.com/wrry/scada-io
```

## Run Instructions
Run the server using the Application Server's public IP address as a first argument. After starting the IO Server, start the IoT device program using the IO Server's IP address as a first argument. After configuring and starting both programs, tags should now be filtered and sent to the application server as delta values. Replace 2.2.2.2 with the IP address of the Amazon EC2 instance hosting the application server.

```
scada-io 2.2.2.2
```
