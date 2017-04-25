package main

import (
    "net/http"
)

// Action receives a supervisory command from the application server to control
// a sensor or control on a specified device. The action is forwarded to the
// device on the network for action processing. Trasmitted over HTTP-JSON.
func action(res http.ResponseWriter, req *http.Request) {
    // This is a system integration definition. For this demonstration, this
    // action command handler will only toggle the AC LED on the Edison board.
    ActionStore[2101] = "toggle"
}
