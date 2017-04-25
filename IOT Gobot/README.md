# Simple SCADA IOT Prototype

## Build Instructions
Note that dispite Gobot's stability, it is a very poorly managed framework and undergoes changes constantly. Since I last worked on this, the entire framework changed and all calls needed to be rewritten. Please check documentation before building this project: https://gobot.io/documentation/core/api/.

Run the following commands in order, and replace the F: drive with the mounted drive of the Intel Edison's onboard flash storage. If the build fails due to changes to the Gobot API, fix code and rebuild with "go install gitlab.com/wrry/scada-iot".

```
SET GOPATH=F:\scada\
SET GOOS=linux
SET GOARCH=386
go get -u -v gitlab.com/wrry/scada-iot
```

## Run Instructions
To run the program, download and configure the Intel Edison board's Wi-Fi using this utility: https://software.intel.com/en-us/iot/hardware/edison/downloads. SSH into the device and mount the flash storage. Ensure the clock is synchronized with internet time (rdate wwv.nist.gov && date). Then, run the program using the following commands (replacing the IP Address 1.1.1.1 with the Amazon EC2 instance's public IP address for the IO Server):

```
rdate wwv.nist.gov && date
cd /mnt
mkdir flash
mount -o offset=8192 /dev/mmcblk0p9 flash
cd /mnt/flash/scada/bin/linux_386
./scada-iot 1.1.1.1
```

Once the program has finished running, unmount and delete the mount point using the commands below.

```
cd /mnt
umount flash
rm -r /mnt/flash
```

To run the program without a SSH session alive, use the command "./scada-iot & disown".

## Default Configuration
IDs: 1 = temperature, 2 = light, 3 = sound.

Device ID: 100, Area ID: 1001

Green LED illuminates for sound over 30db.

Blue LED illuminates for temperature over 78F.

Red LED illuminates for light under 10 intensity.

Air conditioning (Blue LED) only turns on if light is above 10 intensity or sound is above 5db, and temperature is above 78F.

## Pictures
![Real Implementation](http://i.imgur.com/Bo4w2Eh.jpg)

![Diagram](http://i.imgur.com/WQMg8pX.png)
