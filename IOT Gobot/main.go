package main

import (
	"fmt"
	"os"
	"strconv"
	"time"

	// go get -d -u gobot.io/x/gobot/...
	"gobot.io/x/gobot"
	"gobot.io/x/gobot/drivers/aio"
	"gobot.io/x/gobot/drivers/gpio"
	"gobot.io/x/gobot/platforms/intel-iot/edison"
)

// Configuration for the IO Server connection function.
var (
	IOServerAddr string
	TemptID  = 1
	LightID  = 2
	SoundID  = 3
	Override = false
)

// Simple SCADA example of device integration using an Intel Edison development
// board. Performs data collection with onboard sensors, and communicates with
// the prototype IO server for data filtering using HTTP-JSON as a transport.
func main() {
	if len(os.Args) < 2 { panic("Expecting argument for IO Server address") }
	IOServerAddr = os.Args[1]

	adp   := edison.NewAdaptor()
	tempt := aio.NewGroveTemperatureSensorDriver(adp, "0", time.Second / 2)
	light := aio.NewGroveLightSensorDriver(adp, "1", time.Second / 2)
	sound := aio.NewGroveSoundSensorDriver(adp, "2", time.Second / 2)

	bluled := gpio.NewLedDriver(adp, "3")
	redled := gpio.NewLedDriver(adp, "8")
	grnled := gpio.NewLedDriver(adp, "6")
	brdled := gpio.NewLedDriver(adp, "13")

	rob := gobot.NewRobot("device", []gobot.Connection{adp},
		[]gobot.Device{ tempt, light, sound, bluled, redled, grnled, brdled },
		func() {

			gobot.Every(time.Second / 2, func() {
				tempval := tempt.Temperature() * 1.8 + 32
				soundval, _ := sound.Read()
				lightval, _ := light.Read()
				fmt.Printf("Temp: %.2f F, Sound: %d, Light: %d\n",
					tempval, soundval, lightval)

				brdled.Toggle()
				if soundval > 30 { grnled.On() } else { grnled.Off() }
				if lightval < 10 { redled.On() } else { redled.Off() }
				if tempval > 78 && (soundval > 5 || lightval > 10) || Override {
					bluled.On() } else { bluled.Off() }

				r := push(TemptID, strconv.FormatFloat(tempval, 'f', 2, 64))
				if r == "toggle" {
					Override = !Override
					bluled.Toggle()
				}
				push(LightID, strconv.Itoa(lightval))
				push(SoundID, strconv.Itoa(soundval))
			})
		})

	rob.Start()
}
