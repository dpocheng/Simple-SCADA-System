package main

import (
    "bytes"
    "encoding/json"
    "fmt"
    "net/http"
    "time"
)

// Temporary storage of latest tag values and configuration for sending tags.
var (
    TagStore    = make(map[int]string)
    ActionStore = make(map[int]string)
    AppServerAddr string
)

// Tag represents a sensor or data device input from the plant floor. This
// is the final expected structure for tags defined by the prototype design
// document under section "Database". This is also the transport structure.
type Tag struct {
    ID        int       `json:"id"`
    Device    int       `json:"device"`
    Area      int       `json:"area"`
    Value     string    `json:"val"`
    Timestamp time.Time `json:"time"`
}

// Receive accepts data from the devices, determines if the data is a delta
// value that should be sent to the application server, and transmits changes
// upwards to the application server using HTTP-JSON as a trasport.
func receive(res http.ResponseWriter, req *http.Request) {
    t := new(Tag)
    d := json.NewDecoder(req.Body)
    err := d.Decode(t)
    if err != nil { println(err.Error()) }
    defer req.Body.Close()

    // Determine if delta value has changed.
    compositeid := t.Area + t.Device + t.ID
    val, ok := TagStore[compositeid]
    if !ok || val != t.Value {

        TagStore[compositeid] = t.Value
        b := bytes.NewBuffer(nil)
        j := json.NewEncoder(b)
        err := j.Encode(t)
        if err != nil { println(err.Error()) }

        addr := "http://" + AppServerAddr + "/tag"
        fmt.Println(t)
        response, err := http.Post(addr, "application/json; charset=utf-8", b)
        if err != nil { println(err.Error()) }
        defer response.Body.Close()
    }

    // Determine if action needs to be sent back.
    action, ok := ActionStore[compositeid]
    if ok {
        println(action)
        res.Write([]byte(action))
        defer delete(ActionStore, compositeid)
    }
}
