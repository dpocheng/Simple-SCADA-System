package main

import (
    "log"
    "net/http"
    "os"
)

// Simple SCADA example of an IO Server for devices on the network. Performs
// data filtering of incomming tags from device sensors, and communicates with
// the prototype application server for data storage using HTTP-JSON as a
// transport.
func main() {
    if len(os.Args) < 2 { panic("Expecting argument for App Server address") }
	AppServerAddr = os.Args[1]

    http.HandleFunc("/tag", receive)
    http.HandleFunc("/action", action)
    log.Fatal(http.ListenAndServe("0.0.0.0:80", nil))
}
