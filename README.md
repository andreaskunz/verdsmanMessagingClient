# verdsmanMessagingClient


## Message Types and their JSON structures.

### UMCMessage
```json
{
	"from": "",
	"to": "",
	"topic": "",
	"parsertype": ""
}
```

### UMCCommandMessage
```json
{
	"from": "",
	"to": "",
	"topic": "",
	"parsertype": "",
	"command": "",
	"intParams": [
		123456,
		42,
		43
	],
	"doubleParams": [
		0.001,
		1.21,
		10.5
	],
	"stringParams": [
		"string1",
		"Hi there!",
		"JSON's fun."
	]
}
```