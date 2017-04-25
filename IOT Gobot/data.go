package main

import (
    "bytes"
    "encoding/json"
    "net/http"
    "time"
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

// Push posts data to the IO server using an HTTP-JSON transport. Data is
// pushed once every half second to the IO server to meet the server's real-time
// requirement of one second.
func push(tag int, val string) string {
    b := bytes.NewBuffer(nil)
    j := json.NewEncoder(b)

    err := j.Encode(&Tag {
        ID:        tag,
        Device:    100,
        Area:      2000,
        Value:     val,
        Timestamp: time.Now(),
    })

    if err != nil { println(err.Error()); return "" }
    addr := "http://" + IOServerAddr + "/tag"
    res, err := http.Post(addr, "application/json; charset=utf-8", b)
    if err != nil { println(err.Error()); return "" }

    buf := make([]byte, res.ContentLength)
    res.Body.Read(buf)
    defer res.Body.Close()
    return string(buf)
}
