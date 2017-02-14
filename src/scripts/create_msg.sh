ENDPOINT=https://aqueous-caverns-35252.herokuapp.com/tcss360/messages
curl -X POST -H "Content-Type: application/json" -d @./test_msg.json $ENDPOINT
echo