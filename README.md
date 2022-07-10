# Getting Started


Use Scoop to install rsc (RSocket CLI)

There are multiple patterns with RSocket
- Request & Response
- FnF - Fire and Forget
- Channel where a single value or multiple values will give forth to a stream response
- Stream - where both input & output are streamed

##Request & Response
rsc --request --route=request-response --data "{\"empName\":\"test\"}" --stacktrace --debug tcp://localhost:7869/request-response

# Fire & Forget
rsc -fnf --route fire-and-forget --debug --data="{\"empName\":\"d\"}" tcp://localhost:7869

# Stream - output
rsc --stream --debug --trace  --route request-stream -d "{\"key\":123}" tcp://localhost:7869

# Stream Input & Output
rsc --debug --trace --channel --data - --route stream-stream tcp://localhost:7869


