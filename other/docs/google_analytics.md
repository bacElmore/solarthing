# Google Analytics
SolarThing uses Google Analytics to get usage data from users and is **enabled by default**. 

It'd be awesome if you kept it enabled. It's nice for me to be able to see about how many people are using SolarThing
at a single point in time. I understand if you'd like to opt out.

## Opt Out
To opt out, add `"analytics_enabled": false` to your `config/base.json` (with a comma afterwards if necessary). Once you opt out, no data will be sent to Google.

To opt out, you can also set the `ANALYTICS_DISABLED` environment variable. (Run `export ANALYTICS_DISABLED=`). 
NOTE: **This is temporary** unless you make sure this environment variable gets set before running SolarThing. (Only works in versions >= 2020.3.1)

The status of Google Analytics should be sent as one of the first *log messages*. Such as:
`Google Analytics is disabled` or `Google Analytics is ENABLED`.

## Frequency
The frequency is this:
* Once at start
* Once after data is first received
* One hour after the program started
* Every 20 hours

## Collected Data
This data may include the following:
* The type of program running (mate, rover)
* The language and region of the user
* (Mate)The devices connected to the mate (FX, MX, FM)
* (Mate)The operational mode of FX devices
* (Rover)The model of the CC ex: "RNG-CTRL-RVRPG40" or "RCC20RVRE-G1", etc

This data **cannot** be used to uniquely identify you or your system. The data is anonymous.

## Resetting ID
To uniquely identify each SolarThing instance, a UUID is used and is stored. If you have this repository cloned, you should
see the file `.data/analytics_data.json` in one of the working directories located in [program](../../program). You can delete this file
to reset the ID and if Google Analytics is enabled, the next time you start SolarThing a new ID will be generated.

There is no reason you should have to reset this, but it's there if you want to.

### Policy for SolarThing to follow
Because SolarThing uses Google Analytics and its Measurement Protocol, it must follow [this policy](https://developers.google.com/analytics/devguides/collection/protocol/policy).
If you do not believe it follows this policy, please create an issue on [our issue page](https://github.com/wildmountainfarms/solarthing/issues).

